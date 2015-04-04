/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.dao.user;

import org.impensa.dao.Pagination;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.common.bean.BeanConverter;
import org.commons.collections.CollectionUtil;
import org.commons.logger.ILogger;
import org.commons.logger.LoggerFactory;
import org.commons.string.StringUtil;
import org.impensa.dao.AbstractIdSetProcessor;
import org.impensa.dao.AbstractTxnExecutor;
import org.impensa.db.entity.FunctionEntity;
import org.impensa.db.entity.OrgEntity;
import org.impensa.db.entity.RoleEntity;
import org.impensa.db.entity.UserEntity;
import org.impensa.db.entity.UserAssignedOrgRelationship;
import org.impensa.db.entity.UserAssignedRoleRelationship;
import org.impensa.db.repository.OrgRepository;
import org.impensa.db.repository.RoleRepository;
import org.impensa.db.repository.UserRepository;
import org.impensa.exception.BeanConversionErrorCode;
import org.impensa.exception.DataFetchErrorCode;
import org.impensa.exception.ImpensaException;
import org.impensa.exception.ValidationErrorCode;
import org.impensa.txn.Txn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author manosahu
 */
//@Component
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

    @Txn
    @Override
    public UserDMO createUser(final UserDMO userDMO) throws ImpensaException {
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
     * @throws ImpensaException
     */
    @Txn
    @Override
    public UserDMO updateUser(final UserUpdateDMO userUpdateDMO) throws ImpensaException {

        if (userUpdateDMO == null) {
            throw new ImpensaException(ValidationErrorCode.VALUE_NULL).set("userUpdateDMO", "null");
        }
        if (userUpdateDMO.getUserUpdate() == null) {
            throw new ImpensaException(ValidationErrorCode.VALUE_NULL).set("userUpdateDMO's userUpdate", "null");
        }
        if (userUpdateDMO.getUserUpdate().getUserId() == null) {
            throw new ImpensaException(ValidationErrorCode.VALUE_NULL).set("userUpdateDMO's userUpdate's userId", "null");
        }
        final UserEntity userEntity = this.getUserRepository().findByUserId(userUpdateDMO.getUserUpdate().getUserId());

        if (userEntity == null) {
            throw new ImpensaException(DataFetchErrorCode.DATA_NOT_FOUND).set("No userEntity foud for ", userUpdateDMO.getUserUpdate().getUserId());
        }
        /**
         * TODO the below lines look similar and ugly. Dont forget to refactor
         * me :)
         *
         */

        new AbstractIdSetProcessor(userUpdateDMO.getInsertOrgIdSet()) {

            @Override
            public void onIdVisit(String orgId) throws ImpensaException {
                OrgEntity org = getOrgRepository().findByOrgId(orgId);
                userEntity.assignOrg(org);
                getUserRepository().save(userEntity);
                getOrgRepository().save(org);
            }
        }.process();

        new AbstractIdSetProcessor(userUpdateDMO.getDeleteOrgIdSet()) {

            @Override
            public void onIdVisit(String orgId) throws ImpensaException {
                OrgEntity org = getOrgRepository().findByOrgId(orgId);
                userEntity.removeOrg(org);
                getUserRepository().save(userEntity);
                getOrgRepository().save(org);
            }
        }.process();

        new AbstractIdSetProcessor(userUpdateDMO.getInsertRoleIdSet()) {

            @Override
            public void onIdVisit(String roleId) throws ImpensaException {
                RoleEntity role = getRoleRepository().findByRoleId(roleId);
                userEntity.assignRole(role);
                getUserRepository().save(userEntity);
                getRoleRepository().save(role);
            }
        }.process();

        new AbstractIdSetProcessor(userUpdateDMO.getDeleteRoleIdSet()) {

            @Override
            public void onIdVisit(String roleId) throws ImpensaException {
                RoleEntity role = getRoleRepository().findByRoleId(roleId);
                userEntity.removeRole(role);
                getUserRepository().save(userEntity);
                getRoleRepository().save(role);
            }
        }.process();

        return this.convertFrom(userEntity);
    }

    @Override
    public Set<UserDMO> findBy(final UserSearchCriteria usc) throws ImpensaException {
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

    @Txn
    @Override
    public boolean deleteUser(UserDMO userDMO) throws ImpensaException {
        if (userDMO == null) {
            throw new ImpensaException(ValidationErrorCode.VALUE_NULL).set("userDMO", "null");
        }
        //User user = this.convertTo(userDMO);
        UserEntity user = this.getUserRepository().findByUserId(userDMO.getUserId());
        this.getUserRepository().delete(user);
        return true;
    }

    @Override
    public UserEntity convertTo(UserDMO userDMO) throws ImpensaException {
        if (userDMO == null) {
            return null;
        }
        UserEntity user;

        try {
            user = BeanConverter.toMappingBean(userDMO, UserEntity.class
            );
        } catch (Exception ex) {
            throw ImpensaException.wrap(ex)
                    .setErrorCode(BeanConversionErrorCode.FROM_MAPPING_BEAN)
                    .set("userDMO", userDMO);
        }
        return user;
    }

    @Override
    public Set<UserDMO> convertFrom(Set<UserEntity> users) throws ImpensaException {
        if (CollectionUtil.isNullOrEmpty(users)) {
            return null;
        }
        Set<UserDMO> allUsers = new HashSet<UserDMO>();
        if (users != null) {
            for (UserEntity user : users) {
                allUsers.add(this.convertFrom(user));
            }
        }
        return allUsers;
    }

    @Override
    public UserDMO convertFrom(UserEntity userEntity) throws ImpensaException {
        if (userEntity == null) {
            return null;
        }
        UserDMO userDMO;

        try {
            userDMO = BeanConverter.fromMappingBean(userEntity, UserDMO.class
            );
        } catch (Exception ex) {
            throw ImpensaException.wrap(ex)
                    .setErrorCode(BeanConversionErrorCode.FROM_MAPPING_BEAN)
                    .set("userEntity", userEntity);
        }
        for (UserAssignedOrgRelationship uar : userEntity.getAssignedOrgs()) {
            userDMO.getAssignedOrgIds().add(uar.getOrg().getOrgId());
        }
        for (UserAssignedRoleRelationship uar : userEntity.getAssignedRoles()) {
            userDMO.getAssignedRoleIds().add(uar.getRole().getRoleId());
        }
        for (FunctionEntity functionEntity : userEntity.findAssignedFunctions()) {
            userDMO.getAssignedFunctionNames().add(functionEntity.getFunctionName());
        }
        return userDMO;
    }

    @Override
    public UserDMO findByUserId(String userId) throws ImpensaException {
        if (StringUtil.isNullOrEmpty(userId)) {
            throw new ImpensaException(ValidationErrorCode.VALUE_NULL_OR_EMPTY).set("userId", "null or empty");
        }
        UserEntity user = this.getUserRepository().findByUserId(userId);
        return this.convertFrom(user);
    }

}
