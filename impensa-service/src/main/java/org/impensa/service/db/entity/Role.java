/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.db.entity;

import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.support.index.IndexType;

/**
 *
 * @author manosahu
 */
@NodeEntity
public class Role extends IdentifiableEntity implements Comparable<Role>{

    @Indexed(unique = true)
    private String roleId;

    @Indexed(indexType = IndexType.FULLTEXT, indexName = "orgName")
    private String roleName;

    @Indexed(indexType = IndexType.FULLTEXT, indexName = "orgDescription")
    private String roleDescription;

    public Role(String id, String name) {
        this.roleId = id;
        this.roleName = name;
        this.roleDescription = "adsfadf";
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Role other = (Role) obj;
        if ((this.roleId == null) ? (other.roleId != null) : !this.roleId.equals(other.roleId)) {
            return false;
        }
        return true;
    }

    public Role(){}

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

    @Override
    public String toString() {
        return "Role{" + "roleId=" + roleId + ", roleName=" + roleName + ", roleDescription=" + roleDescription + '}';
    }

    @Override
    public int compareTo(Role otherRole) {
        return this.getRoleId().compareTo(otherRole.getRoleId());
    }
    
 
        

}
