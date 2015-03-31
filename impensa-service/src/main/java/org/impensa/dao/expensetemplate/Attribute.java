/**
  * This file is part of impensa.
  * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
  *
  * 1) Modify it if you can understand.
  * 2) If you distribute a modified version, you must do it at your own risk.
  *
  */
package org.impensa.dao.expensetemplate;

import org.common.bean.MappingBean;
import org.impensa.db.entity.AttributeEntity;

/**
 *
 * @author msahu98
 */
@MappingBean(name=AttributeEntity.class)
public class Attribute {

    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Attribute{" + "key=" + key + ", value=" + value + '}';
    }
    
    
}
