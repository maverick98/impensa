/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.dao.function;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.common.di.AppContainer;
import org.commons.logger.ILogger;
import org.commons.logger.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author manosahu
 */
@Component
public class FunctionDAOImpl implements IFunctionDAO {

    private static final ILogger logger = LoggerFactory.getLogger(FunctionDAOImpl.class.getName());

    @Override
    public Set<FunctionDMO> cacheFunctions() throws FunctionDAOException {

        return this.cacheFunctionsInternal();
    }

    private Set<FunctionDMO> cacheFunctionsInternal() throws FunctionDAOException {
        
        Set<FunctionDMO> result = new HashSet<FunctionDMO>();
        
        List<Class> functionProviderClasses = AppContainer.getInstance().getSubTypesOf(IFunctionProvider.class);
        
        if (functionProviderClasses != null) {
        
            for (Class functionProviderClazz : functionProviderClasses) {
            
                //System.out.println(functionProviderClazz);
                
                result.addAll(this.cacheFunctions(functionProviderClazz));
            }
        }
        return result;
    }

    @Override
    public Set<FunctionDMO> cacheFunctions(Class functionProviderClazz) throws FunctionDAOException {
        
        Set<FunctionDMO> result = new HashSet<FunctionDMO>();
        
        Method[] methods = functionProviderClazz.getDeclaredMethods();
        
        for (Method method : methods) {
        
            if (method.isAnnotationPresent(Function.class)) {
            
                Function function = (Function) method.getAnnotation(Function.class);
                
                FunctionDMO functionDMO = new FunctionDMO();
                
                functionDMO.setName(function.name());
                
                functionDMO.setDescription(function.description());
                
                result.add(functionDMO);
                
                //System.out.println(functionDMO);
            }
        }
        return result;
    }

}
