/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.startup;

import org.common.di.AppContainer;
import org.impensa.config.ImpensaSpringConfig;
import org.impensa.config.ImpensaSpringConfigTest;

/**
 *
 * @author manosahu
 */
public class ImpensaStartup {

    private static final String CLASSPATH_LOCATION = "classpath:spring/neo4j/spring-neo4j.xml";

    private static boolean inited = false;

    public static boolean startup() {
        return startup(ImpensaSpringConfig.class);
    }

    public static boolean testStartup() {
        return startup(ImpensaSpringConfigTest.class);
    }

    public static boolean startup(Class springConfigClazz) {
        boolean result;
        if (!inited) {
            result = startup(springConfigClazz, true);
        } else {
            result = false;
        }
        return result;

    }

    private static boolean startup(Class springConfigClazz, boolean enforce) {

        AppContainer.getInstance().registerConfig(springConfigClazz);
        if (enforce) {
            AppContainer.getInstance().getAppContext().refresh();
        }
        return true;
    }

}
