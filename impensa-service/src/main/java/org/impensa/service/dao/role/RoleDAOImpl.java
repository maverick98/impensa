/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.dao.role;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.common.bean.BeanConverter;
import org.commons.collections.CollectionUtil;
import org.commons.logger.ILogger;
import org.commons.logger.LoggerFactory;
import org.commons.string.StringUtil;
import org.impensa.service.dao.AbstractIdSetProcessor;
import org.impensa.service.dao.AbstractTxnExecutor;
import org.impensa.service.dao.Pagination;
import org.impensa.service.db.entity.FunctionEntity;
import org.impensa.service.db.entity.RoleEntity;
import org.impensa.service.db.entity.RoleAssignedFunctionRelationship;
import org.impensa.service.db.repository.FunctionRepository;
import org.impensa.service.db.repository.RoleRepository;
import org.impensa.service.exception.BeanConversionErrorCode;
import org.impensa.service.exception.ImpensaException;
import org.impensa.service.exception.ValidationErrorCode;
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
public class RoleDAOImpl implements IRoleDAO {

    private static final ILogger logger = LoggerFactory.getLogger(RoleDAOImpl.class.getName());

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private FunctionRepository functionRepository;

    public FunctionRepository getFunctionRepository() {
        return functionRepository;
    }

    public void setFunctionRepository(FunctionRepository functionRepository) {
        this.functionRepository = functionRepository;
    }

    public RoleRepository getRoleRepository() {
        return roleRepository;
    }

    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public RoleDMO findByRoleId(String roleId) throws ImpensaException {
        RoleEntity role = this.getRoleRepository().findByRoleId(roleId);
        return this.convertFrom(role);
    }

    @Override
    public Set<RoleDMO> findBy(RoleSearchCriteria rsc) throws ImpensaException {

        final Pagination pagination = rsc.getPagination();
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

        List<RoleEntity> roles = this.getRoleRepository().findAll(pg).getContent();
        Set<RoleEntity> tmpRoles = new HashSet<RoleEntity>();
        if (roles != null) {
            for (RoleEntity role : roles) {
                if (!StringUtil.isNullOrEmpty(rsc.getRoleId())) {
                    if (rsc.getRoleId().equals(role.getRoleId())) {
                        tmpRoles.add(role);
                    }
                } else {
                    tmpRoles.add(role);
                }

                if (!StringUtil.isNullOrEmpty(rsc.getRoleName())) {
                    if (rsc.getRoleName().equals(role.getRoleName())) {
                        tmpRoles.add(role);
                    }
                } else {
                    tmpRoles.add(role);
                }

            }
        }
        Set<RoleDMO> all = this.convertFrom(tmpRoles);
        return all;

    }

    @Transactional
    @Override
    public RoleDMO createRole(RoleDMO roleDMO) throws ImpensaException {
        if (roleDMO == null) {
            throw new ImpensaException(ValidationErrorCode.VALUE_NULL).set("roleDMO", "null");
        }
        RoleEntity roleEntity = this.convertTo(roleDMO);
        if(roleEntity == null){
            throw new ImpensaException(ValidationErrorCode.VALUE_NULL).set("roleEntity", "null");
        }
        this.getRoleRepository().save(roleEntity);
        return roleDMO;
    }

    @Override
    public RoleDMO updateRole(final RoleUpdateDMO roleUpdateDMO) throws ImpensaException {

        final RoleEntity roleEntity = this.getRoleRepository().findByRoleId(roleUpdateDMO.getRoleDMO().getRoleId());

        new AbstractTxnExecutor() {

            @Override
            public void execute() throws ImpensaException {
                new AbstractIdSetProcessor(roleUpdateDMO.getInsertFunctionNameSet()) {

                    @Override
                    public void onIdVisit(String functionName) throws ImpensaException {
                        FunctionEntity functionEntity = getFunctionRepository().findByFunctionName(functionName);
                        if (functionEntity == null) {
                            throw new ImpensaException(ValidationErrorCode.VALUE_NULL).set("functionEntity", "null");
                        }
                        roleEntity.assignFunction(functionEntity);
                        getFunctionRepository().save(functionEntity);
                        getRoleRepository().save(roleEntity);
                    }
                }.process();
                new AbstractIdSetProcessor(roleUpdateDMO.getDeleteFunctionNameSet()) {

                    @Override
                    public void onIdVisit(String functionName) throws ImpensaException {
                        FunctionEntity functionEntity = getFunctionRepository().findByFunctionName(functionName);
                        if (functionEntity == null) {
                            throw new ImpensaException(ValidationErrorCode.VALUE_NULL).set("functionEntity", "null");
                        }
                        roleEntity.removeFunction(functionEntity);
                        getFunctionRepository().save(functionEntity);
                        getRoleRepository().save(roleEntity);
                    }
                }.process();
            }
        }.createTxn();

        return this.convertFrom(roleEntity);

    }

    @Transactional
    @Override
    public boolean deleteRole(RoleDMO roleDMO) throws ImpensaException {
        if (roleDMO == null) {
            throw new ImpensaException(ValidationErrorCode.VALUE_NULL).set("roleDMO", "null");
        }
        if (StringUtil.isNullOrEmpty(roleDMO.getRoleId())) {
            throw new ImpensaException(ValidationErrorCode.VALUE_NULL_OR_EMPTY).set("roleDMO's roleId", "null or empty");
        }
        RoleEntity roleEntity = this.getRoleRepository().findByRoleId(roleDMO.getRoleId());
        if (roleEntity == null) {
            throw new ImpensaException(ValidationErrorCode.VALUE_NULL).set("roleEntity", "null");
        }
        this.getRoleRepository().delete(roleEntity);
        return true;
    }

    @Override
    public RoleEntity convertTo(RoleDMO roleDMO) throws ImpensaException {
        if (roleDMO == null) {
            return null;
        }
        RoleEntity roleEntity;
        try {
            roleEntity = BeanConverter.toMappingBean(roleDMO, RoleEntity.class);
        } catch (Exception ex) {
            throw ImpensaException.wrap(ex)
                    .setErrorCode(BeanConversionErrorCode.FROM_MAPPING_BEAN)
                    .set("roleDMO", roleDMO);
        }
        return roleEntity;
    }

    @Override
    public RoleDMO convertFrom(RoleEntity roleEntity) throws ImpensaException {
        if (roleEntity == null) {
            return null;
        }
        RoleDMO roleDMO;
        try {
            roleDMO = BeanConverter.fromMappingBean(roleEntity, RoleDMO.class);
        } catch (Exception ex) {
            throw ImpensaException.wrap(ex)
                    .setErrorCode(BeanConversionErrorCode.FROM_MAPPING_BEAN)
                    .set("roleEntity", roleEntity);
        }

        for (RoleAssignedFunctionRelationship raf : roleEntity.getAssignedFunctions()) {
            roleDMO.getAssignedFunctionNames().add(raf.getFunction().getFunctionName());
        }

        return roleDMO;
    }

    @Override
    public Set<RoleDMO> convertFrom(Set<RoleEntity> roles) throws ImpensaException {
        if (CollectionUtil.isNullOrEmpty(roles)) {
            return null;
        }
        Set<RoleDMO> allRoles = new HashSet<RoleDMO>();
        if (roles != null) {
            for (RoleEntity role : roles) {
                allRoles.add(this.convertFrom(role));
            }
        }
        return allRoles;
    }

}
