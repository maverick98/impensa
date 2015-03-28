/*
 *  Copyleft(BigBang<-->BigCrunch)  Manoranjan Sahu
 *  
 */
package org.impensa.dao.role;

import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author manosahu
 */
@XmlRootElement
public class RoleUpdateDMO {

    private RoleDMO roleDMO;
    
    private Set<String> insertFunctionNameSet = new HashSet<String>();
    
    private Set<String> deleteFunctionNameSet = new HashSet<String>();

    @Override
    public String toString() {
        return "RoleUpdateDMO{" + "roleDMO=" + roleDMO + ", insertFunctionNameSet=" + insertFunctionNameSet + ", deleteFunctionNameSet=" + deleteFunctionNameSet + '}';
    }

    public RoleDMO getRoleDMO() {
        return roleDMO;
    }

    public void setRoleDMO(RoleDMO roleDMO) {
        this.roleDMO = roleDMO;
    }

    public Set<String> getInsertFunctionNameSet() {
        return insertFunctionNameSet;
    }

    public void setInsertFunctionNameSet(Set<String> insertFunctionNameSet) {
        this.insertFunctionNameSet = insertFunctionNameSet;
    }

    public Set<String> getDeleteFunctionNameSet() {
        return deleteFunctionNameSet;
    }

    public void setDeleteFunctionNameSet(Set<String> deleteFunctionNameSet) {
        this.deleteFunctionNameSet = deleteFunctionNameSet;
    }
}
