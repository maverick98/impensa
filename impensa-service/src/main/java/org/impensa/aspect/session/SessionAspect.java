/**
 * This file is part of impensa. CopyLeft (C) BigBang<->BigCrunch.All Rights are
 * left.
 *
 * 1) Modify it if you can understand. 2) If you distribute a modified version,
 * you must do it at your own risk.
 *
 */
package org.impensa.aspect.session;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.commons.logger.ILogger;
import org.commons.logger.LoggerFactory;
import org.impensa.AppContainer;
import org.impensa.exception.ImpensaException;
import org.impensa.service.login.ILoginService;
import org.impensa.service.login.LoginServiceImpl;
import static org.impensa.service.login.LoginErrorCode.*;

/**
 * So this aspect checks unauthorized access to impensa service.
 *
 * @author msahu98
 */
@Aspect
public class SessionAspect {

    private static final ILogger logger = LoggerFactory.getLogger(SessionAspect.class.getName());

    public ILoginService getLoginService() {
        return AppContainer.getInstance().getBean("loginServiceImpl", ILoginService.class);
    }

    /**
     * spare the 3 methods login , isLoggedIn, getCurrentSession for obvious
     * reason.
     *
     * @param joinPoint
     * @throws ImpensaException
     */
    @Before("allServiceClasses()")
    public void sessionAdvice(JoinPoint joinPoint) throws ImpensaException {
        System.out.println("inside session advice ");
       // if (joinPoint.getTarget().getClass().equals(LoginServiceImpl.class)) {

            if (joinPoint.getSignature().getName().equals("login") || joinPoint.getSignature().getName().equals("isLoggedIn")
                    || joinPoint.getSignature().getName().equals("getCurrentSession")
                    || joinPoint.getSignature().getName().equals("createTenantRole")) {
                return;
            }

       // }
        if (this.getLoginService().getCurrentSession() == null) {
            logger.error("Unauthorized method invocation "+joinPoint.getSignature().getName());
            throw new ImpensaException(USER_NOT_LOGGED_IN);
        }
    }

    /**
     * We are intersted in service layer as this is the entry point for clients
     * to interact with impensa Hence check for logged in user for this layer
     * only.
     */
    @Pointcut("within(org.impensa.service..*)")
    public void allServiceClasses() {
    }
}
