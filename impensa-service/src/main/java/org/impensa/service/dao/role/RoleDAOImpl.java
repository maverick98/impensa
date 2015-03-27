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
import org.commons.logger.ILogger;
import org.commons.logger.LoggerFactory;
import org.commons.string.StringUtil;
import org.impensa.service.dao.AbstractIdSetProcessor;
import org.impensa.service.dao.AbstractTxnExecutor;
import org.impensa.service.dao.Pagination;
import org.impensa.service.dao.exception.DAOException;
import org.impensa.service.db.entity.FunctionEntity;
import org.impensa.service.db.entity.RoleEntity;
import org.impensa.service.db.entity.RoleAssignedFunctionRelationship;
import org.impensa.service.db.repository.FunctionRepository;
import org.impensa.service.db.repository.RoleRepository;

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
    public RoleDMO findByRoleId(String roleId) throws RoleDAOException {
        RoleEntity role = this.getRoleRepository().findByRoleId(roleId);
        return this.convertFrom(role);
    }

    @Override
    public Set<RoleDMO> findBy(RoleSearchCriteria rsc) throws RoleDAOException {

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
    public RoleDMO createRole(RoleDMO roleDMO) throws RoleDAOException {
        RoleEntity role = this.convertTo(roleDMO);
        this.getRoleRepository().save(role);
        return roleDMO;
    }

    @Override
    public RoleDMO updateRole(final RoleUpdateDMO roleUpdateDMO) throws RoleDAOException {

        final RoleEntity roleEntity = this.getRoleRepository().findByRoleId(roleUpdateDMO.getRoleDMO().getRoleId());

        try {
            new AbstractTxnExecutor() {

                @Override
                public void execute() throws DAOException {
                    new AbstractIdSetProcessor(roleUpdateDMO.getInsertFunctionNameSet()) {

                        @Override
                        public void onIdVisit(String functionName) throws DAOException {
                            FunctionEntity functionEntity = getFunctionRepository().findByFunctionName(functionName);
                            roleEntity.assignFunction(functionEntity);
                            getFunctionRepository().save(functionEntity);
                            getRoleRepository().save(roleEntity);
                        }
                    }.process();
                    new AbstractIdSetProcessor(roleUpdateDMO.getDeleteFunctionNameSet()) {

                        @Override
                        public void onIdVisit(String functionName) throws DAOException {
                            FunctionEntity functionEntity = getFunctionRepository().findByFunctionName(functionName);
                            roleEntity.removeFunction(functionEntity);
                            getFunctionRepository().save(functionEntity);
                            getRoleRepository().save(roleEntity);
                        }
                    }.process();
                }
            }.createTxn();
        } catch (DAOException ex) {
            throw new RoleDAOException(ex);
        }

        return this.convertFrom(roleEntity);

    }
    @Transactional
    @Override
    public boolean deleteRole(RoleDMO roleDMO) throws RoleDAOException {
        RoleEntity role = this.getRoleRepository().findByRoleId(roleDMO.getRoleId());
        this.getRoleRepository().delete(role);
        return true;
    }

    @Override
    public RoleEntity convertTo(RoleDMO roleDMO) throws RoleDAOException {
        RoleEntity role;
        try {
            role = BeanConverter.toMappingBean(roleDMO, RoleEntity.class);
        } catch (Exception ex) {
            logger.error("error while converting to entity object " + roleDMO, ex);
            throw new RoleDAOException("error while converting to entity object " + roleDMO, ex);
        }
        return role;
    }

    @Override
    public RoleDMO convertFrom(RoleEntity role) throws RoleDAOException {
        RoleDMO roleDMO;
        try {
            roleDMO = BeanConverter.fromMappingBean(role, RoleDMO.class);
        } catch (Exception ex) {
            logger.error("error while converting from entity object " + role, ex);
            throw new RoleDAOException("error while converting from entity object " + role, ex);
        }
        if (role != null && !role.getAssignedFunctions().isEmpty()) {
            for (RoleAssignedFunctionRelationship raf : role.getAssignedFunctions()) {
                roleDMO.getAssignedFunctionNames().add(raf.getFunction().getFunctionName());
            }
        }

        return roleDMO;
    }

    @Override
    public Set<RoleDMO> convertFrom(Set<RoleEntity> roles) throws RoleDAOException {
        Set<RoleDMO> allRoles = new HashSet<RoleDMO>();
        if (roles != null) {
            for (RoleEntity role : roles) {
                allRoles.add(this.convertFrom(role));
            }
        }
        return allRoles;
    }

}
