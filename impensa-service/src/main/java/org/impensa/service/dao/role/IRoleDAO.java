/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.dao.role;




/**
 *
 * @author manosahu
 */
public interface IRoleDAO {

    /**
     * This creates user from the input roleDMO
     * @param roleDMO
     * @return created roleDMO 
     * @throws RoleDAOException 
     */
    public RoleDMO createRole(final RoleDMO roleDMO) throws RoleDAOException;

    /**
     * This updates the user entity.
     * This takes RoleUpdateDMO as input.
     * It essentially works in org and role insert and delete id sets.
     * It also has userUpdateDMO which will be used to update various user's attribute.
     * However RoleDAOException will be thrown if an attempt is made to update userId of the object.
     * TODO think about the above statement for its validity.
     * @param userUpdateDMO
     * @return updated roleDMO
     * @throws RoleDAOException 
     */
    public RoleDMO updateRole(final RoleUpdateDMO userUpdateDMO) throws RoleDAOException;

    /**
     * This deletes the user identified by roleDMO
     * @param roleDMO
     * @return true on success , false otherwise
     * @throws RoleDAOException 
     */
    public boolean deleteRole(final RoleDMO roleDMO) throws RoleDAOException;

    /**
     * This is same as deleteUser which takes roleDMO.
     * The only difference the user is identified by the input userId
     * @param userId
     * @return
     * @throws RoleDAOException 
     */
    public boolean deleteRole(final String roleId) throws RoleDAOException;

}
