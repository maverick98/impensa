/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.dao.function;

import javax.xml.bind.annotation.XmlRootElement;
import org.common.bean.MappingBean;


/**
 *
 * @author manosahu
 */
@XmlRootElement
@MappingBean(name=org.impensa.db.entity.FunctionEntity.class)
public class FunctionDMO {

    private String functionName;
    
    private String functionDescription;

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionDescription() {
        return functionDescription;
    }

    public void setFunctionDescription(String functionDescription) {
        this.functionDescription = functionDescription;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.functionName != null ? this.functionName.hashCode() : 0);
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
        final FunctionDMO other = (FunctionDMO) obj;
        if ((this.functionName == null) ? (other.functionName != null) : !this.functionName.equals(other.functionName)) {
            return false;
        }
        return true;
    }

   

   
    
    
}
