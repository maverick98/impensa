/**
 * This file is part of impensa. CopyLeft (C) BigBang<->BigCrunch.All Rights are
 * left.
 *
 * 1) Modify it if you can understand. 2) If you distribute a modified version,
 * you must do it at your own risk.
 *
 */
package org.impensa.startup;

import org.common.di.AppContainer;
import org.hawk.executor.command.parasite.IContainerStartup;
import org.impensa.config.ImpensaSpringConfig;

/**
 *
 * @author msahu98
 */
public class ImpensaContainerStartup implements IContainerStartup {

    @Override
    public boolean startup() throws Exception {
        System.out.println("ok I am called!!!");
        AppContainer.getInstance().registerContainerConfig(ImpensaSpringConfig.class);
        AppContainer.getInstance().refreshAppContainerCtx();
        return true;
    }

}
