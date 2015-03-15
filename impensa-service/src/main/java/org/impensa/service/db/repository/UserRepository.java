/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.db.repository;

import org.impensa.service.db.entity.User;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 *
 * @author manosahu
 */
public interface UserRepository extends GraphRepository<User>{

    User findByUserId(String userId);

    Iterable<User> findByFirstNameLike(String userName);
    
    Iterable<User> findByMiddleNameLike(String userName);
    
    Iterable<User> findByLastNameLike(String userName);
    
    
    Iterable<User> findByPhoneLike(String userName);
    Iterable<User> findByEmailLike(String userName);
    
    Iterable<User> findByAddressLike(String userName);
    
    
    Iterable<User> findByAgeLike(Integer userName);
    
    //Iterable<User> findByUserNameLike(String userName);
}

