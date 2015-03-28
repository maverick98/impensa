/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.txn;

import org.common.di.ScanMe;
import org.impensa.function.Function;
import org.impensa.function.IFunctionProvider;
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
        System.out.println("I am creating txn");
    }

    @Override
    @Function(name="approveTransaction" , description = "This approves a transaction")
    public void approveTxn() {
        System.out.println("I am approving txn");
    }

    @Override
    @Function(name="rejectTransaction" , description = "This rejects a transaction")
    public void rejectTxn() {
        System.out.println("I am rejcting txn");
    }

}
