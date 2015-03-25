/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.dao.user;

import org.impensa.service.AppContainer;
import org.impensa.service.ImpensaStartup;
import org.impensa.service.dao.Pagination;
import org.impensa.service.dao.org.IOrgDAO;
import org.impensa.service.dao.org.OrgDMO;
import org.impensa.service.db.entity.UserEntity;
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
public class UserDAONGTest {

    private static ClassPathXmlApplicationContext context;

    public IUserDAO getUserDAO() {
         return AppContainer.getInstance().getBean("userDAOImpl", IUserDAO.class);
        //return (IUserDAO) context.getBean("userDAOImpl");
    }
    
     public IOrgDAO getOrgDAO() {
         return AppContainer.getInstance().getBean("orgDAOImpl", IOrgDAO.class);
    }

    public UserDAONGTest() {
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
    public void testCreateUser() throws Exception {
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
        System.out.println("createed user " + userDMO);
        UserDMO userDMO1 = this.getUserDAO().findByUserId(userDMO.getUserId());
        AssertJUnit.assertNotNull(userDMO1);
        AssertJUnit.assertNotNull(userDMO1.getAddress());
        System.out.println(userDMO1.getAddress());
        
        
        OrgDMO orgDMO = new OrgDMO();
        orgDMO.setOrgId("og1");
        orgDMO.setOrgName("Kids");
        orgDMO.setOrgDescription("KIDS group");
        this.getOrgDAO().createOrg(orgDMO);
        OrgDMO orgDMO1 = this.getOrgDAO().findByOrgId("og1");
        AssertJUnit.assertNotNull(orgDMO1);
        System.out.println("org name found is "+orgDMO1.getOrgName());
        System.out.println("checking for orgname with Kids");
        AssertJUnit.assertEquals(orgDMO1.getOrgName(), "Kids");
        
        UserUpdateDMO userUpdateDMO = new UserUpdateDMO();
        userUpdateDMO.setUserUpdate(userDMO1);
        userUpdateDMO.getInsertOrgIdSet().add(orgDMO.getOrgId());
        this.getUserDAO().updateUser(userUpdateDMO);
        UserDMO updatedUserDMO = this.getUserDAO().findByUserId(userDMO.getUserId());
        
        
        
        System.out.println("orgs are as follows"+updatedUserDMO.getAssignedOrgIds());
        
         UserUpdateDMO userUpdateDMO1 = new UserUpdateDMO();
        userUpdateDMO1.setUserUpdate(userDMO1);
        userUpdateDMO1.getDeleteOrgIdSet().add(orgDMO.getOrgId());
        
         this.getUserDAO().updateUser(userUpdateDMO1);
        UserDMO updatedUserDMO1 = this.getUserDAO().findByUserId(userDMO.getUserId());
         System.out.println("after updattion orgs are as follows"+updatedUserDMO1.getAssignedOrgIds());
         UserSearchCriteria usc = new UserSearchCriteria();
         Pagination pagination = new Pagination();
         pagination.setOffset(0);
         pagination.setPageNumber(1);
         pagination.setPageSize(15);
         usc.setPagination(pagination);
         usc.setUserId("ms");
         System.out.println( "found some users ...."+this.getUserDAO().findBy(usc));
    }

    @Test
    public void testDeleteUser() throws Exception {
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
        System.out.println("createed user " + userDMO);
        UserDMO userDMO1 = this.getUserDAO().findByUserId(userDMO.getUserId());
        AssertJUnit.assertNotNull(userDMO1);
        AssertJUnit.assertNotNull(userDMO1.getAddress());
        System.out.println(userDMO1.getAddress());
        this.getUserDAO().deleteUser(userDMO);

    }
}