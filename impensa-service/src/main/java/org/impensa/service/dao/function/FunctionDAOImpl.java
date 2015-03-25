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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.common.di.AppContainer;
import org.commons.logger.ILogger;
import org.commons.logger.LoggerFactory;
import org.commons.string.StringUtil;
import org.impensa.service.db.repository.FunctionRepository;
import org.impensa.service.util.DomainEntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author manosahu
 */
@Component
public class FunctionDAOImpl implements IFunctionDAO {

    private static final ILogger logger = LoggerFactory.getLogger(FunctionDAOImpl.class.getName());

    private Map<String, FunctionDMO> functionCache = new HashMap<String, FunctionDMO>();

    @Autowired
    private FunctionRepository functionRepository;

    public FunctionRepository getFunctionRepository() {
        return functionRepository;
    }

    public void setFunctionRepository(FunctionRepository functionRepository) {
        this.functionRepository = functionRepository;
    }

    public Map<String, FunctionDMO> getFunctionCache() {
        return functionCache;
    }

    @Override
    public Map<String, FunctionDMO> cacheFunctions() throws FunctionDAOException {

        return this.cacheFunctionsInternal();
    }

    private Map<String, FunctionDMO> cacheFunctionsInternal() throws FunctionDAOException {

        List<Class> functionProviderClasses = AppContainer.getInstance().getSubTypesOf(IFunctionProvider.class);

        if (functionProviderClasses != null) {

            for (Class functionProviderClazz : functionProviderClasses) {
                Map<String, FunctionDMO> functions = this.cacheFunctions(functionProviderClazz);
                this.getFunctionCache().putAll(functions);
            }
        }
        return this.getFunctionCache();
    }

    @Override
    public Map<String, FunctionDMO> cacheFunctions(Class functionProviderClazz) throws FunctionDAOException {

        Map<String, FunctionDMO> result = new HashMap<String, FunctionDMO>();

        Method[] methods = functionProviderClazz.getDeclaredMethods();

        for (Method method : methods) {

            if (method.isAnnotationPresent(Function.class)) {

                Function function = (Function) method.getAnnotation(Function.class);

                FunctionDMO functionDMO = new FunctionDMO();

                functionDMO.setFunctionName(function.name());

                functionDMO.setFunctionDescription(function.description());

                result.put(functionDMO.getFunctionName(), functionDMO);

            }
        }
        return result;
    }

    @Override
    public FunctionDMO findByFunctionName(String functionName) throws FunctionDAOException {
        if (StringUtil.isNullOrEmpty(functionName)) {
            throw new IllegalArgumentException("null or empty functionaname");
        }
        return this.getFunctionCache().get(functionName);
    }

    @Override
    @Transactional
    public FunctionDMO createFunction(final FunctionDMO functionDMO) throws FunctionDAOException {
        org.impensa.service.db.entity.FunctionEntity function = this.convertTo(functionDMO);
        this.getFunctionRepository().save(function);
        return functionDMO;
    }

    @Override
    @Transactional
    public FunctionDMO updateFunction(FunctionDMO functionDMO) throws FunctionDAOException {
        org.impensa.service.db.entity.FunctionEntity function = this.convertTo(functionDMO);
        this.getFunctionRepository().save(function);
        return functionDMO;
    }

    @Override
    @Transactional
    public boolean deleteFunction(FunctionDMO functionDMO) throws FunctionDAOException {
        org.impensa.service.db.entity.FunctionEntity function = this.convertTo(functionDMO);
        this.getFunctionRepository().delete(function);
        return true;
    }

    @Override
    public org.impensa.service.db.entity.FunctionEntity convertTo(final FunctionDMO functionDMO) throws FunctionDAOException {
        org.impensa.service.db.entity.FunctionEntity function;
        try {
            function = DomainEntityConverter.toEntity(functionDMO, org.impensa.service.db.entity.FunctionEntity.class);
        } catch (Exception ex) {
            logger.error("error while converting to entity object " + functionDMO, ex);
            throw new FunctionDAOException("error while converting to entity object " + functionDMO, ex);
        }
        return function;
    }

    @Override
    public FunctionDMO convertFrom(final org.impensa.service.db.entity.FunctionEntity function) throws FunctionDAOException {
        FunctionDMO functionDMO;
        try {
            functionDMO = DomainEntityConverter.toDomain(function, FunctionDMO.class);
        } catch (Exception ex) {
            logger.error("error while converting from entity object " + function, ex);
            throw new FunctionDAOException("error while converting from entity object " + function, ex);
        }
        return functionDMO;
    }

}
