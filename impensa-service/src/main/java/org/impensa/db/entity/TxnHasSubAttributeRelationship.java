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
@RelationshipEntity(type = RelationshipTypes.TXNDATA_HAS_ATTRIBUTES)
public class TxnHasSubAttributeRelationship {

    @GraphId
    private Long graphId;

    @StartNode
    @Fetch
    private TxnDataEntity txnDataEntity;

    @EndNode
    @Fetch
    private AttributeEntity attributeEntity;

    public Long getGraphId() {
        return graphId;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + (this.txnDataEntity != null ? this.txnDataEntity.hashCode() : 0);
        hash = 17 * hash + (this.attributeEntity != null ? this.attributeEntity.hashCode() : 0);
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
        final TxnHasSubAttributeRelationship other = (TxnHasSubAttributeRelationship) obj;
        if (this.txnDataEntity != other.txnDataEntity && (this.txnDataEntity == null || !this.txnDataEntity.equals(other.txnDataEntity))) {
            return false;
        }
        if (this.attributeEntity != other.attributeEntity && (this.attributeEntity == null || !this.attributeEntity.equals(other.attributeEntity))) {
            return false;
        }
        return true;
    }

    public void setGraphId(Long graphId) {
        this.graphId = graphId;
    }

    public TxnDataEntity getTxnDataEntity() {
        return txnDataEntity;
    }

    public void setTxnDataEntity(TxnDataEntity txnDataEntity) {
        this.txnDataEntity = txnDataEntity;
    }

    public AttributeEntity getAttributeEntity() {
        return attributeEntity;
    }

    public void setAttributeEntity(AttributeEntity attributeEntity) {
        this.attributeEntity = attributeEntity;
    }

   

   
    
}
