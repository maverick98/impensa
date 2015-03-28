/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.db.entity;

import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.support.index.IndexType;

/**
 *
 * @author manosahu
 */
@NodeEntity
public class FunctionEntity extends IdentifiableEntity implements Comparable<FunctionEntity> {

    @Indexed(unique = true, indexType = IndexType.FULLTEXT, indexName = "functionName")
    private String functionName;

    @Indexed(indexType = IndexType.FULLTEXT, indexName = "functionDescription")
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
        hash = 53 * hash + (this.functionName != null ? this.functionName.hashCode() : 0);
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
        final FunctionEntity other = (FunctionEntity) obj;
        if ((this.functionName == null) ? (other.functionName != null) : !this.functionName.equals(other.functionName)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(FunctionEntity otherRole) {
        return this.getFunctionName().compareTo(otherRole.getFunctionName());
    }

}
