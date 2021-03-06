/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.dao;

import org.impensa.exception.ImpensaException;
import org.impensa.exception.ValidationErrorCode;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;

/**
 *
 * @author manosahu
 */
public class TxnUtil {

    public static Transaction createTxn(GraphDatabaseService graphDatabaseService) throws ImpensaException {
        if (graphDatabaseService == null) {
            throw new ImpensaException(Neo4JErrorCode.GRAHDB_SERVICE_NOT_FOUND);
        }
        Transaction txn = graphDatabaseService.beginTx();
        if (txn == null) {
            throw new ImpensaException(Neo4JErrorCode.TXN_NOT_CREATED);
        }

        return txn;
    }

    public static boolean closeTxn(Transaction txn) throws ImpensaException {
        if (txn == null) {
            throw new ImpensaException(ValidationErrorCode.VALUE_NULL).set("txn", "null");
        }
        boolean result;

        txn.finish();
        result = true;
        return result;
    }

    public static boolean endTxn(Transaction txn) throws ImpensaException {
        if (txn == null) {
            throw new ImpensaException(ValidationErrorCode.VALUE_NULL).set("txn", "null");
        }
        txn.success();
        return closeTxn(txn);

    }

    public static boolean endTxnWithFailure(Transaction txn) throws ImpensaException {
        if (txn == null) {
            throw new ImpensaException(ValidationErrorCode.VALUE_NULL).set("txn", "null");
        }
        txn.failure();
        return closeTxn(txn);

    }
}
