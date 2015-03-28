/*
 *  Copyleft(BigBang<-->BigCrunch)  Manoranjan Sahu
 *  
 */
package org.impensa.service.txn;

import org.impensa.dao.role.RoleUpdateDMO;
import org.impensa.dao.role.RoleDMO;
import org.impensa.dao.role.IRoleDAO;
import java.util.Map;
import org.impensa.AppContainer;
import org.impensa.aspect.permission.PermissionAspect;
import org.impensa.startup.ImpensaStartup;
import org.impensa.dao.function.FunctionDMO;
import org.impensa.dao.function.IFunctionDAO;
import org.impensa.service.role.IRoleService;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.AssertJUnit;
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
    public void testCreateTxn() throws Exception {
        PermissionAspect permissionAspect = AppContainer.getInstance().getBean("permissionAspect", PermissionAspect.class);
        System.out.println("permission aspect" + permissionAspect);
        this.getTxnService().createTxn();

    }

}
