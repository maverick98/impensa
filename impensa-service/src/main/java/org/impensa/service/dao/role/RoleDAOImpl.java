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
import org.impensa.service.dao.org.OrgDAOException;
import org.impensa.service.dao.user.UserDAOException;
import org.impensa.service.dao.user.UserDMO;
import org.impensa.service.db.entity.Org;
import org.impensa.service.db.entity.Role;
import org.impensa.service.db.entity.RoleAssignedFunctionRelationship;
import org.impensa.service.db.entity.User;
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
        
        List<Role> roles = this.getRoleRepository().findAll(pg).getContent();
        Set<Role> tmpRoles = new HashSet<Role>();
        if (roles != null) {
            for (Role role : roles) {
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
        Role role = this.convertTo(roleDMO);
        this.getRoleRepository().save(role);
        return roleDMO;
    }

    @Override
    public RoleDMO updateRole(RoleUpdateDMO userUpdateDMO) throws RoleDAOException {
         final Org org = this.getOrgRepository().findByOrgId(orgUpdateDMO.getOrgUpdate().getOrgId());

        try {
            new AbstractTxnExecutor() {

                @Override
                public void execute() throws DAOException {
                    new AbstractIdSetProcessor(orgUpdateDMO.getInsertRoleIdSet()) {

                        @Override
                        public void onIdVisit(String roleId) throws UserDAOException {
                            Role role = getRoleRepository().findByRoleId(roleId);
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
    }

    @Override
    public boolean deleteRole(RoleDMO roleDMO) throws RoleDAOException {
        Role role = this.getRoleRepository().findByRoleId(roleDMO.getRoleId());
        this.getRoleRepository().delete(role);
        return true;
    }

    @Override
    public Role convertTo(RoleDMO roleDMO) throws RoleDAOException {
        Role role;
        try {
            role = DomainEntityConverter.toEntity(roleDMO, Role.class);
        } catch (Exception ex) {
            logger.error("error while converting to entity object " + roleDMO, ex);
            throw new RoleDAOException("error while converting to entity object " + roleDMO, ex);
        }
        return role;
    }

    @Override
    public RoleDMO convertFrom(Role role) throws RoleDAOException {
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
    public Set<RoleDMO> convertFrom(Set<Role> roles) throws RoleDAOException {
        Set<RoleDMO> allRoles = new HashSet<RoleDMO>();
        if (roles != null) {
            for (Role role : roles) {
                allRoles.add(this.convertFrom(role));
            }
        }
        return allRoles;
    }

}
