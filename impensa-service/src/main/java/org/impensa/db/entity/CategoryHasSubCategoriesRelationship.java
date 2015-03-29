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
@RelationshipEntity(type = RelationshipTypes.CATEGORY_HAS_SUBCATEGORIES)
public class CategoryHasSubCategoriesRelationship {

    @GraphId
    private Long graphId;

    @StartNode
    @Fetch
    private CategoryEntity categoryEntity;

    @EndNode
    @Fetch
    private CategoryEntity childCategoryEntity;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.categoryEntity != null ? this.categoryEntity.hashCode() : 0);
        hash = 67 * hash + (this.childCategoryEntity != null ? this.childCategoryEntity.hashCode() : 0);
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
        final CategoryHasSubCategoriesRelationship other = (CategoryHasSubCategoriesRelationship) obj;
        if (this.categoryEntity != other.categoryEntity && (this.categoryEntity == null || !this.categoryEntity.equals(other.categoryEntity))) {
            return false;
        }
        if (this.childCategoryEntity != other.childCategoryEntity && (this.childCategoryEntity == null || !this.childCategoryEntity.equals(other.childCategoryEntity))) {
            return false;
        }
        return true;
    }

    
    public Long getGraphId() {
        return graphId;
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

    public CategoryEntity getChildCategoryEntity() {
        return childCategoryEntity;
    }

    public void setChildCategoryEntity(CategoryEntity childCategoryEntity) {
        this.childCategoryEntity = childCategoryEntity;
    }

   
    
}
