/**
 * This file is part of impensa. CopyLeft (C) BigBang<->BigCrunch.All Rights are
 * left.
 *
 * 1) Modify it if you can understand. 2) If you distribute a modified version,
 * you must do it at your own risk.
 *
 */
package org.impensa.db.entity;

import java.util.HashSet;
import java.util.Set;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedToVia;

/**
 *
 * @author msahu98
 */
@NodeEntity
public class TxnDataEntity extends IdentifiableEntity implements Comparable<TxnDataEntity> {

    @Indexed(unique=true)
    private String name;

    @RelatedToVia(type = RelationshipTypes.TXNDATA_HAS_ATTRIBUTES)
    private Set<TxnHasSubAttributeRelationship> attributes = new HashSet<TxnHasSubAttributeRelationship>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<TxnHasSubAttributeRelationship> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<TxnHasSubAttributeRelationship> attributes) {
        this.attributes = attributes;
    }

   

    @Override
    public int compareTo(TxnDataEntity o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
