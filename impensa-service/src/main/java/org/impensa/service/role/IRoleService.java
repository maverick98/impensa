/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.role;

import org.impensa.service.dao.role.*;
import java.util.Set;




/**
 *
 * @author manosahu
 */
public interface IRoleService {
    /**
     * 
     * @param RoleSearchCriteria
     * @return
     * @throws RoleServiceException 
     */
    public Set<RoleDMO> findBy(RoleSearchCriteria RoleSearchCriteria) throws RoleServiceException;
   
    /**
     * This creates user from the input roleDMO
     * @param roleDMO
     * @return created roleDMO 
     * @throws RoleServiceException 
     */
    public RoleDMO createRole(final RoleDMO roleDMO) throws RoleServiceException;
    
    /**
     * 
     * @return
     * @throws RoleServiceException 
     */
    public RoleDMO createTenantRole() throws RoleServiceException;

    /**
     * This updates the user entity.
     * This takes RoleUpdateDMO as input.
     * It essentially works in org and role insert and delete id sets.
     * It also has userUpdateDMO which will be used to update various user's attribute.
     * However RoleServiceException will be thrown if an attempt is made to update userId of the object.
     * TODO think about the above statement for its validity.
     * @param userUpdateDMO
     * @return updated roleDMO
     * @throws RoleServiceException 
     */
    public RoleDMO updateRole(final RoleUpdateDMO userUpdateDMO) throws RoleServiceException;

    /**
     * This deletes the user identified by roleDMO
     * @param roleDMO
     * @return true on success , false otherwise
     * @throws RoleServiceException 
     */
    public boolean deleteRole(final RoleDMO roleDMO) throws RoleServiceException;

   

}
