/**
 * This file is part of impensa. CopyLeft (C) BigBang<->BigCrunch.All Rights are
 * left.
 *
 * 1) Modify it if you can understand. 2) If you distribute a modified version,
 * you must do it at your own risk.
 *
 */
package org.impensa.aspect.permission;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.common.di.AppContainer;
import org.commons.logger.ILogger;
import org.commons.logger.LoggerFactory;
import org.impensa.dao.session.SessionDMO;
import org.impensa.exception.ImpensaException;
import org.impensa.service.login.ILoginService;
import static org.impensa.exception.PrivilegeErrorCode.*;

/**
 * So this aspect is meant to handle service classes that implements biz
 * functionalities . This essentially checks if the loggedin user has permision
 * to execute the target method. It will throw Impensaexception with
 * permissionnotavailable exception.
 *
 * @author msahu98
 */
@Aspect
public class PermissionAspect {

    private static final ILogger logger = LoggerFactory.getLogger(PermissionAspect.class.getName());

    public ILoginService getLoginService() {
        return AppContainer.getInstance().getBean("loginServiceImpl", ILoginService.class);
    }

    /**
     * so all methods of service classes which has @Function annotation would be
     * candidate for permission check
     *
     * @TODO if not required , think of removing allServiceClasses() pointcut.
     * for now keep it.
     * @param proceedingJoinPoint
     * @return
     * @throws ImpensaException
     */
    @Around("allServiceClasses() && @annotation(org.impensa.function.Function)")
    public Object permitAdvice(ProceedingJoinPoint proceedingJoinPoint) throws ImpensaException {
        Object rtnValue = null;
        SessionDMO sessionDMO = this.getLoginService().getCurrentSession();
        System.out.println("session details are "+sessionDMO.getUserDMO().getAssignedFunctionNames());
        
        if(sessionDMO.getUserDMO().getAssignedFunctionNames().contains(proceedingJoinPoint.getSignature().getName())){
            System.out.println("GOOD you have permission.. go ahead");
        }else{
            System.out.println("BAD.. how dare you are here."+proceedingJoinPoint.getSignature().getName());
            throw new ImpensaException(INSUFFICIENT_PRIVILEGE);
        }
        try {
            System.out.println("Before calling method....");
            rtnValue = proceedingJoinPoint.proceed();
        } catch (Throwable ex) {
            throw ImpensaException.wrap(ex);
        }

        return rtnValue;

    }

    /**
     * Since we are putting all our service classes in org.impensa.service
     * package. so lets put this pointcut.
     */
    @Pointcut("within(org.impensa.service..*)")
    public void allServiceClasses() {
    }
}
