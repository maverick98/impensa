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
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.data.neo4j.aspects.config.Neo4jAspectConfiguration;

/**
 *
 * @author msahu98
 */
@Configuration
//@ImportResource({"classpath:spring/neo4j/spring-neo4j.xml"})
@Import(Neo4jAspectConfiguration.class)
@EnableTransactionManagement
@EnableNeo4jRepositories("org.impensa.db.repository")
@EnableSpringConfigured

public class ImpensaSpringConfig {

    @Bean
    public GraphDatabaseService graphDatabaseService() {
        System.out.println("creating database service");
        // if you want to use Neo4j as a REST service
        //return new SpringRestGraphDatabase("http://localhost:7474/db/data/");
        // Use Neo4j as Odin intended (as an embedded service)
        GraphDatabaseService service = new GraphDatabaseFactory().newEmbeddedDatabase("target/data/db_main");
        return service;
    }

    @Bean
    public ExpenseTemplateDAOImpl expenseTemplateDAOImpl() {
        return new ExpenseTemplateDAOImpl();
    }

    @Bean
    public FunctionDAOImpl  functionDAOImpl() {
        System.out.println("creating getFunctionDAOImpl service");
        return new FunctionDAOImpl();
    }

    @Bean
    public OrgDAOImpl orgDAOImpl() {
        return new OrgDAOImpl();
    }

    @Bean

    public RoleDAOImpl roleDAOImpl() {
        return new RoleDAOImpl();
    }

    @Bean

    public SessionDAOImpl sessionDAOImpl() {
        return new SessionDAOImpl();
    }

    @Bean

    public UserDAOImpl userDAOImpl() {
        return new UserDAOImpl();
    }

}
