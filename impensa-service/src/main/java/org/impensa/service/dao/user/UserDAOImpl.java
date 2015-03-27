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
import org.common.bean.BeanConverter;
import org.commons.logger.ILogger;
import org.commons.logger.LoggerFactory;
import org.commons.string.StringUtil;
import org.impensa.service.dao.AbstractIdSetProcessor;
import org.impensa.service.dao.AbstractTxnExecutor;
import org.impensa.service.dao.exception.DAOException;
import org.impensa.service.db.entity.OrgEntity;
import org.impensa.service.db.entity.RoleEntity;
import org.impensa.service.db.entity.UserEntity;
import org.impensa.service.db.entity.UserAssignedOrgRelationship;
import org.impensa.service.db.repository.OrgRepository;
import org.impensa.service.db.repository.RoleRepository;
import org.impensa.service.db.repository.UserRepository;

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
        UserEntity user = this.convertTo(userDMO);
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

        final UserEntity user = this.getUserRepository().findByUserId(userUpdateDMO.getUserUpdate().getUserId());

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
                            OrgEntity org = getOrgRepository().findByOrgId(orgId);
                            user.assignOrg(org);
                            getUserRepository().save(user);
                            getOrgRepository().save(org);
                        }
                    }.process();

                    new AbstractIdSetProcessor(userUpdateDMO.getDeleteOrgIdSet()) {

                        @Override
                        public void onIdVisit(String orgId) throws UserDAOException {
                            OrgEntity org = getOrgRepository().findByOrgId(orgId);
                            user.removeOrg(org);
                            getUserRepository().save(user);
                            getOrgRepository().save(org);
                        }
                    }.process();

                    new AbstractIdSetProcessor(userUpdateDMO.getInsertRoleIdSet()) {

                        @Override
                        public void onIdVisit(String roleId) throws UserDAOException {
                            RoleEntity role = getRoleRepository().findByRoleId(roleId);
                            user.assignRole(role);
                            getUserRepository().save(user);
                            getRoleRepository().save(role);
                        }
                    }.process();

                    new AbstractIdSetProcessor(userUpdateDMO.getDeleteRoleIdSet()) {

                        @Override
                        public void onIdVisit(String roleId) throws UserDAOException {
                            RoleEntity role = getRoleRepository().findByRoleId(roleId);
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

        List<UserEntity> users = this.getUserRepository().findAll(pg).getContent();
        Set<UserEntity> tmpUsers = new HashSet<UserEntity>();
        if (users != null) {
            for (UserEntity user : users) {
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
        UserEntity user = this.getUserRepository().findByUserId(userDMO.getUserId());
        this.getUserRepository().delete(user);
        return true;
    }

    @Override
    public UserEntity convertTo(UserDMO userDMO) throws UserDAOException {
        UserEntity user;
        try {
            user = BeanConverter.toMappingBean(userDMO, UserEntity.class);
        } catch (Exception ex) {
            logger.error("error while converting to entity object " + userDMO, ex);
            throw new UserDAOException("error while converting to entity object " + userDMO, ex);
        }
        return user;
    }

    @Override
    public Set<UserDMO> convertFrom(Set<UserEntity> users) throws UserDAOException {
        Set<UserDMO> allUsers = new HashSet<UserDMO>();
        if (users != null) {
            for (UserEntity user : users) {
                allUsers.add(this.convertFrom(user));
            }
        }
        return allUsers;
    }

    @Override
    public UserDMO convertFrom(UserEntity user) throws UserDAOException {
        UserDMO userDMO;
        try {
            userDMO = BeanConverter.fromMappingBean(user, UserDMO.class);
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
        UserEntity user = this.getUserRepository().findByUserId(userId);
        return this.convertFrom(user);
    }

   
}
