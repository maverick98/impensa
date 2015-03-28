/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.db.entity;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

/**
 *
 * @author manosahu
 */
@RelationshipEntity(type = RelationshipTypes.ROLE_FUNCTION_ASSIGNED)
public class RoleAssignedFunctionRelationship {

    @GraphId
    private Long graphId;

    @StartNode
    @Fetch
    private RoleEntity role;

    @EndNode
    @Fetch
    private FunctionEntity function;

    public RoleAssignedFunctionRelationship(RoleEntity role, FunctionEntity function) {
        this.role = role;
        this.function = function;
    }
    
    

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    public FunctionEntity getFunction() {
        return function;
    }

    public void setFunction(FunctionEntity function) {
        this.function = function;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.role != null ? this.role.hashCode() : 0);
        hash = 83 * hash + (this.function != null ? this.function.hashCode() : 0);
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
        final RoleAssignedFunctionRelationship other = (RoleAssignedFunctionRelationship) obj;
        if (this.role != other.role && (this.role == null || !this.role.equals(other.role))) {
            return false;
        }
        if (this.function != other.function && (this.function == null || !this.function.equals(other.function))) {
            return false;
        }
        return true;
    }

    

    public Long getGraphId() {
        return graphId;
    }

    public void setGraphId(Long graphId) {
        this.graphId = graphId;
    }

    public RoleAssignedFunctionRelationship() {
    }

    @Override
    public String toString() {
        return "RoleAssignedFunctionRelationship{" + "graphId=" + graphId + ", role=" + role + ", function=" + function + '}';
    }

}
