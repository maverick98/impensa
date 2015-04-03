/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.dao.tenant;

import org.impensa.db.entity.TenantEntity;
import org.impensa.exception.ImpensaException;

/**
 *
 * @author manosahu
 */
public interface ITenantDAO {

    public TenantDMO findByTenantId(final String tenantId) throws ImpensaException;

    /**
     * This creates tenant from the input tenantDMO
     *
     * @param tenantDMO
     * @return created TenantDMO
     * @throws ImpensaException
     */
    public TenantDMO createTenant(final TenantDMO tenantDMO) throws ImpensaException;

 
    /**
     * This deletes the tenant identified by orgDMO
     *
     * @param tenantDMO
     * @return true on success , false otherwise
     * @throws ImpensaException
     */
    public boolean deleteTenant(final TenantDMO tenantDMO) throws ImpensaException;

    /**
     * This is same as deleteTenant which takes tenantId. The only difference the
     * user is identified by the input userId
     *
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
