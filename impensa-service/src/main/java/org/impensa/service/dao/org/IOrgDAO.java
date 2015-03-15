/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.dao.org;

/**
 *
 * @author manosahu
 */
public interface IOrgDAO {

     /**
     * This creates org from the input orgDMO
     * @param orgDMO
     * @return created orgDMO 
     * @throws OrgDAOException 
     */
    public OrgDMO createOrg(final OrgDMO orgDMO) throws OrgDAOException;

    /**
     * This updates the org entity.
     * This takes orgUpdateDMO as input.
     * It essentially works in org and role insert and delete id sets.
     * It also has userUpdateDMO which will be used to update various user's attribute.
     * However OrgDAOException will be thrown if an attempt is made to update userId of the object.
     * TODO think about the above statement for its validity.
     * @param orgUpdateDMO
     * @return updated orgDMO
     * @throws OrgDAOException 
     */
    public OrgDMO updateOrg(final OrgUpdateDMO orgUpdateDMO) throws OrgDAOException;

    /**
     * This deletes the user identified by orgDMO
     * @param orgDMO
     * @return true on success , false otherwise
     * @throws OrgDAOException 
     */
    public boolean deleteOrg(final OrgDMO orgDMO) throws OrgDAOException;

    /**
     * This is same as deleteUser which takes orgDMO.
     * The only difference the user is identified by the input userId
     * @param orgId
     * @return
     * @throws OrgDAOException 
     */
    public boolean deleteOrg(final String orgId) throws OrgDAOException; 
}
