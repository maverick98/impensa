/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.dao.org;

import org.impensa.dao.org.IOrgDAO;
import org.impensa.dao.org.OrgDMO;
import org.impensa.AppContainer;
import org.impensa.ImpensaStartup;
import org.impensa.dao.function.IFunctionDAO;
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
public class OrgDAONGTest {

    private static ClassPathXmlApplicationContext context;



    public IOrgDAO getOrgDAO() {
        return AppContainer.getInstance().getBean("orgDAOImpl", IOrgDAO.class);
        //return (IOrgDAO) context.getBean("orgDAOImpl");
    }
     
    public OrgDAONGTest() {
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
    public void testCreateOrg() throws Exception{
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
    }
    @Test
    public void testDeleteOrg() throws Exception{
        OrgDMO orgDMO = new OrgDMO();
        orgDMO.setOrgId("og1");
        orgDMO.setOrgName("Kids");
        orgDMO.setOrgDescription("KIDS group");
        this.getOrgDAO().createOrg(orgDMO);
        OrgDMO orgDMO1 = this.getOrgDAO().findByOrgId("og1");
        AssertJUnit.assertNotNull(orgDMO1);
        this.getOrgDAO().deleteOrg(orgDMO1);
        orgDMO1 = this.getOrgDAO().findByOrgId("og1");
        AssertJUnit.assertNull(orgDMO1);
        
    }
}
