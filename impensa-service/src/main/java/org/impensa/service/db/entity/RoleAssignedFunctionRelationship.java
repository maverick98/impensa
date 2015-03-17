/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.db.entity;

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
    private Role role;

    @EndNode
    @Fetch
    private Function function;

    public RoleAssignedFunctionRelationship(Role role, Function function) {
        this.role = role;
        this.function = function;
    }
    
    

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + (this.graphId != null ? this.graphId.hashCode() : 0);
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
        if (this.graphId != other.graphId && (this.graphId == null || !this.graphId.equals(other.graphId))) {
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