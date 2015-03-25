/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.dao.role;

import java.util.Set;
import org.impensa.service.dao.user.UserDAOException;
import org.impensa.service.dao.user.UserDMO;
import org.impensa.service.db.entity.RoleEntity;
import org.impensa.service.db.entity.UserEntity;




/**
 *
 * @author manosahu
 */
public interface IRoleDAO {
    /**
     * 
     * @param RoleSearchCriteria
     * @return
     * @throws RoleDAOException 
     */
    public Set<RoleDMO> findBy(RoleSearchCriteria RoleSearchCriteria) throws RoleDAOException;
   
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
     * So client code make calls to this API for conversion.
     * Let's us stop her directly invoking DomainEntityConverter. That being static
     * , we would end up having lesser control.
     * @param roleDMO
     * @return
     * @throws UserDAOException 
     */
    public RoleEntity convertTo(final RoleDMO roleDMO) throws RoleDAOException ;
    
    /**
     * reciprocal of convertTo method.
     * @param role
     * @return 
     * @throws org.impensa.service.dao.role.RoleDAOException 
     */
    public RoleDMO convertFrom(final RoleEntity role) throws RoleDAOException ;
    
    /**
     * 
     * @param roles
     * @return 
     * @throws org.impensa.service.dao.role.RoleDAOException 
     */
    public Set<RoleDMO> convertFrom(Set<RoleEntity> roles) throws RoleDAOException;

}
