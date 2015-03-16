/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.dao.org;

import org.commons.logger.ILogger;
import org.commons.logger.LoggerFactory;
import org.impensa.service.db.entity.Org;
import org.impensa.service.db.entity.User;
import org.impensa.service.db.repository.OrgRepository;
import org.impensa.service.util.DomainEntityConverter;
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

    @Override
    public OrgDMO findByOrgId(String orgId) throws OrgDAOException {
        Org org = this.getOrgRepository().findByOrgId(orgId);
        return this.convertFrom(org);
    }
    @Autowired
    private OrgRepository OrgRepository;

    public OrgRepository getOrgRepository() {
        return OrgRepository;
    }

    public void setOrgRepository(OrgRepository OrgRepository) {
        this.OrgRepository = OrgRepository;
    }

    @Override
    @Transactional
    public OrgDMO createOrg(OrgDMO orgDMO) throws OrgDAOException {
        Org org = this.convertTo(orgDMO);
        this.getOrgRepository().save(org);
        return orgDMO;
    }

    @Override
    @Transactional
    public OrgDMO updateOrg(OrgUpdateDMO orgUpdateDMO) throws OrgDAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Transactional
    public boolean deleteOrg(OrgDMO orgDMO) throws OrgDAOException {
        Org org = this.getOrgRepository().findByOrgId(orgDMO.getOrgId());
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
    public Org convertTo(OrgDMO orgDMO) throws OrgDAOException {
        Org org;
        try {
            org = DomainEntityConverter.toEntity(orgDMO, Org.class);
        } catch (Exception ex) {
            logger.error("error while converting to entity object " + orgDMO, ex);
            throw new OrgDAOException("error while converting to entity object " + orgDMO, ex);
        }
        return org;
    }

    @Override
    public OrgDMO convertFrom(Org org) throws OrgDAOException {
        OrgDMO orgDMO;
        try {
            orgDMO = DomainEntityConverter.toDomain(org, OrgDMO.class);
        } catch (Exception ex) {
            logger.error("error while converting from entity object " + org, ex);
            throw new OrgDAOException("error while converting from entity object " + org, ex);
        }
        return orgDMO;
    }
}
