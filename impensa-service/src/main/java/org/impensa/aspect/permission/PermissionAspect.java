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
import org.commons.logger.ILogger;
import org.commons.logger.LoggerFactory;
import org.impensa.exception.ImpensaException;

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

    /**
     * so all methods of service classes 
     * which has @Function annotation would be candidate 
     * for permission check
     * @TODO if not required , think of removing allServiceClasses() pointcut.
     * for now keep it.
     * @param proceedingJoinPoint
     * @return
     * @throws ImpensaException 
     */
    @Around("allServiceClasses() && @annotation(org.impensa.function.Function)")
    public Object permitAdvice(ProceedingJoinPoint proceedingJoinPoint) throws ImpensaException {
        Object rtnValue = null;
        try {
            System.out.println("Before calling method....");
            rtnValue = proceedingJoinPoint.proceed();
        } catch (Throwable ex) {
            throw ImpensaException.wrap(ex);
        }

        return rtnValue;

    }
    /**
     * Since we are putting all our service classes in org.impensa.service package.
     * so lets put this pointcut.
     */
    @Pointcut("within(org.impensa.service..*)")
    public void allServiceClasses() {
    }
}
