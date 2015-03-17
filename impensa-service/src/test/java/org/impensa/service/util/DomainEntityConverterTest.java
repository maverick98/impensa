
/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch . All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.util;


import org.impensa.service.dao.user.UserDMO;
import org.impensa.service.db.entity.User;
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
public class DomainEntityConverterTest {
    
    public DomainEntityConverterTest() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

    @BeforeClass
    public static void setUpClass() throws Exception {
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
    /**
     * 
     * @throws Exception 
     */
    @Test
    public void testDomainEntityConversion() throws Exception{
        UserDMO userDMO = new UserDMO();
        userDMO.setUserId("ms");
        userDMO.setAddress("Uptown Village,17th main street,Sherman,Denver");
        userDMO.setAge(12);
        userDMO.setEmail("ms@ms.com");
        userDMO.setFirstName("Manoranjan");
        userDMO.setLastName("Sahu");
        userDMO.setMiddleName("");
        userDMO.setPhone("303-123-4782");
        User user = DomainEntityConverter.toEntity(userDMO, User.class);
        System.out.println(user.getEmail());
        AssertJUnit.assertNotNull(user);
        AssertJUnit.assertNotNull(user.getUserId());
        AssertJUnit.assertNotNull(user.getAddress());
        AssertJUnit.assertNotNull(user.getAge());
        AssertJUnit.assertNotNull(user.getEmail());
        AssertJUnit.assertNotNull(user.getPhone());
        AssertJUnit.assertNotNull(user.getFirstName());
        AssertJUnit.assertNotNull(user.getLastName());
        AssertJUnit.assertNotNull(user.getMiddleName());
        AssertJUnit.assertEquals("ms@ms.com", user.getEmail());
        
        user.setEmail("ms@abc.com");
        UserDMO userDMO1 = DomainEntityConverter.toDomain(user, UserDMO.class);
        System.out.println(userDMO1.getEmail());
        AssertJUnit.assertNotNull(userDMO1);
        AssertJUnit.assertNotNull(userDMO1.getUserId());
        AssertJUnit.assertNotNull(userDMO1.getAddress());
        AssertJUnit.assertNotNull(userDMO1.getAge());
        AssertJUnit.assertNotNull(userDMO1.getEmail());
        AssertJUnit.assertNotNull(userDMO1.getPhone());
        AssertJUnit.assertNotNull(userDMO1.getFirstName());
        AssertJUnit.assertNotNull(userDMO1.getLastName());
        AssertJUnit.assertNotNull(userDMO1.getMiddleName());
        AssertJUnit.assertEquals("ms@abc.com", userDMO1.getEmail());
        
    }
    
   
}