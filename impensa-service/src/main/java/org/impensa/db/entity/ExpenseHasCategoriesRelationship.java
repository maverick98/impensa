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
@RelationshipEntity(type = RelationshipTypes.EXPENSE_HAS_CATEGORIES)
public class ExpenseHasCategoriesRelationship {

    @GraphId
    private Long graphId;

    @StartNode
    @Fetch
    private ExpenseTemplateEntity expenseTemplateEntity;

    @EndNode
    @Fetch
    private CategoryEntity categoryEntity;

    public Long getGraphId() {
        return graphId;
    }

    public void setGraphId(Long graphId) {
        this.graphId = graphId;
    }

    public ExpenseTemplateEntity getExpenseTemplateEntity() {
        return expenseTemplateEntity;
    }

    public void setExpenseTemplateEntity(ExpenseTemplateEntity expenseTemplateEntity) {
        this.expenseTemplateEntity = expenseTemplateEntity;
    }

    public CategoryEntity getCategoryEntity() {
        return categoryEntity;
    }

    public void setCategoryEntity(CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + (this.expenseTemplateEntity != null ? this.expenseTemplateEntity.hashCode() : 0);
        hash = 53 * hash + (this.categoryEntity != null ? this.categoryEntity.hashCode() : 0);
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
        final ExpenseHasCategoriesRelationship other = (ExpenseHasCategoriesRelationship) obj;
        if (this.expenseTemplateEntity != other.expenseTemplateEntity && (this.expenseTemplateEntity == null || !this.expenseTemplateEntity.equals(other.expenseTemplateEntity))) {
            return false;
        }
        if (this.categoryEntity != other.categoryEntity && (this.categoryEntity == null || !this.categoryEntity.equals(other.categoryEntity))) {
            return false;
        }
        return true;
    }
    
}
