/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.dao.user;

import java.util.HashSet;
import java.util.Set;
import org.commons.logger.ILogger;
import org.commons.logger.LoggerFactory;
import org.impensa.service.db.entity.Org;
import org.impensa.service.db.entity.User;
import org.impensa.service.db.repository.OrgRepository;
import org.impensa.service.db.repository.UserRepository;
import org.impensa.service.util.DomainEntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author manosahu
 */
@Component
public class UserDAOImpl implements IUserDAO {

    private static final ILogger logger = LoggerFactory.getLogger(UserDAOImpl.class.getName());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrgRepository orgRepository;

    public OrgRepository getOrgRepository() {
        return orgRepository;
    }

    public void setOrgRepository(OrgRepository orgRepository) {
        this.orgRepository = orgRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository UserRepository) {
        this.userRepository = UserRepository;
    }

    @Transactional
    @Override
    public UserDMO createUser(final UserDMO userDMO) throws UserDAOException {
        User user = this.convertTo(userDMO);
        this.getUserRepository().save(user);
        return userDMO;
    }

    @Transactional
    @Override
    public UserDMO updateUser(final UserUpdateDMO userUpdateDMO) throws UserDAOException {

        User user = this.convertTo(userUpdateDMO.getUserUpdate());
        Set<String> orgIds = userUpdateDMO.getInsertOrgIdSet();

        if (orgIds != null && orgIds.isEmpty()) {
            
            for (String orgId : orgIds) {

                Org org = new Org();
                org.setOrgId(orgId);
                
                user.assignOrg(org);
                this.getOrgRepository().save(org);
            }
            this.getUserRepository().save(user);
        }
        return this.convertFrom(user);
    }

    @Transactional
    @Override
    public boolean deleteUser(UserDMO userDMO) throws UserDAOException {
        //User user = this.convertTo(userDMO);
        User user = this.getUserRepository().findByUserId(userDMO.getUserId());
        this.getUserRepository().delete(user);
        return true;
    }

    @Override
    public User convertTo(UserDMO userDMO) throws UserDAOException {
        User user;
        try {
            user = DomainEntityConverter.toEntity(userDMO, User.class);
        } catch (Exception ex) {
            logger.error("error while converting to entity object " + userDMO, ex);
            throw new UserDAOException("error while converting to entity object " + userDMO, ex);
        }
        return user;
    }

    @Override
    public UserDMO convertFrom(User user) throws UserDAOException {
        UserDMO userDMO;
        try {
            userDMO = DomainEntityConverter.toDomain(user, UserDMO.class);
        } catch (Exception ex) {
            logger.error("error while converting from entity object " + user, ex);
            throw new UserDAOException("error while converting from entity object " + user, ex);
        }
        return userDMO;
    }

    @Override
    public UserDMO findByUserId(String userId) throws UserDAOException {
        User user = this.getUserRepository().findByUserId(userId);
        return this.convertFrom(user);
    }
}
