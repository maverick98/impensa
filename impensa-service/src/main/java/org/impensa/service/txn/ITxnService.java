/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.txn;

/**
 *
 * @author manosahu
 */
public interface ITxnService {
    
    public void createTransaction();
    
    public void approveTransaction();
    
    public void rejectTransaction();
    

}
