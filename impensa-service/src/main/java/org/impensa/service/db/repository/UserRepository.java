/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.db.repository;

import org.impensa.service.db.entity.UserEntity;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 *
 * @author manosahu
 */
public interface UserRepository extends GraphRepository<UserEntity>{

    UserEntity findByUserId(String userId);

    Iterable<UserEntity> findByFirstNameLike(String userName);
    
    Iterable<UserEntity> findByMiddleNameLike(String userName);
    
    Iterable<UserEntity> findByLastNameLike(String userName);
    
    
    Iterable<UserEntity> findByPhoneLike(String userName);
    Iterable<UserEntity> findByEmailLike(String userName);
    
    Iterable<UserEntity> findByAddressLike(String userName);
    
    
    Iterable<UserEntity> findByAgeLike(Integer userName);
    
    
}

