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

import org.impensa.dao.function.FunctionDMO;
import org.impensa.exception.ImpensaException;

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
     * @throws org.impensa.service.function.ImpensaException
     */
    public Map<String, FunctionDMO> cacheFunctions() throws ImpensaException;

    /**
     * This parses a functionproivder class object to retrieve the functions
     *
     * @param functionProviderClazz
     * @return
     * @throws org.impensa.service.function.ImpensaException
     */
    public Map<String, FunctionDMO> cacheFunctions(Class functionProviderClazz) throws ImpensaException;

    /**
     *
     * @param functionName
     * @return
     * @throws ImpensaException
     */
    public FunctionDMO findByFunctionName(String functionName) throws ImpensaException;

    /**
     * 
     * @return
     * @throws ImpensaException 
     */
    public Map<String, FunctionDMO> createAllFunctions() throws ImpensaException;

    
    /**
     *
     * @param functionDMO
     * @return
     * @throws ImpensaException
     */
    public FunctionDMO createFunction(final FunctionDMO functionDMO) throws ImpensaException;

    /**
     *
     * @param functionDMO
     * @return
     * @throws ImpensaException
     */
    public FunctionDMO updateFunction(final FunctionDMO functionDMO) throws ImpensaException;

    /**
     *
     * @param functionDMO
     * @return
     * @throws ImpensaException
     */
    public boolean deleteFunction(final FunctionDMO functionDMO) throws ImpensaException;
}
