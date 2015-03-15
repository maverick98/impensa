/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.dao.org;

import org.impensa.service.db.repository.OrgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author manosahu
 */
@Component
@Transactional
public class OrgDAOImpl implements IOrgDAO {

    @Autowired
    private OrgRepository OrgRepository;

    public OrgRepository getOrgRepository() {
        return OrgRepository;
    }

    public void setOrgRepository(OrgRepository OrgRepository) {
        this.OrgRepository = OrgRepository;
    }
    
    

    @Override
    public OrgDMO createOrg(OrgDMO orgDMO) throws OrgDAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OrgDMO updateOrg(OrgUpdateDMO orgUpdateDMO) throws OrgDAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteOrg(OrgDMO orgDMO) throws OrgDAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteOrg(String orgId) throws OrgDAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
