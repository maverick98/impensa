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
 * User Persitence Entity
 *
 * @author manosahu
 */
@NodeEntity
public class User extends IdentifiableEntity implements Comparable<User> {

    @Indexed(unique = true)
    private String userId;

    @Indexed(indexType = IndexType.FULLTEXT, indexName = "firstName")
    private String firstName;
    @Indexed(indexType = IndexType.FULLTEXT, indexName = "middleName")
    private String middleName;
    @Indexed(indexType = IndexType.FULLTEXT, indexName = "lastName")
    private String lastName;
    @Indexed(indexType = IndexType.FULLTEXT, indexName = "age")
    private Integer age;
    @Indexed(indexType = IndexType.FULLTEXT, indexName = "email")
    private String email;
    @Indexed(indexType = IndexType.FULLTEXT, indexName = "phone")
    private String phone;
    @Indexed(indexType = IndexType.FULLTEXT, indexName = "address")
    private String address;

    @RelatedToVia(type = RelationshipTypes.USER_ORG_ASSIGNED)
    private Set<UserAssignedOrgRelationship> assignedOrgs = new HashSet<UserAssignedOrgRelationship>();

    @RelatedToVia(type = RelationshipTypes.USER_ROLE_ASSIGNED)
    private Set<UserAssignedRoleRelationship> assignedRoles = new HashSet<UserAssignedRoleRelationship>();

    public User() {/* NOOP */

    }

    public boolean isFunctionAssigned(Function function) {
        boolean status;
        status = this.findAssignedFunctions().contains(function);
        return status;
    }

    public boolean isRoleAssigned(Role role) {
        boolean status;
        status = this.findAssignedRoles().contains(role);
        return status;
    }

    public Set<Function> findAssignedFunctions() {
        Set<Role> roles = this.findAssignedRoles();
        Set<Function> assignedFunctions = new HashSet<Function>();
        for (Role role : roles) {
            assignedFunctions.addAll(role.findAssignedFunctions());
        }
        return assignedFunctions;
    }

    public Set<Role> findAssignedRoles() {
        Set<Role> result = new HashSet<Role>();

        Set<UserAssignedOrgRelationship> orgs = this.getAssignedOrgs();
        for (UserAssignedOrgRelationship uar : orgs) {
            result.addAll(uar.getOrg().findAssignedRoles());
        }
        for (UserAssignedRoleRelationship uar : this.getAssignedRoles()) {
            result.add(uar.getRole());
        }
        return result;
    }

    public boolean isOrgAssigned(Org org) {
        boolean status;
        UserAssignedOrgRelationship orgAssigned = new UserAssignedOrgRelationship(this, org);
        status = this.getAssignedOrgs().contains(orgAssigned);

        return status;
    }

    public boolean assignOrg(Set<Org> orgs) {
        boolean status;
        if (orgs != null && !orgs.isEmpty()) {
            for (Org org : orgs) {
                this.assignOrg(org);
            }
            status = true;
        } else {
            status = false;
        }
        return status;
    }
   

    /**
     * This assigns org to the user. This returns true if it is not present.
     * false otherwise.
     *
     * @param org
     * @return status
     */
    //@Transactional
    public boolean assignOrg(Org org) {

        boolean status;
        
        UserAssignedOrgRelationship orgAssigned = new UserAssignedOrgRelationship(this, org);

        if (!this.getAssignedOrgs().contains(orgAssigned)) {

            this.getAssignedOrgs().add(orgAssigned);

            status = true;
        } else {

            status = false;
        }
       
        return status;
    }

    /**
     * This assigns org to the user. This returns true if it is not present.
     * false otherwise.
     *
     * @param org
     * @return status
     */
    public boolean removeOrg(Org org) {

        boolean status;

        UserAssignedOrgRelationship orgAssigned = new UserAssignedOrgRelationship(this, org);

        if (this.getAssignedOrgs().contains(orgAssigned)) {

            this.getAssignedOrgs().remove(orgAssigned);

            status = true;
        } else {

            status = false;
        }

        return status;
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

        UserAssignedRoleRelationship roleAssigned = new UserAssignedRoleRelationship(this, role);

        if (!this.getAssignedRoles().contains(roleAssigned)) {

            this.getAssignedRoles().add(roleAssigned);

            status = true;
        } else {

            status = false;
        }

        return status;
    }
    
    /**
     * This assigns org to the user. This returns true if it is not present.
     * false otherwise.
     *
     * @param role
     * @return status
     */
    public boolean removeRole(Role role) {

        boolean status;

        UserAssignedRoleRelationship roleAssigned = new UserAssignedRoleRelationship(this, role);

        if (this.getAssignedRoles().contains(roleAssigned)) {

            this.getAssignedRoles().remove(roleAssigned);

            status = true;
        } else {

            status = false;
        }

        return status;
    }

    public Set<UserAssignedOrgRelationship> getAssignedOrgs() {
        return assignedOrgs;
    }

    public void setAssignedOrgs(Set<UserAssignedOrgRelationship> assignedOrgs) {
        this.assignedOrgs = assignedOrgs;
    }

    public Set<UserAssignedRoleRelationship> getAssignedRoles() {
        return assignedRoles;
    }

    public void setAssignedRoles(Set<UserAssignedRoleRelationship> assignedRoles) {
        this.assignedRoles = assignedRoles;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + (this.userId != null ? this.userId.hashCode() : 0);
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
        final User other = (User) obj;
        if ((this.userId == null) ? (other.userId != null) : !this.userId.equals(other.userId)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(User otherUser) {
        return this.getUserId().compareTo(otherUser.getUserId());
    }

}
