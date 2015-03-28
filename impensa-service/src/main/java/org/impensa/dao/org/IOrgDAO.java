/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.dao.org;

import org.impensa.db.entity.OrgEntity;
import org.impensa.exception.ImpensaException;

/**
 *
 * @author manosahu
 */
public interface IOrgDAO {

    public OrgDMO findByOrgId(final String orgId) throws ImpensaException;

    /**
     * This creates org from the input orgDMO
     *
     * @param orgDMO
     * @return created orgDMO
     * @throws ImpensaException
     */
    public OrgDMO createOrg(final OrgDMO orgDMO) throws ImpensaException;

    /**
     * This updates the org entity. This takes orgUpdateDMO as input. It
     * essentially works in org and role insert and delete id sets. It also has
     * userUpdateDMO which will be used to update various user's attribute.
     * However ImpensaException will be thrown if an attempt is made to update
     * userId of the object. TODO think about the above statement for its
     * validity.
     *
     * @param orgUpdateDMO
     * @return updated orgDMO
     * @throws ImpensaException
     */
    public OrgDMO updateOrg(final OrgUpdateDMO orgUpdateDMO) throws ImpensaException;

    /**
     * This deletes the user identified by orgDMO
     *
     * @param orgDMO
     * @return true on success , false otherwise
     * @throws ImpensaException
     */
    public boolean deleteOrg(final OrgDMO orgDMO) throws ImpensaException;

    /**
     * This is same as deleteOrg which takes orgDMO. The only difference the
     * user is identified by the input userId
     *
     * @param orgId
     * @return
     * @throws ImpensaException
     */
    public boolean deleteOrg(final String orgId) throws ImpensaException;

    /**
     * So client code make calls to this API for conversion. Let's us stop her
     * directly invoking DomainEntityConverter. That being static , we would end
     * up having lesser control.
     *
     * @param orgDMO
     * @return
     * @throws ImpensaException
     */
    public OrgEntity convertTo(final OrgDMO orgDMO) throws ImpensaException;

    /**
     * reciprocal of convertTo method.
     *
     * @param org
     * @return
     * @throws ImpensaException
     */
    public OrgDMO convertFrom(final OrgEntity org) throws ImpensaException;
}
