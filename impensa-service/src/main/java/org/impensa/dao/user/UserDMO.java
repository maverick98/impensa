/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.dao.user;

import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;
import org.common.bean.MappingBean;
import org.impensa.db.entity.UserEntity;


/**
 *
 * @author manosahu
 */
@XmlRootElement
@MappingBean(name = UserEntity.class)
public class UserDMO {

    private String userId;
    
    private String encryptedPassword;

    
    private String firstName;

    private String middleName;

    private String lastName;

    private Integer age;

    private String email;

    private String phone;

    private String address;
    
    private  Set<String> assignedOrgIds = new HashSet<String>();
    
    private  Set<String> assignedRoleIds = new HashSet<String>();
    
    private  Set<String> assignedFunctionNames = new HashSet<String>();

    public Set<String> getAssignedOrgIds() {
        return assignedOrgIds;
    }

    public void setAssignedOrgIds(Set<String> assignedOrgIds) {
        this.assignedOrgIds = assignedOrgIds;
    }

    public Set<String> getAssignedRoleIds() {
        return assignedRoleIds;
    }

    public void setAssignedRoleIds(Set<String> assignedRoleIds) {
        this.assignedRoleIds = assignedRoleIds;
    }

    public Set<String> getAssignedFunctionNames() {
        return assignedFunctionNames;
    }

    public void setAssignedFunctionNames(Set<String> assignedFunctionNames) {
        this.assignedFunctionNames = assignedFunctionNames;
    }
    
    

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
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
        hash = 79 * hash + (this.userId != null ? this.userId.hashCode() : 0);
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
        final UserDMO other = (UserDMO) obj;
        if ((this.userId == null) ? (other.userId != null) : !this.userId.equals(other.userId)) {
            return false;
        }
        return true;
    }
    
    
    
}
