/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.dao.user;

import org.impensa.service.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author manosahu
 */
@Component
@Transactional
public class UserDAOImpl implements IUserDAO{

    @Autowired
    private UserRepository UserRepository;

    public UserRepository getUserRepository() {
        return UserRepository;
    }

    public void setUserRepository(UserRepository UserRepository) {
        this.UserRepository = UserRepository;
    }
    
    
    @Override
    public UserDMO createUser(UserDMO userDMO) throws UserDAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UserDMO updateUser(UserUpdateDMO userUpdateDMO) throws UserDAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteUser(UserDMO userDMO) throws UserDAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteUser(String userId) throws UserDAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
