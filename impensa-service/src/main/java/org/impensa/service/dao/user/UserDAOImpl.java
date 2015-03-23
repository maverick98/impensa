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
import org.impensa.service.db.entity.Role;
import org.impensa.service.db.entity.User;
import org.impensa.service.db.entity.UserAssignedOrgRelationship;
import org.impensa.service.db.repository.OrgRepository;
import org.impensa.service.db.repository.RoleRepository;
import org.impensa.service.db.repository.UserRepository;
import org.impensa.service.util.DomainEntityConverter;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
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

    @Autowired
    private RoleRepository roleRepository;

    public RoleRepository getRoleRepository() {
        return roleRepository;
    }

    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

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
    @Autowired
    private GraphDatabaseService graphDb;

    /**
     * TODO check why @Transactional is not working. Untill that is fixed..
     * create txn boundary yourself!!!
     *
     * @param userUpdateDMO
     * @return
     * @throws UserDAOException
     */
    //@Transactional
    @Override
    public UserDMO updateUser(final UserUpdateDMO userUpdateDMO) throws UserDAOException {
        Transaction tx = graphDb.beginTx();
        final User user = this.getUserRepository().findByUserId(userUpdateDMO.getUserUpdate().getUserId());

        /**
         * TODO the below lines look similar and ugly. Dont forget to refactor
         * me :)
         *
         */
        new AbstractIdSetProcessor(userUpdateDMO.getInsertOrgIdSet()) {

            @Override
            public void onIdVisit(String orgId) throws UserDAOException {
                Org org = getOrgRepository().findByOrgId(orgId);
                user.assignOrg(org);
                getUserRepository().save(user);
                getOrgRepository().save(org);
            }
        }.process();

        new AbstractIdSetProcessor(userUpdateDMO.getDeleteOrgIdSet()) {

            @Override
            public void onIdVisit(String orgId) throws UserDAOException {
                Org org = getOrgRepository().findByOrgId(orgId);
                user.removeOrg(org);
                getUserRepository().save(user);
                getOrgRepository().save(org);
            }
        }.process();

        new AbstractIdSetProcessor(userUpdateDMO.getInsertRoleIdSet()) {

            @Override
            public void onIdVisit(String roleId) throws UserDAOException {
                Role role = getRoleRepository().findByRoleId(roleId);
                user.assignRole(role);
                getUserRepository().save(user);
                getRoleRepository().save(role);
            }
        }.process();

        new AbstractIdSetProcessor(userUpdateDMO.getDeleteRoleIdSet()) {

            @Override
            public void onIdVisit(String roleId) throws UserDAOException {
                Role role = getRoleRepository().findByRoleId(roleId);
                user.removeRole(role);
                getUserRepository().save(user);
                getRoleRepository().save(role);
            }
        }.process();

        
        tx.success(); //or tx.failure();
        tx.finish();
        return this.convertFrom(user);
    }

    private static abstract class AbstractIdSetProcessor {

        private Set<String> ids = new HashSet<String>();

        public AbstractIdSetProcessor(Set<String> ids) {
            this.ids = ids;
        }

        public Set<String> getIds() {
            return ids;
        }

        public void setIds(Set<String> ids) {
            this.ids = ids;
        }

        public void process() throws UserDAOException {
            if (this.getIds() != null && !this.getIds().isEmpty()) {
                for (String id : this.getIds()) {
                    this.onIdVisit(id);
                }
            }
        }

        public abstract void onIdVisit(String id) throws UserDAOException;

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
        for (UserAssignedOrgRelationship uar : user.getAssignedOrgs()) {
            userDMO.getAssignedOrgIds().add(uar.getOrg().getOrgId());
        }
        return userDMO;
    }

    @Override
    public UserDMO findByUserId(String userId) throws UserDAOException {
        User user = this.getUserRepository().findByUserId(userId);
        return this.convertFrom(user);
    }
}
