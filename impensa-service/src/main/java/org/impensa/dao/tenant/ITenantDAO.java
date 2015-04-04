/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.dao.tenant;

import org.impensa.db.TenantGraphDatabaseService;
import org.impensa.db.entity.TenantEntity;
import org.impensa.exception.ImpensaException;
import org.neo4j.graphdb.GraphDatabaseService;

/**
 *
 * @author manosahu
 */
public interface ITenantDAO {
    
    public boolean registerTenantDatabaseService(String tenantId) throws ImpensaException;
    
    public TenantGraphDatabaseService findTenantGraphDatabaseService(String tenantId) throws ImpensaException;
    
    public boolean shutdownTenantDatabaseService(TenantGraphDatabaseService tenantGraphDatabseService) throws ImpensaException;
    
    public boolean shutdownTenantDatabaseService(GraphDatabaseService graphDatabseService) throws ImpensaException;
   
    public boolean shutdownTenantDatabaseService(String tenantId) throws ImpensaException;
     
    /**
     * If the tenant identified by tenantId exists in impensa , 
     * this api returns the TenantDMO object. 
     * This simply checks in the main database. It does NOT 
     * check for the existence of tenant database.
     * @param tenantId
     * @return
     * @throws ImpensaException 
     */
    public TenantDMO isTenantRegistered(final String tenantId) throws ImpensaException;
    
    public boolean isTenantDatabaseCreated(final String tenantId) throws ImpensaException;
    /**
     * This searches the tenant in the main database.
     * @param tenantId
     * @return
     * @throws ImpensaException 
     */
    public TenantDMO findByTenantId(final String tenantId) throws ImpensaException;

    /**
     * This creates tenant from the input tenantDMO in the main database.
     *
     * @param tenantDMO
     * @return created TenantDMO
     * @throws ImpensaException
     */
    public TenantDMO createTenant(final TenantDMO tenantDMO) throws ImpensaException;

    /**
     * This creates tenant database .
     * 
     * @param tenantDMO
     * @return
     * @throws ImpensaException 
     */
    public boolean createTenantDatabase(final TenantDMO tenantDMO ) throws ImpensaException;
 
    /**
     * Think TWICE before invoking this API
     * This deletes the tenant identified by tenantDMO
     * This deletes the tenant from the main database 
     * and deletes the tenant database.
     * So this essentially wipes out the tenant information completely from the system
     * @param tenantDMO
     * @return true on success , false otherwise
     * @throws ImpensaException
     */
    public boolean deleteTenant(final TenantDMO tenantDMO) throws ImpensaException;

    /**
     * Think TWICE before invoking this API
     * This is same as deleteTenant which takes tenantId. The only difference the
     * user is identified by the input userId
     * So this essentially wipes out the tenant information completely from the system
     * @param tenantId
     * @return
     * @throws ImpensaException
     */
    public boolean deleteTenant(final String tenantId) throws ImpensaException;

    /**
     * So client code make calls to this API for conversion. Let's us stop her
     * directly invoking DomainEntityConverter. That being static , we would end
     * up having lesser control.
     *
     * @param tenantDMO
     * @return
     * @throws ImpensaException
     */
    public TenantEntity convertTo(final TenantDMO tenantDMO) throws ImpensaException;

    /**
     * reciprocal of convertTo method.
     *
     * @param tenantEntity
     * @return
     * @throws ImpensaException
     */
    public TenantDMO convertFrom(final TenantEntity tenantEntity) throws ImpensaException;
}
