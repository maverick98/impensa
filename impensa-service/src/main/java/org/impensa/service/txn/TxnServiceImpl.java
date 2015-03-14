/*
 *  Copyleft(BigBang<-->BigCrunch)  Manoranjan Sahu
 *  
 */
package org.impensa.service.txn;

import org.common.di.ScanMe;
import org.impensa.service.dao.function.Function;
import org.impensa.service.dao.function.IFunctionProvider;
import org.springframework.stereotype.Service;

/**
 *
 * @author manosahu
 */
@Service
@ScanMe(true)
public class TxnServiceImpl implements ITxnService , IFunctionProvider{

    @Override
    @Function(name="createTransaction" , description = "This creates a transaction")
    public void createTxn() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Function(name="approveTransaction" , description = "This approves a transaction")
    public void approveTxn() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Function(name="rejectTransaction" , description = "This rejects a transaction")
    public void rejectTxn() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
