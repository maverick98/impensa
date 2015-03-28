/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.dao.function;

import org.impensa.dao.function.FunctionDMO;
import org.impensa.dao.function.IFunctionDAO;
import java.util.Map;
import java.util.Map.Entry;
import org.impensa.AppContainer;
import org.impensa.startup.ImpensaStartup;
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
public class FunctionDAONGTest {

    // @Autowired
    private IFunctionDAO functionDAO;
    private static ClassPathXmlApplicationContext context;

    public IFunctionDAO getFunctionDAO() {
          return AppContainer.getInstance().getBean("functionDAOImpl", IFunctionDAO.class);
        //return (IFunctionDAO) context.getBean("functionDAOImpl");

    }

    public FunctionDAONGTest() {
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
    public void testCacheFunctions() throws Exception {
        Map<String,FunctionDMO> functionDMOMap = this.getFunctionDAO().cacheFunctions();
        
        AssertJUnit.assertNotNull(functionDMOMap);
        System.out.println(functionDMOMap);
        for (Entry<String,FunctionDMO>entry : functionDMOMap.entrySet()) {
            
            FunctionDMO functionDMO = entry.getValue();
            AssertJUnit.assertNotNull("functionName can NOT be null", functionDMO.getFunctionName());
            AssertJUnit.assertNotNull("function Description can NOT be null", functionDMO.getFunctionDescription());
            System.out.println(functionDMO.getFunctionName());
            System.out.println(functionDMO.getFunctionDescription());
            this.getFunctionDAO().createFunction(functionDMO);
            FunctionDMO functionDMO1 = this.getFunctionDAO().findByFunctionName(functionDMO.getFunctionName());
            System.out.println("found function dmo1 "+functionDMO1.getFunctionName());
            System.out.println("@@@@@@@@@@@@");
            
        }
        
    }
}
