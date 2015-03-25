/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.dao.user;

import org.impensa.service.dao.Pagination;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.commons.logger.ILogger;
import org.commons.logger.LoggerFactory;
import org.commons.string.StringUtil;
import org.impensa.service.dao.AbstractIdSetProcessor;
import org.impensa.service.dao.AbstractTxnExecutor;
import org.impensa.service.dao.exception.DAOException;
import org.impensa.service.db.entity.Org;
import org.impensa.service.db.entity.Role;
import org.impensa.service.db.entity.User;
import org.impensa.service.db.entity.UserAssignedOrgRelationship;
import org.impensa.service.db.repository.OrgRepository;
import org.impensa.service.db.repository.RoleRepository;
import org.impensa.service.db.repository.UserRepository;
import org.impensa.service.util.DomainEntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

        final User user = this.getUserRepository().findByUserId(userUpdateDMO.getUserUpdate().getUserId());

        /**
         * TODO the below lines look similar and ugly. Dont forget to refactor
         * me :)
         *
         */
        try {
            new AbstractTxnExecutor() {

                @Override
                public void execute() throws DAOException {

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
                }
            }.createTxn();
        } catch (DAOException ex) {
            throw new UserDAOException(ex);
        }

        return this.convertFrom(user);
    }

    @Override
    public Set<UserDMO> findBy(final UserSearchCriteria usc) throws UserDAOException {
        Set<UserDMO> all = new HashSet<UserDMO>();
        final Pagination pagination = usc.getPagination();
        Pageable pg = new Pageable() {

            @Override
            public int getPageNumber() {
                return pagination.getPageNumber();
            }

            @Override
            public int getPageSize() {
                return pagination.getPageSize();
            }

            @Override
            public int getOffset() {
                return pagination.getOffset();
            }

            @Override
            public Sort getSort() {
                //will check it later
                return null;
            }
        };

        List<User> users = this.getUserRepository().findAll(pg).getContent();
        Set<User> tmpUsers = new HashSet<User>();
        if (users != null) {
            for (User user : users) {
                if (!StringUtil.isNullOrEmpty(usc.getUserId())) {
                    if (usc.getUserId().equals(user.getUserId())) {
                        tmpUsers.add(user);
                    }
                } else {
                    tmpUsers.add(user);
                }

            }
        }
        all = this.convertFrom(tmpUsers);
        return all;
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
    public Set<UserDMO> convertFrom(Set<User> users) throws UserDAOException {
        Set<UserDMO> allUsers = new HashSet<UserDMO>();
        if (users != null) {
            for (User user : users) {
                allUsers.add(this.convertFrom(user));
            }
        }
        return allUsers;
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
