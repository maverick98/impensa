package org.impensa.service;



import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.impensa.service.dao.function.IFunctionDAO;
import org.impensa.service.dao.user.UserDMO;
import org.impensa.service.db.entity.User;
import org.impensa.service.utl.DomainEntityConverter;

/**
 * @author: rkowalewski
 */
public class Main {

    private static final String CLASSPATH_LOCATION = "classpath:spring/neo4j/spring-neo4j.xml";

    public static void main(String[] args) throws Throwable{

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(CLASSPATH_LOCATION);

        //Neo4jPersister neo4jPersister = (Neo4jPersister) context.getBean("neo4jPersister");

        //neo4jPersister.createTestData();
        IFunctionDAO functionDAO = (IFunctionDAO)context.getBean("functionDAOImpl");
        functionDAO.cacheFunctions();
        UserDMO userDMO = new UserDMO();
        userDMO.setUserId("ms");
        userDMO.setAddress("asdf");
        userDMO.setAge(12);
       // userDMO.setEmail("abc@email.com");
       // userDMO.setFirstName("afdafds");
        userDMO.setLastName("asdfadsf");
        userDMO.setMiddleName("adsfadsfasdfasdf");
        userDMO.setPhone("1212121");
        User user = DomainEntityConverter.toEntity(userDMO, User.class);
        System.out.println(user.getEmail());
        user.setEmail("reetika@abc.com");
        UserDMO userDMO1 = DomainEntityConverter.toDomain(user, UserDMO.class);
        System.out.println(userDMO1.getEmail());
    }
}
