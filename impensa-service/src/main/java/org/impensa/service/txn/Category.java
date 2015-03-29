/**
 * This file is part of impensa. CopyLeft (C) BigBang<->BigCrunch.All Rights are
 * left.
 *
 * 1) Modify it if you can understand. 2) If you distribute a modified version,
 * you must do it at your own risk.
 *
 */
package org.impensa.service.txn;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author msahu98
 */
public class Category {

    private String name;
    private String description;
    private List<Category> category = new ArrayList<Category>();
    private TxnData txnData = new TxnData();

    public TxnData getTxnData() {
        return txnData;
    }

    public void setTxnData(TxnData txnData) {
        this.txnData = txnData;
    }

   

    
    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
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
    

}
