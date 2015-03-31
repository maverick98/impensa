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
import org.springframework.data.neo4j.support.index.IndexType;

/**
 *
 * @author msahu98
 */
@NodeEntity
public class CategoryEntity extends IdentifiableEntity implements Comparable<CategoryEntity> {

    @Indexed(unique = true)
    private String name;
    @Indexed(indexType = IndexType.FULLTEXT, indexName = "description")
    private String description;

    @RelatedToVia(type = RelationshipTypes.CATEGORY_HAS_SUBCATEGORIES)
    private Set<CategoryHasSubCategoriesRelationship> categories = new HashSet<CategoryHasSubCategoriesRelationship>();

    @RelatedToVia(type = RelationshipTypes.CATEGORY_HAS_TXNDATA)
    private CategoryHasTxnDataRelationship txnData = new CategoryHasTxnDataRelationship();

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

    public Set<CategoryHasSubCategoriesRelationship> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryHasSubCategoriesRelationship> categories) {
        this.categories = categories;
    }

    public CategoryHasTxnDataRelationship getTxnData() {
        return txnData;
    }

    public void setTxnData(CategoryHasTxnDataRelationship txnData) {
        this.txnData = txnData;
    }

    @Override
    public int compareTo(CategoryEntity o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
