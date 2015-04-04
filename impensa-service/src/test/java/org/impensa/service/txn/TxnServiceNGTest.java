/*
 *  Copyleft(BigBang<-->BigCrunch)  Manoranjan Sahu
 *  
 */
package org.impensa.service.txn;

import org.common.crypto.EncryptionUtil;
import org.impensa.dao.role.RoleDMO;
import org.impensa.dao.role.IRoleDAO;
import org.impensa.AppContainer;
import org.impensa.startup.ImpensaStartup;
import org.impensa.dao.function.IFunctionDAO;
import org.impensa.dao.user.IUserDAO;
import org.impensa.dao.user.UserDMO;
import org.impensa.dao.user.UserUpdateDMO;
import org.impensa.service.login.ILoginService;
import org.impensa.service.role.IRoleService;
import org.springframework.context.support.ClassPathXmlApplicationContext;
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
public class TxnServiceNGTest {

    private static ClassPathXmlApplicationContext context;

    public ILoginService getLoginService() {
        return AppContainer.getInstance().getBean("loginServiceImpl", ILoginService.class);
    }

    public IUserDAO getUserDAO() {
        return AppContainer.getInstance().getBean("userDAOImpl", IUserDAO.class);
        //return (IUserDAO) context.getBean("userDAOImpl");
    }

    public ITxnService getTxnService() {
        return AppContainer.getInstance().getBean("txnServiceImpl", ITxnService.class);
        //return (IOrgDAO) context.getBean("orgDAOImpl");
    }

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

    public TxnServiceNGTest() {
    }

     @BeforeClass
    public static void setUpClass() throws Exception {
 //       ImpensaStartup.startup();

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

    public void testCreateTxn() throws Exception {
        //PermissionAspect permissionAspect = AppContainer.getInstance().getBean("permissionAspect", PermissionAspect.class);
        //System.out.println("permission aspect" + permissionAspect);
        this.getRoleService().createTenantRole();
        RoleDMO tenantRoleDMO = this.getRoleDAO().findByRoleId(TENANT_ROLE_ID);

        UserDMO tenantUserDMO = new UserDMO();
        tenantUserDMO.setUserId("ms");
        tenantUserDMO.setAddress("Uptown Village,17th main street,Sherman,Denver");
        tenantUserDMO.setAge(12);
        tenantUserDMO.setEmail("ms@ms.com");
        tenantUserDMO.setFirstName("Manoranjan");
        tenantUserDMO.setLastName("Sahu");
        tenantUserDMO.setMiddleName("");
        tenantUserDMO.setPhone("303-123-4782");
        tenantUserDMO.setEncryptedPassword(EncryptionUtil.encrypt("abcd123#"));
        this.getUserDAO().createUser(tenantUserDMO);

        UserUpdateDMO tenantUserUpdateDMO = new UserUpdateDMO();
        tenantUserUpdateDMO.setUserUpdate(tenantUserDMO);
        tenantUserUpdateDMO.getInsertRoleIdSet().add(tenantRoleDMO.getRoleId());
        this.getUserDAO().updateUser(tenantUserUpdateDMO);
        this.getLoginService().login("ms", "abcd123#",null);
        this.getTxnService().createTransaction();

    }

}
