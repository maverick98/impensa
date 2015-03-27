/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.function;

import java.util.Map;

import org.impensa.service.dao.function.FunctionDMO;

/**
 *
 * @author manosahu
 */
public interface IFunctionService {

    /**
     * This caches functions from classes that implements the marker interface
     * IFunctionProvider Essentially the methods with Function annotation are
     * the candidates for caching.
     *
     * @return collection of FunctionDMOs
     * @throws org.impensa.service.function.FunctionServiceException
     */
    public Map<String, FunctionDMO> cacheFunctions() throws FunctionServiceException;

    /**
     * This parses a functionproivder class object to retrieve the functions
     *
     * @param functionProviderClazz
     * @return
     * @throws org.impensa.service.function.FunctionServiceException
     */
    public Map<String, FunctionDMO> cacheFunctions(Class functionProviderClazz) throws FunctionServiceException;

    /**
     *
     * @param functionName
     * @return
     * @throws FunctionServiceException
     */
    public FunctionDMO findByFunctionName(String functionName) throws FunctionServiceException;

    /**
     * 
     * @return
     * @throws FunctionServiceException 
     */
    public Map<String, FunctionDMO> createAllFunctions() throws FunctionServiceException;

    
    /**
     *
     * @param functionDMO
     * @return
     * @throws FunctionServiceException
     */
    public FunctionDMO createFunction(final FunctionDMO functionDMO) throws FunctionServiceException;

    /**
     *
     * @param functionDMO
     * @return
     * @throws FunctionServiceException
     */
    public FunctionDMO updateFunction(final FunctionDMO functionDMO) throws FunctionServiceException;

    /**
     *
     * @param functionDMO
     * @return
     * @throws FunctionServiceException
     */
    public boolean deleteFunction(final FunctionDMO functionDMO) throws FunctionServiceException;
}
