/*
 *  Copyleft(BigBang<-->BigCrunch)  Manoranjan Sahu
 *  
 */
package org.impensa;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author manosahu
 */
public class AppContainer {

    private static final org.common.di.AppContainer  theInstance =  org.common.di.AppContainer.getInstance();
    private ApplicationContext appContext;

    private AppContainer() {
    }

    public ApplicationContext getAppContext() {
        return appContext;
    }

    
    public static org.common.di.AppContainer getInstance() {

        return theInstance;
    }

    public boolean configureSpringContext(String path) {

        appContext = new ClassPathXmlApplicationContext(path);
        return appContext != null;
    }

    public <T> T getBean(String clazzStr, Class<T> type) {
        /* T result = null;
        if (appContext != null) {
        result = appContext.getBean(clazzStr, type);
        }
        return result;*/
        return theInstance.getBean(clazzStr, type);
    }

    public Object getBean(String clazzStr) {

        /*  Object result = null;
        if (appContext != null) {
        result = appContext.getBean(clazzStr);
        }
        return result;*/
        return theInstance.getBean(clazzStr);
    }
}
