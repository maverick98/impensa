package org.impensa.service;

import org.impensa.service.dao.function.IFunctionDAO;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: rkowalewski
 */
public class Main {

    private static final String CLASSPATH_LOCATION = "classpath:spring/neo4j/spring-neo4j.xml";

    public static void main(String[] args) throws Throwable {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(CLASSPATH_LOCATION);

        //Neo4jPersister neo4jPersister = (Neo4jPersister) context.getBean("neo4jPersister");
        //neo4jPersister.createTestData();
        IFunctionDAO functionDAO = (IFunctionDAO) context.getBean("functionDAOImpl");
        functionDAO.cacheFunctions();

        

    }
}
