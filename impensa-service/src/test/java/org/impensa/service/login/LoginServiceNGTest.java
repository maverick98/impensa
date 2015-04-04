/*
 *  Copyleft(BigBang<-->BigCrunch)  Manoranjan Sahu
 *  
 */
package org.impensa.service.login;

import org.impensa.AppContainer;
import org.impensa.dao.org.IOrgDAO;
import org.impensa.dao.session.SessionDMO;
import org.impensa.dao.user.IUserDAO;
import org.impensa.dao.user.UserDMO;
import org.common.crypto.EncryptionUtil;
import org.impensa.startup.ImpensaStartup;
import org.impensa.exception.ImpensaException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author manosahu
 */
public class LoginServiceNGTest {

    private static ClassPathXmlApplicationContext context;

    public IUserDAO getUserDAO() {
        return AppContainer.getInstance().getBean("userDAOImpl", IUserDAO.class);
        //return (IUserDAO) context.getBean("userDAOImpl");
    }

    public IOrgDAO getOrgDAO() {
        return AppContainer.getInstance().getBean("orgDAOImpl", IOrgDAO.class);
    }

    public ILoginService getLoginService() {
        return AppContainer.getInstance().getBean("loginServiceImpl", ILoginService.class);
    }

    public LoginServiceNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
//        ImpensaStartup.startup();

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

    public void login() throws Exception {

        UserDMO userDMO = new UserDMO();
        userDMO.setUserId("Brian");
        userDMO.setAddress("Uptown Village,17th main street,Sherman,Denver");
        userDMO.setAge(12);
        userDMO.setEmail("rs@rs.com");
        userDMO.setFirstName("Brian");
        userDMO.setLastName("Sahoo");
        userDMO.setMiddleName("");
        userDMO.setPhone("303-123-4782");
        userDMO.setEncryptedPassword(EncryptionUtil.encrypt("abcd123#"));
        this.getUserDAO().createUser(userDMO);
        SessionDMO sessionDMO;
        sessionDMO = this.getLoginService().login("Brian", "abcd123#", null);

        Assert.assertEquals(true, sessionDMO.isLoggedIn());
        boolean isLoggedIn = this.getLoginService().isLoggedIn("Brian");
        Assert.assertEquals(true, isLoggedIn);
        System.out.println("Brian logged in " + isLoggedIn);
        sessionDMO = this.getLoginService().login("Brian", "abcd123#", "");
        Assert.assertEquals(true, sessionDMO.isLoggedIn());
        System.out.println("Brian logged in again " + isLoggedIn);

        this.getLoginService().logout("Brian");

        isLoggedIn = this.getLoginService().isLoggedIn("Brian");

        System.out.println("Brian must be logged out " + isLoggedIn);
        System.out.println("Brian trying wrong password 1st time");
        sessionDMO = this.getLoginService().login("Brian", "abcd123#1", null);
        Assert.assertEquals(false, sessionDMO.isLoggedIn());

        System.out.println("Brian trying wrong password 2nd time");
        sessionDMO = this.getLoginService().login("Brian", "abcd123#11", null);
        Assert.assertEquals(false, sessionDMO.isLoggedIn());

        System.out.println("Brian trying wrong password 3rd time");
        sessionDMO = this.getLoginService().login("Brian", "abcd123#112", null);
        Assert.assertEquals(false, sessionDMO.isLoggedIn());

        System.out.println("Brian trying wrong password 4th time");
        try {
            sessionDMO = this.getLoginService().login("Brian", "abcd123#112", null);
            sessionDMO = this.getLoginService().login("Brian", "abcd123#", null);
        } catch (ImpensaException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }

    }
}
