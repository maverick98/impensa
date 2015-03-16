/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.dao.user;

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
public class UserDAOTestNGTest {

    private static ClassPathXmlApplicationContext context;



    public IUserDAO getUserDAO() {
         return (IUserDAO) context.getBean("userDAOImpl");
    }
     
    public UserDAOTestNGTest() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    private static final String CLASSPATH_LOCATION = "classpath:spring/neo4j/spring-neo4j.xml";

    @BeforeClass
    public static void setUpClass() throws Exception {
        context = new ClassPathXmlApplicationContext(CLASSPATH_LOCATION);

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
    public void testCreateUser() throws Exception{
        //IUserDAO userDAO = (IUserDAO) context.getBean("userDAOImpl");
        UserDMO userDMO = new UserDMO();
        userDMO.setUserId("ms");
        userDMO.setAddress("Uptown Village,17th main street,Sherman,Denver");
        userDMO.setAge(12);
        userDMO.setEmail("ms@ms.com");
        userDMO.setFirstName("Manoranjan");
        userDMO.setLastName("Sahu");
        userDMO.setMiddleName("");
        userDMO.setPhone("303-123-4782");
        this.getUserDAO().createUser(userDMO);
        System.out.println("createed user "+userDMO);
        UserDMO userDMO1 = this.getUserDAO().findByUserId(userDMO.getUserId());
        AssertJUnit.assertNotNull(userDMO1);
         AssertJUnit.assertNotNull(userDMO1.getAddress());
        System.out.println(userDMO1.getAddress());
    }
    @Test
    public void testDeleteUser() throws Exception{
         UserDMO userDMO = new UserDMO();
        userDMO.setUserId("ms");
        userDMO.setAddress("Uptown Village,17th main street,Sherman,Denver");
        userDMO.setAge(12);
        userDMO.setEmail("ms@ms.com");
        userDMO.setFirstName("Manoranjan");
        userDMO.setLastName("Sahu");
        userDMO.setMiddleName("");
        userDMO.setPhone("303-123-4782");
        this.getUserDAO().createUser(userDMO);
        System.out.println("createed user "+userDMO);
        UserDMO userDMO1 = this.getUserDAO().findByUserId(userDMO.getUserId());
        AssertJUnit.assertNotNull(userDMO1);
         AssertJUnit.assertNotNull(userDMO1.getAddress());
        System.out.println(userDMO1.getAddress());
        this.getUserDAO().deleteUser(userDMO);
        
    }
}
