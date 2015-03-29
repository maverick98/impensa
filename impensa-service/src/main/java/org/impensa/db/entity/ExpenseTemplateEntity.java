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
public class ExpenseTemplateEntity extends IdentifiableEntity implements Comparable<ExpenseTemplateEntity> {

    private String name;
    private String description;
    
    @RelatedToVia(type = RelationshipTypes.EXPENSE_HAS_CATEGORIES)
    private Set<CategoryEntity> categories = new HashSet<CategoryEntity>();

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
