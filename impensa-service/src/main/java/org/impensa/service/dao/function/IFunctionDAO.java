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
import java.util.Set;
import org.impensa.service.db.entity.FunctionEntity;
import org.impensa.service.exception.ImpensaException;

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
     * @throws org.impensa.service.dao.function.ImpensaException
     */
    public Map<String, FunctionDMO> cacheFunctions() throws ImpensaException;

    /**
     * This parses a functionproivder class object to retrieve the functions
     *
     * @param functionProviderClazz
     * @return
     * @throws org.impensa.service.dao.function.ImpensaException
     */
    public Map<String, FunctionDMO> cacheFunctions(Class functionProviderClazz) throws ImpensaException;

    /**
     *
     * @param functionName
     * @return
     * @throws ImpensaException
     */
    public FunctionDMO findByFunctionName(String functionName) throws ImpensaException;

    public Set<FunctionDMO> createFunction(final Set<FunctionDMO> functionDMOs) throws ImpensaException;

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

    /**
     * So client code make calls to this API for conversion. Let's us stop her
     * directly invoking DomainEntityConverter. That being static , we would end
     * up having lesser control.
     *
     * @param functionDMO
     * @return
     * @throws org.impensa.service.dao.function.ImpensaException
     */
    public FunctionEntity convertTo(final FunctionDMO functionDMO) throws ImpensaException;

    public Set<FunctionEntity> convertTo(final Set<FunctionDMO> functionDMOs) throws ImpensaException;

    /**
     * reciprocal of convertTo method.
     *
     * @param function
     * @return
     * @throws org.impensa.service.dao.function.ImpensaException
     */
    public FunctionDMO convertFrom(final FunctionEntity function) throws ImpensaException;

    public Set<FunctionDMO> convertFrom(final Set<FunctionEntity> functions) throws ImpensaException;

}
