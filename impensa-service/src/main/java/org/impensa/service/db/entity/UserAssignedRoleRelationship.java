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

@RelationshipEntity(type = RelationshipTypes.USER_ROLE_ASSIGNED)
public class UserAssignedRoleRelationship {

    @GraphId
    private Long graphId;

    @StartNode
    @Fetch
    private User user;

    @EndNode
    @Fetch
    private Role role;

    public UserAssignedRoleRelationship() {/* NOOP */

    }

    public UserAssignedRoleRelationship(User user, Role role) {
        this.user = user;
        this.role = role;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
        final UserAssignedRoleRelationship other = (UserAssignedRoleRelationship) obj;
        if (this.graphId != other.graphId && (this.graphId == null || !this.graphId.equals(other.graphId))) {
            return false;
        }
        return true;
    }

}
