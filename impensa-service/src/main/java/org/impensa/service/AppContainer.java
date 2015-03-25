/*
 *  Copyleft(BigBang<-->BigCrunch)  Manoranjan Sahu
 *  
 */
package org.impensa.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author manosahu
 */
public class AppContainer {

    private static final AppContainer theInstance = new AppContainer();
    private ApplicationContext appContext;

    private AppContainer() {
    }

    public ApplicationContext getAppContext() {
        return appContext;
    }

    
    public static AppContainer getInstance() {

        return theInstance;
    }

    public boolean configureSpringContext(String path) {

        appContext = new ClassPathXmlApplicationContext(path);
        return appContext != null;
    }

    public <T extends Object> T getBean(String clazzStr, Class<T> type) {
        T result = null;
        if (appContext != null) {
            result = appContext.getBean(clazzStr, type);
        }
        return result;
    }

    public Object getBean(String clazzStr) {

        Object result = null;
        if (appContext != null) {
            result = appContext.getBean(clazzStr);
        }
        return result;
    }
}
