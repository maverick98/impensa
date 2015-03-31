/**
  * This file is part of impensa.
  * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
  *
  * 1) Modify it if you can understand.
  * 2) If you distribute a modified version, you must do it at your own risk.
  *
  */
package org.impensa.dao.expensetemplate;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import org.common.bean.MappingBean;
import org.common.bean.Property;
import org.impensa.db.entity.ExpenseTemplateEntity;

/**
 *
 * @author msahu98
 */
@XmlRootElement
@MappingBean(name=ExpenseTemplateEntity.class)
public class ExpenseTemplate {

    private String tenantId;
    private String name;
    private String description;
    @Property(name="dummy",ignore = true)
    private List<Category> category = new ArrayList<Category>();

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
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

    
    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    

   
    
}
