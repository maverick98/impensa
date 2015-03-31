/**
  * This file is part of impensa.
  * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
  *
  * 1) Modify it if you can understand.
  * 2) If you distribute a modified version, you must do it at your own risk.
  *
  */
package org.impensa.dao.expensetemplate;

import org.impensa.service.txn.*;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author msahu98
 */
public class TxnData {

    private String name;
    private List<Attribute> attributes = new ArrayList<Attribute>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }
    
}
