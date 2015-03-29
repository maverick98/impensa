/**
 * This file is part of impensa. CopyLeft (C) BigBang<->BigCrunch.All Rights are
 * left.
 *
 * 1) Modify it if you can understand. 2) If you distribute a modified version,
 * you must do it at your own risk.
 *
 */
package org.impensa.db.entity;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

/**
 *
 * @author msahu98
 */
@RelationshipEntity(type = RelationshipTypes.CATEGORY_HAS_TXNDATA)
public class CategoryHasTxnDataRelationship {

    @GraphId
    private Long graphId;

    @StartNode
    @Fetch
    private CategoryEntity categoryEntity;

    @EndNode
    @Fetch
    private TxnDataEntity txnDataEntity;

    public Long getGraphId() {
        return graphId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.categoryEntity != null ? this.categoryEntity.hashCode() : 0);
        hash = 97 * hash + (this.txnDataEntity != null ? this.txnDataEntity.hashCode() : 0);
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
        final CategoryHasTxnDataRelationship other = (CategoryHasTxnDataRelationship) obj;
        if (this.categoryEntity != other.categoryEntity && (this.categoryEntity == null || !this.categoryEntity.equals(other.categoryEntity))) {
            return false;
        }
        return true;
    }

    public void setGraphId(Long graphId) {
        this.graphId = graphId;
    }

    public CategoryEntity getCategoryEntity() {
        return categoryEntity;
    }

    public void setCategoryEntity(CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
    }

    public TxnDataEntity getTxnDataEntity() {
        return txnDataEntity;
    }

    public void setTxnDataEntity(TxnDataEntity txnDataEntity) {
        this.txnDataEntity = txnDataEntity;
    }

   
   
    
}
