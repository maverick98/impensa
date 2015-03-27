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
import org.impensa.service.dao.AbstractIdSetProcessor;
import org.impensa.service.dao.AbstractTxnExecutor;
import org.impensa.service.dao.exception.DAOException;
import org.impensa.service.dao.user.UserDAOException;
import org.impensa.service.db.entity.OrgEntity;
import org.impensa.service.db.entity.RoleEntity;
import org.impensa.service.db.repository.OrgRepository;
import org.impensa.service.db.repository.RoleRepository;

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
    public OrgDMO findByOrgId(String orgId) throws OrgDAOException {
        OrgEntity org = this.getOrgRepository().findByOrgId(orgId);
        return this.convertFrom(org);
    }

    @Override
    @Transactional
    public OrgDMO createOrg(OrgDMO orgDMO) throws OrgDAOException {
        OrgEntity org = this.convertTo(orgDMO);
        this.getOrgRepository().save(org);
        return orgDMO;
    }

    @Override
    //@Transactional
    public OrgDMO updateOrg(final OrgUpdateDMO orgUpdateDMO) throws OrgDAOException {

        final OrgEntity org = this.getOrgRepository().findByOrgId(orgUpdateDMO.getOrgUpdate().getOrgId());

        try {
            new AbstractTxnExecutor() {

                @Override
                public void execute() throws DAOException {
                    new AbstractIdSetProcessor(orgUpdateDMO.getInsertRoleIdSet()) {

                        @Override
                        public void onIdVisit(String roleId) throws UserDAOException {
                            RoleEntity role = getRoleRepository().findByRoleId(roleId);
                            org.assignRole(role);
                            getOrgRepository().save(org);
                            getRoleRepository().save(role);
                        }
                    }.process();
                }
            }.createTxn();
        } catch (DAOException ex) {
            throw new OrgDAOException(ex);
        }

        return this.convertFrom(org);
    }

    @Override
    @Transactional
    public boolean deleteOrg(OrgDMO orgDMO) throws OrgDAOException {
        OrgEntity org = this.getOrgRepository().findByOrgId(orgDMO.getOrgId());
        this.getOrgRepository().delete(org);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteOrg(String orgId) throws OrgDAOException {
        OrgDMO orgDMO = new OrgDMO();
        orgDMO.setOrgId(orgId);
        return this.deleteOrg(orgDMO);
    }

    @Override
    public OrgEntity convertTo(OrgDMO orgDMO) throws OrgDAOException {
        OrgEntity org;
        try {
            org = BeanConverter.toMappingBean(orgDMO, OrgEntity.class);
        } catch (Exception ex) {
            logger.error("error while converting to entity object " + orgDMO, ex);
            throw new OrgDAOException("error while converting to entity object " + orgDMO, ex);
        }
        return org;
    }

    @Override
    public OrgDMO convertFrom(OrgEntity org) throws OrgDAOException {
        OrgDMO orgDMO;
        try {
            orgDMO = BeanConverter.fromMappingBean(org, OrgDMO.class);
        } catch (Exception ex) {
            logger.error("error while converting from entity object " + org, ex);
            throw new OrgDAOException("error while converting from entity object " + org, ex);
        }
        return orgDMO;
    }
}
