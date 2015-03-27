/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.role;

import org.impensa.service.dao.role.*;
import java.util.Set;
import org.commons.logger.ILogger;
import org.commons.logger.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author manosahu
 */
@Service
public class RoleServiceImpl implements IRoleService {

    private static final ILogger logger = LoggerFactory.getLogger(RoleServiceImpl.class.getName());

    @Autowired
    private IRoleDAO roleDAOImpl;

    public IRoleDAO getRoleDAO() {
        return roleDAOImpl;
    }

    public void setRoleDAO(IRoleDAO roleDAO) {
        this.roleDAOImpl = roleDAO;
    }

    @Override
    public Set<RoleDMO> findBy(RoleSearchCriteria rsc) throws RoleServiceException {
        Set<RoleDMO> result;
        try {
            result = this.getRoleDAO().findBy(rsc);
        } catch (RoleDAOException ex) {
            logger.error("error while roledao.findby", ex);
            throw new RoleServiceException("error while roledao.findby", ex);
        }

        return result;
    }

    @Override
    public RoleDMO createRole(RoleDMO roleDMO) throws RoleServiceException {
        RoleDMO result;
        try {
            result = this.getRoleDAO().createRole(roleDMO);
        } catch (RoleDAOException ex) {
            logger.error("error while roledao.createRole", ex);
            throw new RoleServiceException("error while roledao.createRole", ex);
        }
        return result;
    }

    @Override
    public RoleDMO updateRole(final RoleUpdateDMO roleUpdateDMO) throws RoleServiceException {
        RoleDMO result;
        try {
            result = this.getRoleDAO().updateRole(roleUpdateDMO);
        } catch (RoleDAOException ex) {
            logger.error("error while roledao.updateRole", ex);
            throw new RoleServiceException("error while roledao.updateRole", ex);
        }
        return result;
    }

    @Override
    public boolean deleteRole(RoleDMO roleDMO) throws RoleServiceException {
        boolean result;
        try {
            result =  this.getRoleDAO().deleteRole(roleDMO);
        } catch (RoleDAOException ex) {
             logger.error("error while roledao.deleteRole", ex);
            throw new RoleServiceException("error while roledao.deleteRole", ex);
        }
        return result;
    }

    @Override
    public RoleDMO createTenantRole() throws RoleServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
