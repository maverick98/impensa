/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.db.entity;

import org.springframework.data.neo4j.annotation.*;

@RelationshipEntity(type = RelationshipTypes.USER_ORG_ASSIGNED)
public class UserAssignedOrgRelationship {

    @GraphId
    private Long graphId;

    @StartNode
    @Fetch
    private User user;

    @EndNode
    @Fetch
    private Org org;

    public UserAssignedOrgRelationship() {/* NOOP */

    }

    public UserAssignedOrgRelationship(User user, Org org) {
        this.user = user;
        this.org = org;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.graphId != null ? this.graphId.hashCode() : 0);
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
        if (this.graphId != other.graphId && (this.graphId == null || !this.graphId.equals(other.graphId))) {
            return false;
        }
        return true;
    }

}
