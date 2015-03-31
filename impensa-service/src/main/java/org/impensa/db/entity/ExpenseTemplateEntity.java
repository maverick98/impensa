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
public class ExpenseTemplateEntity extends IdentifiableEntity implements Comparable<ExpenseTemplateEntity> {

    @Indexed(unique = true)
    private String tenantId;
    @Indexed(indexType = IndexType.FULLTEXT, indexName = "name")
    private String name;
    @Indexed(indexType = IndexType.FULLTEXT, indexName = "description")
    private String description;

    @RelatedToVia(type = RelationshipTypes.EXPENSE_HAS_CATEGORIES)
    private Set<ExpenseHasCategoriesRelationship> categories = new HashSet<ExpenseHasCategoriesRelationship>();

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Set<ExpenseHasCategoriesRelationship> getCategories() {
        return categories;
    }

    public void setCategories(Set<ExpenseHasCategoriesRelationship> categories) {
        this.categories = categories;
    }

    
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

    @Override
    public int compareTo(ExpenseTemplateEntity o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
