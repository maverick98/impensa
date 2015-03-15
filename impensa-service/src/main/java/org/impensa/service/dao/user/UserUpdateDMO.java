/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.dao.user;

import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author manosahu
 */
@XmlRootElement
public class UserUpdateDMO {

    private UserDMO userUpdate;
    
    private Set<String> insertOrgIdSet = new HashSet<String>();
    
    private Set<String> deleteOrgIdSet = new HashSet<String>();
    
    private Set<String> insertRoleIdSet = new HashSet<String>();
    
    private Set<String> deleteRoleIdSet = new HashSet<String>();

    public UserDMO getUserUpdate() {
        return userUpdate;
    }

    public void setUserUpdate(UserDMO userUpdate) {
        this.userUpdate = userUpdate;
    }

    
    public Set<String> getInsertOrgIdSet() {
        return insertOrgIdSet;
    }

    public void setInsertOrgIdSet(Set<String> insertOrgIdSet) {
        this.insertOrgIdSet = insertOrgIdSet;
    }

    public Set<String> getDeleteOrgIdSet() {
        return deleteOrgIdSet;
    }

    public void setDeleteOrgIdSet(Set<String> deleteOrgIdSet) {
        this.deleteOrgIdSet = deleteOrgIdSet;
    }

    public Set<String> getInsertRoleIdSet() {
        return insertRoleIdSet;
    }

    public void setInsertRoleIdSet(Set<String> insertRoleIdSet) {
        this.insertRoleIdSet = insertRoleIdSet;
    }

    public Set<String> getDeleteRoleIdSet() {
        return deleteRoleIdSet;
    }

    public void setDeleteRoleIdSet(Set<String> deleteRoleIdSet) {
        this.deleteRoleIdSet = deleteRoleIdSet;
    }

    @Override
    public String toString() {
        return "UserUpdateDMO{" + "insertOrgIdSet=" + insertOrgIdSet + ", deleteOrgIdSet=" + deleteOrgIdSet + ", insertRoleIdSet=" + insertRoleIdSet + ", deleteRoleIdSet=" + deleteRoleIdSet + '}';
    }
    
    
}
