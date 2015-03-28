/*
 *  Copyleft(BigBang<-->BigCrunch)  Manoranjan Sahu
 *  
 */
package org.impensa.dao.org;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author manosahu
 */
public class OrgUpdateDMO {

    private OrgDMO orgUpdate;
    
    private Set<String> insertRoleIdSet = new HashSet<String>();

    private Set<String> deleteRoleIdSet = new HashSet<String>();

    public OrgDMO getOrgUpdate() {
        return orgUpdate;
    }

    public void setOrgUpdate(OrgDMO orgUpdate) {
        this.orgUpdate = orgUpdate;
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
        return "OrgUpdateDMO{" + "orgUpdate=" + orgUpdate + ", insertRoleIdSet=" + insertRoleIdSet + ", deleteRoleIdSet=" + deleteRoleIdSet + '}';
    }
    
    
}
