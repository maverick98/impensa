/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.db.entity;

import java.util.HashSet;
import java.util.Set;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedToVia;
import org.springframework.data.neo4j.support.index.IndexType;

/**
 *
 * @author manosahu
 */
@NodeEntity
public class RoleEntity extends IdentifiableEntity implements Comparable<RoleEntity> {

    @Indexed(unique = true)
    private String roleId;

    @Indexed(indexType = IndexType.FULLTEXT, indexName = "roleName")
    private String roleName;

    @Indexed(indexType = IndexType.FULLTEXT, indexName = "roleDescription")
    private String roleDescription;

    @RelatedToVia(type = RelationshipTypes.ROLE_FUNCTION_ASSIGNED)
    private Set<RoleAssignedFunctionRelationship> assignedFunctions = new HashSet<RoleAssignedFunctionRelationship>();

    public Set<RoleAssignedFunctionRelationship> getAssignedFunctions() {
        return assignedFunctions;
    }

    public void setAssignedFunctions(Set<RoleAssignedFunctionRelationship> assignedFunctions) {
        this.assignedFunctions = assignedFunctions;
    }

    public Set<FunctionEntity> findAssignedFunctions() {
        Set<FunctionEntity> result = new HashSet<FunctionEntity>();

        for (RoleAssignedFunctionRelationship raf : this.getAssignedFunctions()) {
            result.add(raf.getFunction());
        }
        return result;
    }

    /**
     * This assigns function to the role. This returns true if it is not
     * present. false otherwise.
     *
     * @param function
     * @return status
     */
    public boolean assignFunction(FunctionEntity function) {

        boolean status;

        RoleAssignedFunctionRelationship functionAssigned = new RoleAssignedFunctionRelationship(this, function);

        if (!this.getAssignedFunctions().contains(functionAssigned)) {

            this.getAssignedFunctions().add(functionAssigned);

            status = true;
        } else {

            status = false;
        }

        return status;
    }
    
    /**
     * This removes function to the role. This returns true if it is not
     * present. false otherwise.
     *
     * @param function
     * @return status
     */
    public boolean removeFunction(FunctionEntity function) {

        boolean status;

        RoleAssignedFunctionRelationship functionAssigned = new RoleAssignedFunctionRelationship(this, function);

        if (this.getAssignedFunctions().contains(functionAssigned)) {

            this.getAssignedFunctions().remove(functionAssigned);

            status = true;
        } else {

            status = false;
        }

        return status;
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
        final RoleEntity other = (RoleEntity) obj;
        if ((this.roleId == null) ? (other.roleId != null) : !this.roleId.equals(other.roleId)) {
            return false;
        }
        return true;
    }

    public RoleEntity() {
    }

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
    public int compareTo(RoleEntity otherRole) {
        return this.getRoleId().compareTo(otherRole.getRoleId());
    }

}
