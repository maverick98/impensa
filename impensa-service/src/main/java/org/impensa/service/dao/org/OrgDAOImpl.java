/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.dao.org;

import org.common.bean.BeanConverter;
import org.commons.logger.ILogger;
import org.commons.logger.LoggerFactory;
import org.commons.string.StringUtil;
import org.impensa.service.dao.AbstractIdSetProcessor;
import org.impensa.service.dao.AbstractTxnExecutor;
import org.impensa.service.db.entity.OrgEntity;
import org.impensa.service.db.entity.RoleEntity;
import org.impensa.service.db.repository.OrgRepository;
import org.impensa.service.db.repository.RoleRepository;
import org.impensa.service.exception.BeanConversionErrorCode;
import org.impensa.service.exception.DataFetchErrorCode;
import org.impensa.service.exception.ImpensaException;
import org.impensa.service.exception.ValidationErrorCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author manosahu
 */
@Component

public class OrgDAOImpl implements IOrgDAO {

    private static final ILogger logger = LoggerFactory.getLogger(OrgDAOImpl.class.getName());

    @Autowired
    private OrgRepository orgRepository;

    @Autowired
    private RoleRepository roleRepository;

    public OrgRepository getOrgRepository() {
        return orgRepository;
    }

    public void setOrgRepository(OrgRepository OrgRepository) {
        this.orgRepository = OrgRepository;
    }

    public RoleRepository getRoleRepository() {
        return roleRepository;
    }

    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public OrgDMO findByOrgId(String orgId) throws ImpensaException {
        if (StringUtil.isNullOrEmpty(orgId)) {
            throw new ImpensaException(ValidationErrorCode.VALUE_NULL_OR_EMPTY).set("orgId", "null or empty");
        }
        OrgEntity orgEntity = this.getOrgRepository().findByOrgId(orgId);

        return this.convertFrom(orgEntity);
    }

    @Override
    @Transactional
    public OrgDMO createOrg(OrgDMO orgDMO) throws ImpensaException {
        if (orgDMO == null) {
            throw new ImpensaException(ValidationErrorCode.VALUE_NULL).set("orgDMO", "null");
        }
        OrgEntity org = this.convertTo(orgDMO);
        this.getOrgRepository().save(org);
        return orgDMO;
    }

    @Override
    //@Transactional
    public OrgDMO updateOrg(final OrgUpdateDMO orgUpdateDMO) throws ImpensaException {
        if (orgUpdateDMO == null) {
            throw new ImpensaException(ValidationErrorCode.VALUE_NULL).set("orgUpdateDMO", "null");
        }
        if (orgUpdateDMO.getOrgUpdate() == null) {
            throw new ImpensaException(ValidationErrorCode.VALUE_NULL).set("orgUpdateDMO's orgUpdate", "null");
        }
        if (orgUpdateDMO.getOrgUpdate().getOrgId() == null) {
            throw new ImpensaException(ValidationErrorCode.VALUE_NULL).set("orgUpdateDMO's orgUpdate's orgId", "null");
        }
        final OrgEntity orgEntity = this.getOrgRepository().findByOrgId(orgUpdateDMO.getOrgUpdate().getOrgId());

        if (orgEntity == null) {
            throw new ImpensaException(DataFetchErrorCode.DATA_NOT_FOUND).set("No orgEntity foud for ", orgUpdateDMO.getOrgUpdate().getOrgId());
        }

        new AbstractTxnExecutor() {

            @Override
            public void execute() throws ImpensaException {
                new AbstractIdSetProcessor(orgUpdateDMO.getInsertRoleIdSet()) {

                    @Override
                    public void onIdVisit(String roleId) throws ImpensaException {
                        RoleEntity role = getRoleRepository().findByRoleId(roleId);
                        orgEntity.assignRole(role);
                        getOrgRepository().save(orgEntity);
                        getRoleRepository().save(role);
                    }
                }.process();
            }
        }.createTxn();

        return this.convertFrom(orgEntity);
    }

    @Override
    @Transactional
    public boolean deleteOrg(OrgDMO orgDMO) throws ImpensaException {
        if (orgDMO == null) {
            throw new ImpensaException(ValidationErrorCode.VALUE_NULL).set("orgDMO", "null");
        }
        OrgEntity org = this.getOrgRepository().findByOrgId(orgDMO.getOrgId());
        this.getOrgRepository().delete(org);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteOrg(String orgId) throws ImpensaException {
        if (StringUtil.isNullOrEmpty(orgId)) {
            throw new ImpensaException(ValidationErrorCode.VALUE_NULL_OR_EMPTY).set("orgId", "null");
        }
        OrgDMO orgDMO = new OrgDMO();
        orgDMO.setOrgId(orgId);
        return this.deleteOrg(orgDMO);
    }

    @Override
    public OrgEntity convertTo(OrgDMO orgDMO) throws ImpensaException {
        if (orgDMO == null) {
            return null;
        }
        OrgEntity org;
        try {
            org = BeanConverter.toMappingBean(orgDMO, OrgEntity.class);
        } catch (Exception ex) {
            throw ImpensaException.wrap(ex)
                    .setErrorCode(BeanConversionErrorCode.TO_MAPPING_BEAN)
                    .set("orgDMO", orgDMO);

        }
        return org;
    }

    @Override
    public OrgDMO convertFrom(OrgEntity orgEntity) throws ImpensaException {
        if (orgEntity == null) {
            return null;
        }
        OrgDMO orgDMO;
        try {
            orgDMO = BeanConverter.fromMappingBean(orgEntity, OrgDMO.class);
        } catch (Exception ex) {
            throw ImpensaException.wrap(ex)
                    .setErrorCode(BeanConversionErrorCode.FROM_MAPPING_BEAN)
                    .set("orgEntity", orgEntity);
        }
        return orgDMO;
    }
}
