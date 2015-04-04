/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.dao.tenant;

import org.common.bean.BeanConverter;
import org.commons.logger.ILogger;
import org.commons.logger.LoggerFactory;
import org.commons.string.StringUtil;
import org.impensa.AppContainer;
import org.impensa.db.GraphDatabaseUtil;
import org.impensa.db.TenantGraphDatabseService;
import org.impensa.db.TenantGraphDatabseServiceFactory;
import org.impensa.db.entity.TenantEntity;
import org.impensa.db.repository.TenantRepository;
import org.impensa.exception.BeanConversionErrorCode;
import org.impensa.exception.ImpensaException;
import org.impensa.exception.ValidationErrorCode;
import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author manosahu
 */
public class TenantDAOImpl implements ITenantDAO {

    private static final ILogger logger = LoggerFactory.getLogger(TenantDAOImpl.class.getName());

    @Autowired
    private TenantRepository tenantRepository;

    public TenantRepository getTenantRepository() {
        return tenantRepository;
    }

    public void setTenantRepository(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Override
    public boolean registerTenantDatabaseService(String tenantId) throws ImpensaException {
        if (StringUtil.isNullOrEmpty(tenantId)) {
            throw new ImpensaException(ValidationErrorCode.VALUE_NULL_OR_EMPTY).set("tenantId", "null or empty");
        }

        TenantGraphDatabseService tenantGraphDatabaseService = TenantGraphDatabseServiceFactory.startTenantGraphDatabaseService(tenantId);
        TenantGraphDatabseServiceFactory.registerTenantGraphDatabaseService(tenantGraphDatabaseService);
        return true;
    }

    @Override
    public TenantGraphDatabseService findTenantGraphDatabaseService(String tenantId) throws ImpensaException {
        TenantGraphDatabseService tenantGraphDatabseService = new TenantGraphDatabseService();
        GraphDatabaseService graphDatabaseService = AppContainer.getInstance()
                .getBean(
                        tenantGraphDatabseService.getTenantGraphDataServiceBeanName(),
                        GraphDatabaseService.class
                );
        tenantGraphDatabseService.setGraphDatabaseService(graphDatabaseService);
        return tenantGraphDatabseService;

    }

    @Override
    public TenantDMO isTenantRegistered(String tenantId) throws ImpensaException {
        if (StringUtil.isNullOrEmpty(tenantId)) {
            throw new ImpensaException(ValidationErrorCode.VALUE_NULL_OR_EMPTY).set("tenantId", "null or empty");
        }
        TenantDMO tenantDMO = this.findByTenantId(tenantId);
        return tenantDMO;
    }

    @Override
    public boolean isTenantDatabaseCreated(String tenantId) throws ImpensaException {
        if (StringUtil.isNullOrEmpty(tenantId)) {
            throw new ImpensaException(ValidationErrorCode.VALUE_NULL_OR_EMPTY).set("tenantId", "null or empty");
        }
        TenantGraphDatabseService tenantGraphDatabseService = TenantGraphDatabseServiceFactory.startTenantGraphDatabaseService(tenantId);
        boolean status = tenantGraphDatabseService.getGraphDatabaseService() != null;
        GraphDatabaseUtil.shutdown(tenantGraphDatabseService.getGraphDatabaseService());
        return status;

    }

    @Override
    public TenantDMO findByTenantId(String tenantId) throws ImpensaException {
        if (StringUtil.isNullOrEmpty(tenantId)) {
            throw new ImpensaException(ValidationErrorCode.VALUE_NULL_OR_EMPTY).set("tenantId", "null or empty");
        }
        TenantEntity tenantEntity = this.getTenantRepository().findByTenantId(tenantId);

        return this.convertFrom(tenantEntity);
    }

    @Override
    @Transactional
    public TenantDMO createTenant(TenantDMO tenantDMO) throws ImpensaException {
        if (tenantDMO == null) {
            throw new ImpensaException(ValidationErrorCode.VALUE_NULL).set("tenantDMO", "null");
        }
        TenantEntity tenantEntity = this.convertTo(tenantDMO);
        this.getTenantRepository().save(tenantEntity);
        return tenantDMO;
    }

    @Override
    public boolean createTenantDatabase(TenantDMO tenantDMO) throws ImpensaException {
        String tenantId = tenantDMO.getTenantId();
        TenantGraphDatabseService tenantGraphDatabaseService = TenantGraphDatabseServiceFactory.createTenantGraphDatabaseService(tenantId);
        TenantGraphDatabseServiceFactory.registerTenantGraphDatabaseService(tenantGraphDatabaseService);

        return true;
    }

    @Override
    @Transactional
    public boolean deleteTenant(TenantDMO tenantDMO) throws ImpensaException {
        if (tenantDMO == null) {
            throw new ImpensaException(ValidationErrorCode.VALUE_NULL).set("orgDMO", "null");
        }
        TenantEntity tenantEntity = this.getTenantRepository().findByTenantId(tenantDMO.getTenantId());
        this.getTenantRepository().delete(tenantEntity);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteTenant(String tenantId) throws ImpensaException {
        if (StringUtil.isNullOrEmpty(tenantId)) {
            throw new ImpensaException(ValidationErrorCode.VALUE_NULL_OR_EMPTY).set("tenantId", "null");
        }
        TenantDMO tenantDMO = new TenantDMO();
        tenantDMO.setTenantId(tenantId);
        return this.deleteTenant(tenantDMO);
    }

    @Override
    public TenantEntity convertTo(TenantDMO tenantDMO) throws ImpensaException {
        if (tenantDMO == null) {
            return null;
        }
        TenantEntity tenantEntity;
        try {
            tenantEntity = BeanConverter.toMappingBean(tenantDMO, TenantEntity.class);
        } catch (Exception ex) {
            throw ImpensaException.wrap(ex)
                    .setErrorCode(BeanConversionErrorCode.TO_MAPPING_BEAN)
                    .set("tenantDMO", tenantDMO);

        }
        return tenantEntity;
    }

    @Override
    public TenantDMO convertFrom(TenantEntity tenantEntity) throws ImpensaException {
        if (tenantEntity == null) {
            return null;
        }
        TenantDMO tenantDMO;
        try {
            tenantDMO = BeanConverter.fromMappingBean(tenantEntity, TenantDMO.class);
        } catch (Exception ex) {
            throw ImpensaException.wrap(ex)
                    .setErrorCode(BeanConversionErrorCode.FROM_MAPPING_BEAN)
                    .set("tenantEntity", tenantEntity);
        }
        return tenantDMO;
    }
}
