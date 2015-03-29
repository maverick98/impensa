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
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedToVia;

/**
 *
 * @author msahu98
 */
@NodeEntity
public class CategoryEntity extends IdentifiableEntity implements Comparable<CategoryEntity> {

    private String name;
    private String description;

    @RelatedToVia(type = RelationshipTypes.CATEGORY_HAS_SUBCATEGORIES)
    private Set<CategoryEntity> categories = new HashSet<CategoryEntity>();
    
    @RelatedToVia(type = RelationshipTypes.CATEGORY_HAS_TXNDATA)
    private TxnDataEntity txnData = new TxnDataEntity();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<CategoryEntity> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryEntity> categories) {
        this.categories = categories;
    }

    public TxnDataEntity getTxnData() {
        return txnData;
    }

    public void setTxnData(TxnDataEntity txnData) {
        this.txnData = txnData;
    }

    @Override
    public int compareTo(CategoryEntity o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
