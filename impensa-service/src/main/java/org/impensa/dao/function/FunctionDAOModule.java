/**
 * This file is part of impensa. CopyLeft (C) BigBang<->BigCrunch.All Rights are
 * left.
 *
 * 1) Modify it if you can understand. 2) If you distribute a modified version,
 * you must do it at your own risk.
 *
 */
package org.impensa.dao.function;

import org.common.di.AppContainer;
import org.common.di.ScanMe;
import org.hawk.module.annotation.SubTask;
import org.hawk.module.plugin.HawkPluginModule;

/**
 *
 * @author msahu98
 */
@ScanMe(true)
public class FunctionDAOModule extends HawkPluginModule {

    @Override
    public String getPluginName() {
        return "impensa";
    }

    public String getName() {
        return "functionDAO";
    }

    @Override
    public boolean startUp() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean reset() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    @SubTask(name = "cache ", sequence = 1, ignoreException = false, hawkParam = " ")
    public String cache(){
        IFunctionDAO functionDAO = AppContainer.getInstance().getBean(FunctionDAOImpl.class);
        functionDAO.cacheFunctions();
        return "functions caches";
    }
}
