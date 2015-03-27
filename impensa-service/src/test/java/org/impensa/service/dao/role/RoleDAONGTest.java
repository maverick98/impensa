/*
 *  Copyleft(BigBang<-->BigCrunch)  Manoranjan Sahu
 *  
 */
package org.impensa.service.dao.role;

import java.util.Map;
import org.impensa.service.AppContainer;
import org.impensa.service.ImpensaStartup;
import org.impensa.service.dao.function.FunctionDMO;
import org.impensa.service.dao.function.IFunctionDAO;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author manosahu
 */
public class RoleDAONGTest {

    private static ClassPathXmlApplicationContext context;

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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    private static final String CLASSPATH_LOCATION = "classpath:spring/neo4j/spring-neo4j.xml";

    @BeforeClass
    public static void setUpClass() throws Exception {
        ImpensaStartup.startup();

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

    @Test
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

    @Test
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

    @Test
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