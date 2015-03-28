/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.dao.role;

import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;
import org.common.bean.MappingBean;
import org.impensa.db.entity.RoleEntity;


/**
 *
 * @author manosahu
 */
@XmlRootElement
@MappingBean(name = RoleEntity.class)
public class RoleDMO {

    private String roleId;

    private String roleName;

    private String roleDescription;

    private Set<String> assignedFunctionNames = new HashSet<String>();

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public Set<String> getAssignedFunctionNames() {
        return assignedFunctionNames;
    }

    public void setAssignedFunctionNames(Set<String> assignedFunctionNames) {
        this.assignedFunctionNames = assignedFunctionNames;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (this.roleId != null ? this.roleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RoleDMO other = (RoleDMO) obj;
        if ((this.roleId == null) ? (other.roleId != null) : !this.roleId.equals(other.roleId)) {
            return false;
        }
        return true;
    }

    
}
