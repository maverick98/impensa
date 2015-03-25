/*
 *  Copyleft(BigBang<-->BigCrunch)  Manoranjan Sahu
 *  
 */
package org.impensa.service.dao;

import org.impensa.service.dao.exception.DAOException;
import org.neo4j.graphdb.Transaction;

/**
 *
 * @author manosahu
 */
public abstract class AbstractTxnExecutor implements ITxnExecutor {

    @Override
    public abstract void execute() throws DAOException;

    @Override
    public void createTxn() throws DAOException {

        Transaction txn = null;
        try {
            txn = TxnUtil.createTxn();
            this.execute();
            TxnUtil.endTxn(txn);

        } catch (Exception ex) {
            TxnUtil.endTxnWithFailure(txn);
            throw new DAOException(ex);
        }
    }
;
}
