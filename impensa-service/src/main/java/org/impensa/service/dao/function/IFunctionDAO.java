/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.dao.function;

import java.util.Map;
import org.impensa.service.dao.org.OrgDAOException;


/**
 *
 * @author manosahu
 */
public interface IFunctionDAO {

    /**
     * This caches functions from classes that implements the marker interface
     * IFunctionProvider Essentially the methods with Function annotation are
     * the candidates for caching.
     *
     * @return collection of FunctionDMOs
     * @throws org.impensa.service.dao.function.FunctionDAOException
     */
    public Map<String, FunctionDMO> cacheFunctions() throws FunctionDAOException;

    /**
     * This parses a functionproivder class object to retrieve the functions
     *
     * @param functionProviderClazz
     * @return
     * @throws org.impensa.service.dao.function.FunctionDAOException
     */
    public Map<String, FunctionDMO> cacheFunctions(Class functionProviderClazz) throws FunctionDAOException;

    /**
     *
     * @param functionName
     * @return
     * @throws FunctionDAOException
     */
    public FunctionDMO findByFunctionName(String functionName) throws FunctionDAOException;

    /**
     *
     * @param functionDMO
     * @return
     * @throws FunctionDAOException
     */
    public FunctionDMO createFunction(final FunctionDMO functionDMO) throws FunctionDAOException;
    
    /**
     * 
     * @param functionDMO
     * @return
     * @throws FunctionDAOException 
     */
    public FunctionDMO updateFunction(final FunctionDMO functionDMO) throws FunctionDAOException;
    
    /**
     * 
     * @param functionDMO
     * @return
     * @throws FunctionDAOException 
     */
    public boolean deleteFunction(final FunctionDMO functionDMO) throws FunctionDAOException;
    
     /**
     * So client code make calls to this API for conversion. Let's us stop her
     * directly invoking DomainEntityConverter. That being static , we would end
     * up having lesser control.
     *
     * @param functionDMO
     * @return
     * @throws OrgDAOException
     */
    public org.impensa.service.db.entity.FunctionEntity convertTo(final FunctionDMO functionDMO) throws FunctionDAOException;

    /**
     * reciprocal of convertTo method.
     *
     * @param function
     * @return
     * @throws OrgDAOException
     */
    public FunctionDMO convertFrom(final org.impensa.service.db.entity.FunctionEntity function) throws FunctionDAOException;
}
