/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.startup;

import org.impensa.AppContainer;
import org.impensa.config.ImpensaSpringConfig;

/**
 *
 * @author manosahu
 */
public class ImpensaStartup {

    private static final String CLASSPATH_LOCATION = "classpath:spring/neo4j/spring-neo4j.xml";

    public static boolean startup() {

        //return AppContainer.getInstance().configureSpringContext(CLASSPATH_LOCATION);
        AppContainer.getInstance().registerConfig(ImpensaSpringConfig.class);
        AppContainer.getInstance().getAppContext().refresh();
        return true;
    }

}
