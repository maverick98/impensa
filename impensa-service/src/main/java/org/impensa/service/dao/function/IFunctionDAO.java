/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch Manoranjan Sahu. All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.dao.function;

import java.util.Set;

/**
 *
 * @author manosahu
 */
public interface IFunctionDAO {

    /**
     * This caches functions from classes that 
     * are annotated with FunctionProvider.
     * Essentially the mehthods with Function annotation
     * are the candidates for caching.
     * @return collection of FunctionDMOs
     * @throws org.impensa.service.dao.function.FunctionDAOException
     */
    public Set<FunctionDMO> cacheFunctions() throws FunctionDAOException;
    
    /**
     * This parses a functionproivderclass object to retrieve the functions
     * @param functionProviderClazz
     * @return 
     * @throws org.impensa.service.dao.function.FunctionDAOException 
     */
    public Set<FunctionDMO> cacheFunctions(Class functionProviderClazz) throws FunctionDAOException;
}
