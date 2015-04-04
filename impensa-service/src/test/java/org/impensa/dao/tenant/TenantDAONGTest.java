/*
 *  Copyleft(BigBang<-->BigCrunch)  Manoranjan Sahu
 *  
 */
package org.impensa.dao.tenant;

import org.common.crypto.EncryptionUtil;
import org.impensa.AppContainer;
import org.impensa.dao.session.SessionDMO;
import org.impensa.service.login.ILoginService;
import org.impensa.startup.ImpensaStartup;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;

/**
 *
 * @author manosahu
 */
public class TenantDAONGTest {

    private static ClassPathXmlApplicationContext context;

    public ITenantDAO getTenantDAO() {
        return AppContainer.getInstance().getBean("tenantDAOImpl", ITenantDAO.class);

    }
     public ILoginService getLoginService() {
        return AppContainer.getInstance().getBean("loginServiceImpl", ILoginService.class);
    }

    public TenantDAONGTest() {
    }

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
    public void testCreateTenant() throws Exception {
        TenantDMO tenantDMO = new TenantDMO();
        tenantDMO.setTenantId("firstTenant");
        tenantDMO.setEmail("abc@xxx.com");
        tenantDMO.setName("firstTenant");
        tenantDMO.setAddress("HSR layout , bangalore");
        tenantDMO.setPhone("9740319263");
        tenantDMO.setEncryptedPassword(EncryptionUtil.encrypt("Abcd123#"));
        this.getTenantDAO().createTenant(tenantDMO);
        this.getTenantDAO().createTenantDatabase(tenantDMO);
        TenantDMO tenantDMO1 = this.getTenantDAO().findByTenantId("firstTenant");
        System.out.println("tenant found is "+tenantDMO1);
        AssertJUnit.assertNotNull(tenantDMO1);
        System.out.println("tenant's encrypted password  is "+tenantDMO1.getEncryptedPassword());
        SessionDMO sessionDMO = this.getLoginService().loginTenantFirstTime("firstTenant", "Abcd123#");
        AssertJUnit.assertNotNull(sessionDMO);
    }

}
