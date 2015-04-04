/**
 * This file is part of impensa. CopyLeft (C) BigBang<->BigCrunch.All Rights are
 * left.
 *
 * 1) Modify it if you can understand. 2) If you distribute a modified version,
 * you must do it at your own risk.
 *
 */
package org.impensa.aspect.txn;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.commons.logger.ILogger;
import org.commons.logger.LoggerFactory;
import org.commons.string.StringUtil;
import org.impensa.AppContainer;
import org.impensa.dao.AbstractTxnExecutor;
import org.impensa.dao.session.SessionDMO;
import org.impensa.dao.tenant.ITenantDAO;
import org.impensa.db.TenantGraphDatabaseService;
import org.impensa.exception.ImpensaException;
import org.impensa.service.login.ILoginService;
import static org.impensa.service.login.LoginErrorCode.*;
import org.neo4j.graphdb.GraphDatabaseService;

/**
 *
 * @author msahu98
 */
@Aspect
public class TxnAspect {

    private static final ILogger logger = LoggerFactory.getLogger(TxnAspect.class.getName());

    public ITenantDAO getTenantDAO() {
        return AppContainer.getInstance().getBean("tenantDAOImpl", ITenantDAO.class);

    }

    public ILoginService getLoginService() {
        return AppContainer.getInstance().getBean("loginServiceImpl", ILoginService.class);
    }

    @Around("@annotation(org.impensa.txn.Txn)")
    public Object txnAdvice(final ProceedingJoinPoint proceedingJoinPoint) throws ImpensaException {
        final TxnRtnValueContainer rtnValueCotainer = new TxnRtnValueContainer();
        SessionDMO sessionDMO = this.getLoginService().getCurrentSession();
        if(sessionDMO == null){
             throw new ImpensaException(USER_NOT_LOGGED_IN);
        }
        String tenantId = sessionDMO.getTenantId();
        if(StringUtil.isNullOrEmpty(tenantId)){
            throw new ImpensaException(TENANT_NOT_PRESENT);
        }
        TenantGraphDatabaseService tenantGraphDatabseService = this.getTenantDAO().findTenantGraphDatabaseService(tenantId);
        if(tenantGraphDatabseService == null ){
            throw new ImpensaException("no tenantGraphdb found");
        }
        GraphDatabaseService graphDatabaseService = tenantGraphDatabseService.getGraphDatabaseService();
        if(graphDatabaseService == null ){
            throw new ImpensaException("no graphdbservce found");
        }
        System.out.println("creating txn before the method " + proceedingJoinPoint.getSignature().getName());
        
        new AbstractTxnExecutor() {

            @Override
            public void execute() throws ImpensaException {
                try {
                    Object rtnValue = proceedingJoinPoint.proceed();
                    rtnValueCotainer.setRtnValue(rtnValue);
                } catch (Throwable ex) {
                    throw ImpensaException.wrap(ex);
                }
            }
        }.createTxn(graphDatabaseService);

        return rtnValueCotainer.getRtnValue();

    }

    private static class TxnRtnValueContainer {

        private Object rtnValue;

        public Object getRtnValue() {
            return rtnValue;
        }

        public void setRtnValue(Object rtnValue) {
            this.rtnValue = rtnValue;
        }

    }
}
