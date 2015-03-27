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
import org.impensa.service.dao.function.FunctionDAOException;
import org.impensa.service.dao.function.FunctionDMO;
import org.impensa.service.dao.function.IFunctionDAO;
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
    public Map<String, FunctionDMO> cacheFunctions() throws FunctionServiceException {
        Map<String, FunctionDMO> result;
        try {
            result = this.getFunctionDAO().cacheFunctions();
        } catch (FunctionDAOException ex) {
            logger.error("error in functionDAO.cacheFunctions", ex);
            throw new FunctionServiceException("error in functionDAO.cacheFunctions", ex);
        }
        return result;
    }

    @Override
    public Map<String, FunctionDMO> cacheFunctions(Class functionProviderClazz) throws FunctionServiceException {
        Map<String, FunctionDMO> result;
        try {
            result = this.getFunctionDAO().cacheFunctions(functionProviderClazz);
        } catch (FunctionDAOException ex) {
            logger.error("error in functionDAO.cacheFunctions(functionproviderclass)", ex);
            throw new FunctionServiceException("error in functionDAO.cacheFunctions(functionproviderclass)", ex);
        }
        return result;
    }

    @Override
    public FunctionDMO findByFunctionName(String functionName) throws FunctionServiceException {
        FunctionDMO result;
        try {
            result = this.getFunctionDAO().findByFunctionName(functionName);
        } catch (FunctionDAOException ex) {
            logger.error("error in functionDAO.findByFunctionName", ex);
            throw new FunctionServiceException("error in functionDAO.findByFunctionName", ex);
        }
        return result;
    }

    @Transactional
    @Override
    public FunctionDMO createFunction(FunctionDMO functionDMO) throws FunctionServiceException {
        FunctionDMO result;
        try {
            result = this.getFunctionDAO().createFunction(functionDMO);
        } catch (FunctionDAOException ex) {
            logger.error("error in functionDAO.createFunction", ex);
            throw new FunctionServiceException("error in functionDAO.createFunction", ex);
        }
        return result;
    }

    @Transactional
    @Override
    public FunctionDMO updateFunction(FunctionDMO functionDMO) throws FunctionServiceException {
        FunctionDMO result;
        try {
            result = this.getFunctionDAO().updateFunction(functionDMO);
        } catch (FunctionDAOException ex) {
            logger.error("error in functionDAO.updateFunction", ex);
            throw new FunctionServiceException("error in functionDAO.updateFunction", ex);
        }
        return result;
    }

    @Transactional
    @Override
    public boolean deleteFunction(FunctionDMO functionDMO) throws FunctionServiceException {
        boolean result;
        try {
            result = this.getFunctionDAO().deleteFunction(functionDMO);
        } catch (FunctionDAOException ex) {
            logger.error("error in functionDAO.deleteFunction", ex);
            throw new FunctionServiceException("error in functionDAO.deleteFunction", ex);
        }
        return result;
    }

    @Transactional
    @Override
    public Map<String, FunctionDMO> createAllFunctions() throws FunctionServiceException {
        Map<String, FunctionDMO> allFunctionDMOs = new HashMap<String, FunctionDMO>();

        try {
            Set<FunctionDMO> functionDMOs = new HashSet<FunctionDMO>();
            allFunctionDMOs = this.getFunctionDAO().cacheFunctions();

            for (Entry<String, FunctionDMO> entry : allFunctionDMOs.entrySet()) {
                functionDMOs.add(entry.getValue());
            }
            this.getFunctionDAO().createFunction(functionDMOs);
        } catch (FunctionDAOException ex) {
            logger.error("error in functionDAO.deleteFunction", ex);
            throw new FunctionServiceException("error in functionDAO.deleteFunction", ex);
        }
        return allFunctionDMOs;
    }

}
