/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.role;

import org.impensa.dao.role.RoleDMO;
import org.impensa.dao.role.RoleUpdateDMO;
import org.impensa.dao.role.RoleSearchCriteria;
import java.util.Set;
import org.impensa.exception.ImpensaException;




/**
 *
 * @author manosahu
 */
public interface IRoleService {
    /**
     * 
     * @param RoleSearchCriteria
     * @return
     * @throws ImpensaException 
     */
    public Set<RoleDMO> findBy(RoleSearchCriteria RoleSearchCriteria) throws ImpensaException;
   
    /**
     * This creates user from the input roleDMO
     * @param roleDMO
     * @return created roleDMO 
     * @throws ImpensaException 
     */
    public RoleDMO createRole(final RoleDMO roleDMO) throws ImpensaException;
    
    /**
     * 
     * @return
     * @throws ImpensaException 
     */
    public RoleDMO createTenantRole() throws ImpensaException;

    /**
     * This updates the user entity.
     * This takes RoleUpdateDMO as input.
     * It essentially works in org and role insert and delete id sets.
     * It also has userUpdateDMO which will be used to update various user's attribute.
     * However ImpensaException will be thrown if an attempt is made to update userId of the object.
     * TODO think about the above statement for its validity.
     * @param userUpdateDMO
     * @return updated roleDMO
     * @throws ImpensaException 
     */
    public RoleDMO updateRole(final RoleUpdateDMO userUpdateDMO) throws ImpensaException;

    /**
     * This deletes the user identified by roleDMO
     * @param roleDMO
     * @return true on success , false otherwise
     * @throws ImpensaException 
     */
    public boolean deleteRole(final RoleDMO roleDMO) throws ImpensaException;

   

}
