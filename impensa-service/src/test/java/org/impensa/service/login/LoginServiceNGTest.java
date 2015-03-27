/*
 *  Copyleft(BigBang<-->BigCrunch)  Manoranjan Sahu
 *  
 */
package org.impensa.service.login;

import org.impensa.service.AppContainer;
import org.impensa.service.ImpensaStartup;
import org.impensa.service.dao.org.IOrgDAO;
import org.impensa.service.dao.session.SessionDMO;
import org.impensa.service.dao.user.IUserDAO;
import org.impensa.service.dao.user.UserDMO;
import org.common.crypto.EncryptionUtil;
import org.impensa.service.exception.ImpensaException;
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
        sessionDMO = this.getLoginService().login("Brian", "abcd123#");
        
        Assert.assertEquals(true, sessionDMO.isLoggedIn());
        boolean isLoggedIn = this.getLoginService().isLoggedIn("Brian");
        Assert.assertEquals(true, isLoggedIn);
        System.out.println("Brian logged in "+isLoggedIn);
        sessionDMO = this.getLoginService().login("Brian", "abcd123#");
        Assert.assertEquals(true, sessionDMO.isLoggedIn());
        System.out.println("Brian logged in again "+isLoggedIn);
        
        this.getLoginService().logout("Brian");
        
        isLoggedIn = this.getLoginService().isLoggedIn("Brian");
        
        System.out.println("Brian must be logged out "+isLoggedIn);
        System.out.println("Brian trying wrong password 1st time");
        sessionDMO = this.getLoginService().login("Brian", "abcd123#1");
        Assert.assertEquals(false, sessionDMO.isLoggedIn());
        
        System.out.println("Brian trying wrong password 2nd time");
        sessionDMO = this.getLoginService().login("Brian", "abcd123#11");
        Assert.assertEquals(false, sessionDMO.isLoggedIn());
        
        System.out.println("Brian trying wrong password 3rd time");
        sessionDMO = this.getLoginService().login("Brian", "abcd123#112");
        Assert.assertEquals(false, sessionDMO.isLoggedIn());
        
        
        System.out.println("Brian trying wrong password 4th time");
        try{
            sessionDMO = this.getLoginService().login("Brian", "abcd123#112");
            sessionDMO = this.getLoginService().login("Brian", "abcd123#");
        }catch(ImpensaException ex){
            System.out.println(ex.getMessage());
        }
        
        
        
    }
}
