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
import org.commons.logger.ILogger;
import org.commons.logger.LoggerFactory;
import org.commons.string.StringUtil;
import org.impensa.service.dao.AbstractIdSetProcessor;
import org.impensa.service.dao.AbstractTxnExecutor;
import org.impensa.service.dao.Pagination;
import org.impensa.service.dao.exception.DAOException;
import org.impensa.service.dao.function.IFunctionDAO;
import org.impensa.service.dao.org.OrgDAOException;
import org.impensa.service.dao.user.UserDAOException;
import org.impensa.service.db.entity.RoleEntity;
import org.impensa.service.db.entity.RoleAssignedFunctionRelationship;
import org.impensa.service.db.repository.RoleRepository;
import org.impensa.service.util.DomainEntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 *
 * @author manosahu
 */
public class RoleDAOImpl implements IRoleDAO {

    private static final ILogger logger = LoggerFactory.getLogger(RoleDAOImpl.class.getName());

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private IFunctionDAO functionDAOImpl;

    public IFunctionDAO getFunctionDAOImpl() {
        return functionDAOImpl;
    }

    public void setFunctionDAOImpl(IFunctionDAO functionDAOImpl) {
        this.functionDAOImpl = functionDAOImpl;
    }
    
    
    

    public RoleRepository getRoleRepository() {
        return roleRepository;
    }

    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
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

    @Override
    public RoleDMO createRole(RoleDMO roleDMO) throws RoleDAOException {
        RoleEntity role = this.convertTo(roleDMO);
        this.getRoleRepository().save(role);
        return roleDMO;
    }

    @Override
    public RoleDMO updateRole(final RoleUpdateDMO roleUpdateDMO) throws RoleDAOException {
        /* 
        final Role role = this.getRoleRepository().findByRoleId(roleUpdateDMO.getRoleDMO().getRoleId());

        try {
            new AbstractTxnExecutor() {

                @Override
                public void execute() throws DAOException {
                    new AbstractIdSetProcessor(roleUpdateDMO.getInsertFunctionNameSet()) {

                        @Override
                        public void onIdVisit(String functionName) throws UserDAOException {
                            Role role = getFunctionDAOImpl().findByFunctionName(functionName);
                            org.assignRole(role);
                            getOrgRepository().save(org);
                            getRoleRepository().save(role);
                        }
                    }.process();
                }
            }.createTxn();
        } catch (DAOException ex) {
            throw new OrgDAOException(ex);
        }

        return this.convertFrom(org);
                */
        return null;
    }

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
            role = DomainEntityConverter.toEntity(roleDMO, RoleEntity.class);
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
            roleDMO = DomainEntityConverter.toDomain(role, RoleDMO.class);
        } catch (Exception ex) {
            logger.error("error while converting from entity object " + role, ex);
            throw new RoleDAOException("error while converting from entity object " + role, ex);
        }
        for( RoleAssignedFunctionRelationship raf :role.getAssignedFunctions()){
            roleDMO.getAssignedFunctionNames().add(raf.getFunction().getFunctionName());
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
