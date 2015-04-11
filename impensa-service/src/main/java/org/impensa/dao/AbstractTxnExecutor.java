/*
 *  Copyleft(BigBang<-->BigCrunch)  Manoranjan Sahu
 *  
 */
package org.impensa.dao;


import org.common.di.AppContainer;
import org.impensa.dao.session.SessionDMO;
import org.impensa.exception.BeanConversionErrorCode;
import org.impensa.exception.ImpensaException;
import org.impensa.service.login.ILoginService;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;

/**
 *
 * @author manosahu
 */
public abstract class AbstractTxnExecutor implements ITxnExecutor {

    @Override
    public abstract void execute() throws ImpensaException;

    public ILoginService getLoginService() {
        return AppContainer.getInstance().getBean("loginServiceImpl", ILoginService.class);
    }

    @Override
    public void createTxn(GraphDatabaseService databaseService) throws ImpensaException {

        SessionDMO sessionDMO = this.getLoginService().getCurrentSession();

        Transaction txn = sessionDMO.getTxn();
        boolean amIResponsibleForTxn = (txn == null);
        try {
            if (amIResponsibleForTxn) {
                txn = TxnUtil.createTxn(databaseService);
                sessionDMO.setTxn(txn);
            }
            this.execute();
            if (amIResponsibleForTxn) {
                TxnUtil.endTxn(txn);
                sessionDMO.setTxn(null);
            }

        } catch (Throwable ex) {
            if (amIResponsibleForTxn) {
                TxnUtil.endTxnWithFailure(txn);
                sessionDMO.setTxn(null);
            }

            throw ImpensaException.wrap(ex)
                    .setErrorCode(BeanConversionErrorCode.FROM_MAPPING_BEAN);
        }
    }
;
}
