/**
 * This file is part of impensa. CopyLeft (C) BigBang<->BigCrunch.All Rights are
 * left.
 *
 * 1) Modify it if you can understand. 2) If you distribute a modified version,
 * you must do it at your own risk.
 *
 */
package org.impensa.config;

import org.impensa.dao.expensetemplate.ExpenseTemplateDAOImpl;
import org.impensa.dao.function.FunctionDAOImpl;
import org.impensa.dao.org.OrgDAOImpl;
import org.impensa.dao.role.RoleDAOImpl;
import org.impensa.dao.session.SessionDAOImpl;
import org.impensa.dao.user.UserDAOImpl;
import org.impensa.service.function.FunctionServiceImpl;
import org.impensa.service.login.LoginServiceImpl;
import org.impensa.service.role.RoleServiceImpl;
import org.impensa.service.txn.TxnServiceImpl;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.aspects.config.Neo4jAspectConfiguration;

/**
 *
 * @author msahu98
 */
@Configuration
@Import(Neo4jAspectConfiguration.class)
@EnableNeo4jRepositories("org.impensa.db.repository")
@EnableSpringConfigured
public class ImpensaSpringConfigTest {

    @Bean
    public GraphDatabaseService graphDatabaseService() {
        System.out.println("creating database service");
        GraphDatabaseService service = new GraphDatabaseFactory().newEmbeddedDatabase("target/data/db_main_test");
        return service;
    }

    @Bean
    public ExpenseTemplateDAOImpl expenseTemplateDAOImpl() {
        System.out.println("creating ExpenseTemplateDAOImpl");
        return new ExpenseTemplateDAOImpl();
    }

    @Bean
    public FunctionDAOImpl functionDAOImpl() {
        System.out.println("creating FunctionDAOImpl");
        return new FunctionDAOImpl();
    }

    @Bean
    public OrgDAOImpl orgDAOImpl() {
        System.out.println("creating OrgDAOImpl");
        return new OrgDAOImpl();
    }

    @Bean
    public RoleDAOImpl roleDAOImpl() {
        System.out.println("creating RoleDAOImpl");
        return new RoleDAOImpl();
    }

    @Bean
    public SessionDAOImpl sessionDAOImpl() {
        System.out.println("creating SessionDAOImpl");
        return new SessionDAOImpl();
    }

    @Bean
    public UserDAOImpl userDAOImpl() {
        System.out.println("creating UserDAOImpl");
        return new UserDAOImpl();
    }

    @Bean
    public FunctionServiceImpl functionServiceImpl() {
        System.out.println("creating FunctionServiceImpl");
        return new FunctionServiceImpl();
    }

    @Bean
    public LoginServiceImpl loginServiceImpl() {
        System.out.println("creating LoginServiceImpl");
        return new LoginServiceImpl();
    }

    @Bean
    public RoleServiceImpl roleServiceImpl() {
        System.out.println("creating RoleServiceImpl");
        return new RoleServiceImpl();
    }

    @Bean
    public TxnServiceImpl txnServiceImpl() {
        System.out.println("creating TxnServiceImpl");
        return new TxnServiceImpl();
    }

}
