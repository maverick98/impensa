/*
 *  Copyleft(BigBang<-->BigCrunch)  Manoranjan Sahu
 *  
 */
package org.impensa.dao.role;

import org.impensa.dao.role.RoleUpdateDMO;
import org.impensa.dao.role.RoleDMO;
import org.impensa.dao.role.IRoleDAO;
import java.util.Map;
import org.impensa.AppContainer;
import org.impensa.startup.ImpensaStartup;
import org.impensa.dao.function.FunctionDMO;
import org.impensa.dao.function.IFunctionDAO;
import org.impensa.service.role.IRoleService;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.impensa.service.tenant.TenantConstant.*;

/**
 *
 * @author manosahu
 */
public class RoleDAONGTest {

    private static ClassPathXmlApplicationContext context;

    public IRoleService getRoleService() {
        return AppContainer.getInstance().getBean("roleServiceImpl", IRoleService.class);
        //return (IOrgDAO) context.getBean("orgDAOImpl");
    }

    public IRoleDAO getRoleDAO() {
        return AppContainer.getInstance().getBean("roleDAOImpl", IRoleDAO.class);
        //return (IOrgDAO) context.getBean("orgDAOImpl");
    }

    public IFunctionDAO getFunctionDAO() {
        return AppContainer.getInstance().getBean("functionDAOImpl", IFunctionDAO.class);
        //return (IFunctionDAO) context.getBean("functionDAOImpl");

    }

    public RoleDAONGTest() {
    }

 
    @BeforeClass
    public static void setUpClass() throws Exception {
    //    ImpensaStartup.startup();

    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

       @Test(suiteName = "mainSuite")
    public void testCreateRole() throws Exception {

        RoleDMO roleDMO = new RoleDMO();
        roleDMO.setRoleId("r1");
        roleDMO.setRoleDescription("my role desc");
        roleDMO.setRoleName("my role");
        this.getRoleDAO().createRole(roleDMO);
        RoleDMO roleDMO1 = this.getRoleDAO().findByRoleId("r1");
        AssertJUnit.assertNotNull(roleDMO1);
        System.out.println("role name found is " + roleDMO1.getRoleName());
        System.out.println("checking for rolename with my role");
        System.out.println("assigned functions are  " + roleDMO1.getAssignedFunctionNames());
        AssertJUnit.assertEquals(roleDMO1.getAssignedFunctionNames().size(), 0);
        AssertJUnit.assertEquals(roleDMO1.getRoleName(), "my role");

    }

        @Test(suiteName = "mainSuite")
    public void testUpdateRole() throws Exception {

        RoleDMO roleDMO = new RoleDMO();
        roleDMO.setRoleId("r1");
        roleDMO.setRoleDescription("my role desc");
        roleDMO.setRoleName("my role");
        this.getRoleDAO().createRole(roleDMO);
        RoleDMO roleDMO1 = this.getRoleDAO().findByRoleId("r1");
        AssertJUnit.assertNotNull(roleDMO1);
        System.out.println("role name found is " + roleDMO1.getRoleName());
        System.out.println("checking for rolename with my role");
        System.out.println("assigned functions are  " + roleDMO1.getAssignedFunctionNames());
        AssertJUnit.assertEquals(roleDMO1.getAssignedFunctionNames().size(), 0);
        AssertJUnit.assertEquals(roleDMO1.getRoleName(), "my role");

        RoleUpdateDMO roleUpdateDMO = new RoleUpdateDMO();
        roleUpdateDMO.setRoleDMO(roleDMO1);
        Map<String, FunctionDMO> functionDMOMap = this.getFunctionDAO().cacheFunctions();

        AssertJUnit.assertNotNull(functionDMOMap);
        System.out.println(functionDMOMap);
        for (Map.Entry<String, FunctionDMO> entry : functionDMOMap.entrySet()) {

            FunctionDMO functionDMO = entry.getValue();
            AssertJUnit.assertNotNull("functionName can NOT be null", functionDMO.getFunctionName());
            AssertJUnit.assertNotNull("function Description can NOT be null", functionDMO.getFunctionDescription());
            System.out.println(functionDMO.getFunctionName());
            System.out.println(functionDMO.getFunctionDescription());
            this.getFunctionDAO().createFunction(functionDMO);
            FunctionDMO functionDMO1 = this.getFunctionDAO().findByFunctionName(functionDMO.getFunctionName());
            System.out.println("found function dmo1 " + functionDMO1.getFunctionName());
            System.out.println("@@@@@@@@@@@@");

        }
        roleUpdateDMO.getInsertFunctionNameSet().addAll(functionDMOMap.keySet());
        RoleDMO roleDMO2 = this.getRoleDAO().updateRole(roleUpdateDMO);
        System.out.println("assigned functions are " + roleDMO2.getAssignedFunctionNames());

    }

        @Test(suiteName = "mainSuite")
    public void testCreateTenantRole() throws Exception {

        this.getRoleService().createTenantRole();
        RoleDMO tenantRoleDMO = this.getRoleDAO().findByRoleId(TENANT_ROLE_ID);
        System.out.println("COOL.. FOUNDA TENANT "+tenantRoleDMO.getRoleDescription());
        AssertJUnit.assertNotNull(tenantRoleDMO);
    }

        @Test(suiteName = "mainSuite")
    public void testDeleteRole() throws Exception {
        RoleDMO roleDMO = new RoleDMO();
        roleDMO.setRoleId("r1");
        roleDMO.setRoleDescription("my role desc");
        roleDMO.setRoleName("my role");
        this.getRoleDAO().createRole(roleDMO);
        RoleDMO roleDMO1 = this.getRoleDAO().findByRoleId("r1");
        AssertJUnit.assertNotNull(roleDMO1);
        System.out.println("role name found is " + roleDMO1.getRoleName());
        System.out.println("checking for rolename with my role");
        AssertJUnit.assertEquals(roleDMO1.getRoleName(), "my role");
        this.getRoleDAO().deleteRole(roleDMO1);
        roleDMO1 = this.getRoleDAO().findByRoleId("r1");
        AssertJUnit.assertNull(roleDMO1);

    }
}
