/*
 *  Copyleft(BigBang<-->BigCrunch)  Manoranjan Sahu
 *  
 */
package org.impensa.dao;

import org.impensa.db.GraphDatabaseUtil;
import org.impensa.exception.BeanConversionErrorCode;
import org.impensa.exception.ImpensaException;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;

/**
 *
 * @author manosahu
 */
public abstract class AbstractTxnExecutor implements ITxnExecutor {

    @Override
    public abstract void execute() throws ImpensaException;

    @Override
    public void createTxn(GraphDatabaseService databaseService) throws ImpensaException {

        Transaction txn = null;
        try {
            txn = TxnUtil.createTxn(databaseService);
            this.execute();
            TxnUtil.endTxn(txn);

        } catch (Exception ex) {
            TxnUtil.endTxnWithFailure(txn);
            throw ImpensaException.wrap(ex)
                    .setErrorCode(BeanConversionErrorCode.FROM_MAPPING_BEAN);
        }
    }
;
}
