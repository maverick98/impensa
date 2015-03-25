/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.dao;

import org.impensa.service.AppContainer;
import org.impensa.service.dao.exception.DAOException;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;

/**
 *
 * @author manosahu
 */
public class TxnUtil {

    public static  GraphDatabaseService getGraphDb(){
        return AppContainer.getInstance().getBean("graphDatabaseService", GraphDatabaseService.class);
    }
    public static Transaction createTxn() throws Exception{
        Transaction txn = getGraphDb().beginTx();
        if(txn == null){
            throw new DAOException("Unable to create txn");
        }
        
        return txn;
    }
     public static boolean closeTxn(Transaction txn) {
        boolean result;
        if(txn == null){
            result = false;
        }
        txn.finish();
        result = true;
        return result;
    }
     public static boolean endTxn(Transaction txn) {
        if(txn == null){
            return false;
        }
        txn.success();
        return closeTxn(txn);
        
    }
     public static boolean endTxnWithFailure(Transaction txn) {
        if(txn == null){
            return false;
        }
        txn.failure();
        return closeTxn(txn);
        
    }
}
