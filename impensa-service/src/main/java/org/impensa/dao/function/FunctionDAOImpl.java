/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.dao.function;

import org.impensa.function.Function;
import org.impensa.function.IFunctionProvider;
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
import org.impensa.db.entity.FunctionEntity;
import org.impensa.db.repository.FunctionRepository;
import org.impensa.exception.BeanConversionErrorCode;
import org.impensa.exception.ImpensaException;
import org.impensa.exception.ValidationErrorCode;

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
    public Map<String, FunctionDMO> cacheFunctions() throws ImpensaException {

        return this.cacheFunctionsInternal();
    }

    private Map<String, FunctionDMO> cacheFunctionsInternal() throws ImpensaException {

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
    public Map<String, FunctionDMO> cacheFunctions(Class functionProviderClazz) throws ImpensaException {
        if (functionProviderClazz == null) {
            throw new ImpensaException(ValidationErrorCode.VALUE_NULL).set("functionProviderClazz", "null");
        }
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
    public FunctionDMO findByFunctionName(String functionName) throws ImpensaException {
        if (StringUtil.isNullOrEmpty(functionName)) {
            throw new ImpensaException(ValidationErrorCode.VALUE_NULL_OR_EMPTY).set("functionName", "null or empty");
        }
        return this.getFunctionCache().get(functionName);
    }

    @Override
    @Transactional
    public FunctionDMO createFunction(final FunctionDMO functionDMO) throws ImpensaException {
        if (functionDMO == null) {
            throw new ImpensaException(ValidationErrorCode.VALUE_NULL).set("functionDMO", "null");
        }
        FunctionEntity functionEntity = this.convertTo(functionDMO);
        this.getFunctionRepository().save(functionEntity);
        return functionDMO;
    }

    @Transactional
    @Override
    public Set<FunctionDMO> createFunction(Set<FunctionDMO> functionDMOs) throws ImpensaException {
        if (functionDMOs == null) {
            throw new ImpensaException(ValidationErrorCode.VALUE_NULL).set("functionDMOs", "null");
        }
        Set<FunctionEntity> all = this.convertTo(functionDMOs);
        this.getFunctionRepository().save(all);
        return this.convertFrom(all);
    }

    @Override
    @Transactional
    public FunctionDMO updateFunction(FunctionDMO functionDMO) throws ImpensaException {
        if (functionDMO== null) {
            throw new ImpensaException(ValidationErrorCode.VALUE_NULL).set("functionDMO", "null");
        }
        org.impensa.db.entity.FunctionEntity function = this.convertTo(functionDMO);
        this.getFunctionRepository().save(function);
        return functionDMO;
    }

    @Override
    @Transactional
    public boolean deleteFunction(FunctionDMO functionDMO) throws ImpensaException {
        if (functionDMO == null) {
            throw new ImpensaException(ValidationErrorCode.VALUE_NULL).set("functionDMO", "null");
        }
        FunctionEntity functionEntity = this.convertTo(functionDMO);
        this.getFunctionRepository().delete(functionEntity);
        return true;
    }

    @Override
    public FunctionEntity convertTo(final FunctionDMO functionDMO) throws ImpensaException {
        if (functionDMO == null) {
            return null;
        }
        FunctionEntity functionEntity;
        try {
            functionEntity = BeanConverter.toMappingBean(functionDMO, FunctionEntity.class);
        } catch (Exception ex) {

            throw ImpensaException.wrap(ex)
                    .setErrorCode(BeanConversionErrorCode.TO_MAPPING_BEAN)
                    .set("functionDMO", functionDMO);
        }
        return functionEntity;
    }

    @Override
    public FunctionDMO convertFrom(final FunctionEntity functionEntity) throws ImpensaException {
        if (functionEntity == null) {
            return null;
        }
        FunctionDMO functionDMO;
        try {
            functionDMO = BeanConverter.fromMappingBean(functionEntity, FunctionDMO.class);
        } catch (Exception ex) {
            throw ImpensaException.wrap(ex)
                    .setErrorCode(BeanConversionErrorCode.FROM_MAPPING_BEAN)
                    .set("functionEntity", functionEntity);

        }
        return functionDMO;
    }

    @Override
    public Set<FunctionEntity> convertTo(Set<FunctionDMO> functionDMOs) throws ImpensaException {
        if (functionDMOs == null) {
            return null;
        }
        Set<FunctionEntity> result = new HashSet<FunctionEntity>();

        for (FunctionDMO functionDMO : functionDMOs) {
            result.add(this.convertTo(functionDMO));
        }

        return result;
    }

    @Override
    public Set<FunctionDMO> convertFrom(Set<FunctionEntity> functionEntities) throws ImpensaException {
        if (functionEntities == null) {
            return null;
        }

        Set<FunctionDMO> result = new HashSet<FunctionDMO>();

        for (FunctionEntity function : functionEntities) {
            result.add(this.convertFrom(function));
        }

        return result;
    }

}
