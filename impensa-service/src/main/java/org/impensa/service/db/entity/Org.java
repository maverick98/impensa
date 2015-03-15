/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.db.entity;

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
public class Org extends IdentifiableEntity implements Comparable<Org>{

    @Indexed(unique = true)
    private String orgId;

    public Org getParentOrg() {
        return parentOrg;
    }

    public void setParentOrg(Org parentOrg) {
        this.parentOrg = parentOrg;
    }

    @Indexed(indexType = IndexType.FULLTEXT, indexName = "orgName")
    private String orgName;

    @Indexed(indexType = IndexType.FULLTEXT, indexName = "orgDescription")
    private String orgDescription;

    @Indexed(indexType = IndexType.FULLTEXT, indexName = "parentOrg")
    private Org parentOrg;
    
   

    @RelatedToVia(type = RelationshipTypes.ORG_ROLE_ASSIGNED)
    private Set<OrgAssignedRoleRelationship> assignedRoles = new HashSet<OrgAssignedRoleRelationship>();

    
    public Set<Role> findAssignedRoles(){
        Set<Role> result = new HashSet<Role>();
        
        for(OrgAssignedRoleRelationship oar : this.getAssignedRoles()){
            result.add(oar.getRole());
        }
        return result;
    }
    /**
     * This assigns org to the user. This returns true if it is not present.
     * false otherwise.
     *
     * @param role
     * @return status
     */
    public boolean assignRole(Role role) {

        boolean status;

        OrgAssignedRoleRelationship roleAssigned = new OrgAssignedRoleRelationship(this, role);

        if (!this.getAssignedRoles().contains(roleAssigned)) {

            this.getAssignedRoles().add(roleAssigned);

            status = true;
        } else {

            status = false;
        }

        return status;
    }

    public Set<OrgAssignedRoleRelationship> getAssignedRoles() {
        return assignedRoles;
    }

    public void setAssignedRoles(Set<OrgAssignedRoleRelationship> assignedRoles) {
        this.assignedRoles = assignedRoles;
    }

    public Org() {
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgDescription() {
        return orgDescription;
    }

    public void setOrgDescription(String orgDescription) {
        this.orgDescription = orgDescription;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + (this.orgId != null ? this.orgId.hashCode() : 0);
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
        final Org other = (Org) obj;
        if ((this.orgId == null) ? (other.orgId != null) : !this.orgId.equals(other.orgId)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Org otherOrg) {
        return this.getOrgId().compareTo(otherOrg.getOrgId());
    }

    
}
