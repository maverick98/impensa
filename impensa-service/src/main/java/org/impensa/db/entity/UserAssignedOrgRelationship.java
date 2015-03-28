/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.db.entity;

import org.springframework.data.neo4j.annotation.*;

@RelationshipEntity(type = RelationshipTypes.USER_ORG_ASSIGNED)
public class UserAssignedOrgRelationship {

    @GraphId
    private Long graphId;

    @StartNode
    @Fetch
    private UserEntity user;

    @EndNode
    @Fetch
    private OrgEntity org;

    public UserAssignedOrgRelationship() {/* NOOP */

    }

    public UserAssignedOrgRelationship(UserEntity user, OrgEntity org) {
        this.user = user;
        this.org = org;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.user != null ? this.user.hashCode() : 0);
        hash = 97 * hash + (this.org != null ? this.org.hashCode() : 0);
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
        final UserAssignedOrgRelationship other = (UserAssignedOrgRelationship) obj;
        if (this.user != other.user && (this.user == null || !this.user.equals(other.user))) {
            return false;
        }
        if (this.org != other.org && (this.org == null || !this.org.equals(other.org))) {
            return false;
        }
        return true;
    }

   

}
