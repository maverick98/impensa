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

    /**
     * For performance reason , keep name of @Function same as function name.
     * The only justification I can think of now is that there is no harm
     * in using names which are more readable. :)
     * 
     */
    @Override
    @Function(name="createTransaction" , description = "This creates a transaction")
    public void createTransaction() {
        System.out.println("I am creating txn");
    }

    @Override
    @Function(name="approveTransaction" , description = "This approves a transaction")
    public void approveTransaction() {
        System.out.println("I am approving txn");
    }

    @Override
    @Function(name="rejectTransaction" , description = "This rejects a transaction")
    public void rejectTransaction() {
        System.out.println("I am rejcting txn");
    }

}
