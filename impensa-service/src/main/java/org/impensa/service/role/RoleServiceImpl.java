/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.role;

import org.impensa.dao.role.RoleDMO;
import org.impensa.dao.role.RoleUpdateDMO;
import org.impensa.dao.role.IRoleDAO;
import org.impensa.dao.role.RoleSearchCriteria;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.commons.logger.ILogger;
import org.commons.logger.LoggerFactory;
import org.impensa.dao.function.FunctionDMO;
import org.impensa.exception.ImpensaException;
import org.impensa.service.function.IFunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.impensa.service.tenant.TenantConstant.*;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author manosahu
 */
@Service
public class RoleServiceImpl implements IRoleService {

    private static final ILogger logger = LoggerFactory.getLogger(RoleServiceImpl.class.getName());

    @Autowired
    private IRoleDAO roleDAOImpl;

    @Autowired
    private IFunctionService functionServiceImpl;

    public IFunctionService getFunctionService() {
        return functionServiceImpl;
    }

    public void setFunctionService(IFunctionService functionService) {
        this.functionServiceImpl = functionService;
    }

    public IRoleDAO getRoleDAO() {
        return roleDAOImpl;
    }

    public void setRoleDAO(IRoleDAO roleDAO) {
        this.roleDAOImpl = roleDAO;
    }

    @Override
    public Set<RoleDMO> findBy(RoleSearchCriteria rsc) throws ImpensaException {
        Set<RoleDMO> result;

        result = this.getRoleDAO().findBy(rsc);

        return result;
    }

    @Transactional
    @Override
    public RoleDMO createRole(RoleDMO roleDMO) throws ImpensaException {
        RoleDMO result;

        result = this.getRoleDAO().createRole(roleDMO);

        return result;
    }

    @Override
    public RoleDMO updateRole(final RoleUpdateDMO roleUpdateDMO) throws ImpensaException {
        RoleDMO result;

        result = this.getRoleDAO().updateRole(roleUpdateDMO);

        return result;
    }

    @Transactional
    @Override
    public boolean deleteRole(RoleDMO roleDMO) throws ImpensaException {
        boolean result;

        result = this.getRoleDAO().deleteRole(roleDMO);

        return result;
    }

    /**
     * So this is special API in the sense that it will create a default tenant
     * role with ALL the functions available in the system. This will be useful
     * in new tenant setup. We want to manage this special role called tenant in
     * a functional way , rather than hardcoding way. The only thing that will
     * be hard coded is this tenant Role name TENANT_ROLE to be specific. At
     * present , we will NOT create any default tenant org for org. but that is
     * debatable. The current philosophy is concerned towards minimum
     * maintenance. My gut feeling tells me that more default items result in
     * untractable problems. And hence maintenance nightmare. but we will
     * revisit this again as impensa evolves. for now create this tenant role
     * without any second thought. so what it does that it retrieves all the
     * functions available in the setup and assign the same to a new created
     * tenant called TENANT_ROLE. When this method will be invoked ??? At the
     * moment , lets call it during startup. but we will revisit again.
     *
     *
     *
     * @TODO My doubt on this txn stuff This code might not be a single
     * transaction. as we have mixed declarative with manual txn boundary.
     *
     * @return
     * @throws ImpensaException
     */
    @Transactional
    @Override
    public RoleDMO createTenantRole() throws ImpensaException {

        RoleDMO tenantRoleDMO = new RoleDMO();
        tenantRoleDMO.setRoleId(TENANT_ROLE_ID);
        tenantRoleDMO.setRoleName(TENANT_ROLE_NAME);
        tenantRoleDMO.setRoleDescription(TENANT_ROLE_DESC);
        this.createRole(tenantRoleDMO);
        Set<String> functionNames = new HashSet<String>();

        Map<String, FunctionDMO> allFunctionMap = this.getFunctionService().createAllFunctions();
        if (allFunctionMap != null) {
            functionNames.addAll(allFunctionMap.keySet());
        }

        RoleUpdateDMO roleUpdateDMO = new RoleUpdateDMO();
        roleUpdateDMO.setRoleDMO(tenantRoleDMO);
        roleUpdateDMO.getInsertFunctionNameSet().addAll(functionNames);
        this.updateRole(roleUpdateDMO);
        return tenantRoleDMO;
    }

}
