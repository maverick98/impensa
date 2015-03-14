/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch . All Rights are left.
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
@RelationshipEntity(type = RelationshipTypes.ORG_ROLE_ASSIGNED)
public class OrgAssignedRoleRelationship {

    @GraphId
    private Long graphId;

    @StartNode
    @Fetch
    private Org org;

    public Long getGraphId() {
        return graphId;
    }

    public void setGraphId(Long graphId) {
        this.graphId = graphId;
    }

    public Org getOrg() {
        return org;
    }

    public void setOrg(Org org) {
        this.org = org;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public OrgAssignedRoleRelationship() {
    }
    
    

    public OrgAssignedRoleRelationship(Org org, Role role) {
        this.org = org;
        this.role = role;
    }
    
    @EndNode
    @Fetch
    private Role role;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + (this.graphId != null ? this.graphId.hashCode() : 0);
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
        final OrgAssignedRoleRelationship other = (OrgAssignedRoleRelationship) obj;
        if (this.graphId != other.graphId && (this.graphId == null || !this.graphId.equals(other.graphId))) {
            return false;
        }
        return true;
    }
    
    
}
