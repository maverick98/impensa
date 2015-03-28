/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.function;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.commons.logger.ILogger;
import org.commons.logger.LoggerFactory;
import org.impensa.dao.function.FunctionDMO;
import org.impensa.dao.function.IFunctionDAO;
import org.impensa.exception.ImpensaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author manosahu
 */
@Service
public class FunctionServiceImpl implements IFunctionService {

    private static final ILogger logger = LoggerFactory.getLogger(FunctionServiceImpl.class.getName());

    @Autowired
    private IFunctionDAO functionDAOImpl;

    public IFunctionDAO getFunctionDAO() {
        return functionDAOImpl;
    }

    public void setFunctionDAO(IFunctionDAO functionDAO) {
        this.functionDAOImpl = functionDAO;
    }

    @Override
    public Map<String, FunctionDMO> cacheFunctions() throws ImpensaException {
        Map<String, FunctionDMO> result;

        result = this.getFunctionDAO().cacheFunctions();

        return result;
    }

    @Override
    public Map<String, FunctionDMO> cacheFunctions(Class functionProviderClazz) throws ImpensaException {
        Map<String, FunctionDMO> result;
        result = this.getFunctionDAO().cacheFunctions(functionProviderClazz);

        return result;
    }

    @Override
    public FunctionDMO findByFunctionName(String functionName) throws ImpensaException {
        FunctionDMO result;
        result = this.getFunctionDAO().findByFunctionName(functionName);

        return result;
    }

    @Transactional
    @Override
    public FunctionDMO createFunction(FunctionDMO functionDMO) throws ImpensaException {
        FunctionDMO result;
        result = this.getFunctionDAO().createFunction(functionDMO);

        return result;
    }

    @Transactional
    @Override
    public FunctionDMO updateFunction(FunctionDMO functionDMO) throws ImpensaException {
        FunctionDMO result;
        result = this.getFunctionDAO().updateFunction(functionDMO);

        return result;
    }

    @Transactional
    @Override
    public boolean deleteFunction(FunctionDMO functionDMO) throws ImpensaException {
        boolean result;
        result = this.getFunctionDAO().deleteFunction(functionDMO);

        return result;
    }

    @Transactional
    @Override
    public Map<String, FunctionDMO> createAllFunctions() throws ImpensaException {
        Map<String, FunctionDMO> allFunctionDMOs = new HashMap<String, FunctionDMO>();

        Set<FunctionDMO> functionDMOs = new HashSet<FunctionDMO>();
        allFunctionDMOs = this.getFunctionDAO().cacheFunctions();

        for (Entry<String, FunctionDMO> entry : allFunctionDMOs.entrySet()) {
            functionDMOs.add(entry.getValue());
        }
        this.getFunctionDAO().createFunction(functionDMOs);

        return allFunctionDMOs;
    }

}
