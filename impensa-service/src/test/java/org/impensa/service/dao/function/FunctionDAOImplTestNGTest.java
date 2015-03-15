/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.dao.function;

import java.util.Set;
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
//TODO try to make it work with springjunitrunner.
//till then live with taking injection yourself.
//Sorry No hot nurse to give you injection !!!
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:spring/neo4j/spring-neo4j.xml"})
//@Transactional
public class FunctionDAOImplTestNGTest {

    // @Autowired
    private IFunctionDAO functionDAO;
    private static ClassPathXmlApplicationContext context;

    public IFunctionDAO getFunctionDAO() {
        return (IFunctionDAO) context.getBean("functionDAOImpl");

    }

    public FunctionDAOImplTestNGTest() {
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
    public void testCacheFunctions() throws Exception {
        Set<FunctionDMO> functionDMOs = this.getFunctionDAO().cacheFunctions();

        AssertJUnit.assertNotNull(functionDMOs);
        System.out.println(functionDMOs);
        for (FunctionDMO functionDMO : functionDMOs) {
            AssertJUnit.assertNotNull("functionName can NOT be null", functionDMO.getName());
            AssertJUnit.assertNotNull("function Description can NOT be null", functionDMO.getDescription());
            System.out.println(functionDMO.getName());
            System.out.println(functionDMO.getDescription());
        }
    }
}
