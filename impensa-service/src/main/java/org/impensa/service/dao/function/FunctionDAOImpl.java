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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.common.bean.BeanConverter;
import org.common.di.AppContainer;
import org.commons.logger.ILogger;
import org.commons.logger.LoggerFactory;
import org.commons.string.StringUtil;
import org.impensa.service.db.entity.FunctionEntity;
import org.impensa.service.db.repository.FunctionRepository;

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

    @Transactional
    @Override
    public Set<FunctionDMO> createFunction(Set<FunctionDMO> functionDMOs) throws FunctionDAOException {
        Set<FunctionEntity> all = this.convertTo(functionDMOs);
        this.getFunctionRepository().save(all);
        return this.convertFrom(all);
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
    public FunctionEntity convertTo(final FunctionDMO functionDMO) throws FunctionDAOException {
        FunctionEntity functionEntity;
        try {
            functionEntity = BeanConverter.toMappingBean(functionDMO, FunctionEntity.class);
        } catch (Exception ex) {
            logger.error("error while converting to entity object " + functionDMO, ex);
            throw new FunctionDAOException("error while converting to entity object " + functionDMO, ex);
        }
        return functionEntity;
    }

    @Override
    public FunctionDMO convertFrom(final FunctionEntity function) throws FunctionDAOException {
        FunctionDMO functionDMO;
        try {
            functionDMO = BeanConverter.fromMappingBean(function, FunctionDMO.class);
        } catch (Exception ex) {
            logger.error("error while converting from entity object " + function, ex);
            throw new FunctionDAOException("error while converting from entity object " + function, ex);
        }
        return functionDMO;
    }

    @Override
    public Set<FunctionEntity> convertTo(Set<FunctionDMO> functionDMOs) throws FunctionDAOException {
        Set<FunctionEntity> result = new HashSet<FunctionEntity>();
        if (functionDMOs != null && !functionDMOs.isEmpty()) {
            for (FunctionDMO functionDMO : functionDMOs) {
                result.add(this.convertTo(functionDMO));
            }
        }
        return result;
    }

    @Override
    public Set<FunctionDMO> convertFrom(Set<FunctionEntity> functions) throws FunctionDAOException {
        Set<FunctionDMO> result = new HashSet<FunctionDMO>();
        if (functions != null && !functions.isEmpty()) {
            for (FunctionEntity function : functions) {
                result.add(this.convertFrom(function));
            }
        }
        return result;
    }

}
