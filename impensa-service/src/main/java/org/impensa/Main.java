package org.impensa;

import org.impensa.startup.ImpensaStartup;

/**
 * @author: rkowalewski
 */
public class Main {

    
    public static void main(String[] args) throws Throwable {

        ImpensaStartup.startup();
        //Neo4jPersister neo4jPersister = (Neo4jPersister) context.getBean("neo4jPersister");
        //neo4jPersister.createTestData();
       // IFunctionDAO functionDAO = (IFunctionDAO) context.getBean("functionDAOImpl");
       // functionDAO.cacheFunctions();

        

    }
}
