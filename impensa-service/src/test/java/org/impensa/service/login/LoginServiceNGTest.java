/*
 *  Copyleft(BigBang<-->BigCrunch)  Manoranjan Sahu
 *  
 */
package org.impensa.service.login;

import org.impensa.service.AppContainer;
import org.impensa.service.ImpensaStartup;
import org.impensa.service.dao.org.IOrgDAO;
import org.impensa.service.dao.user.IUserDAO;
import org.impensa.service.dao.user.UserDMO;
import org.impensa.service.util.EncryptionUtil;
import org.springframework.context.support.ClassPathXmlApplicationContext;
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
        userDMO.setUserId("Reetika");
        userDMO.setAddress("Uptown Village,17th main street,Sherman,Denver");
        userDMO.setAge(12);
        userDMO.setEmail("rs@rs.com");
        userDMO.setFirstName("Reetika");
        userDMO.setLastName("Sahoo");
        userDMO.setMiddleName("");
        userDMO.setPhone("303-123-4782");
        userDMO.setEncryptedPassword(EncryptionUtil.encrypt("abcd123#"));
        this.getUserDAO().createUser(userDMO);
        this.getLoginService().login("Reetika", "abcd123#");
        
        boolean isLoggedIn = this.getLoginService().isLoggedIn("Reetika");
        
        System.out.println("Reetika logged in "+isLoggedIn);
        
        this.getLoginService().logout("Reetika");
        
        isLoggedIn = this.getLoginService().isLoggedIn("Reetika");
        
        System.out.println("Reetika must be logged out "+isLoggedIn);
        
    }
}
