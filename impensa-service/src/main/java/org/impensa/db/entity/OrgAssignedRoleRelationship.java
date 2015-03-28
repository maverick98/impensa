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
@RelationshipEntity(type = RelationshipTypes.ORG_ROLE_ASSIGNED)
public class OrgAssignedRoleRelationship {

    @GraphId
    private Long graphId;

    @StartNode
    @Fetch
    private OrgEntity org;

    public Long getGraphId() {
        return graphId;
    }

    public void setGraphId(Long graphId) {
        this.graphId = graphId;
    }

    public OrgEntity getOrg() {
        return org;
    }

    public void setOrg(OrgEntity org) {
        this.org = org;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    public OrgAssignedRoleRelationship() {
    }
    
    

    public OrgAssignedRoleRelationship(OrgEntity org, RoleEntity role) {
        this.org = org;
        this.role = role;
    }
    
    @EndNode
    @Fetch
    private RoleEntity role;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + (this.org != null ? this.org.hashCode() : 0);
        hash = 11 * hash + (this.role != null ? this.role.hashCode() : 0);
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
        if (this.org != other.org && (this.org == null || !this.org.equals(other.org))) {
            return false;
        }
        if (this.role != other.role && (this.role == null || !this.role.equals(other.role))) {
            return false;
        }
        return true;
    }

  
    
    
}
