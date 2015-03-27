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
import org.impensa.service.db.entity.RoleEntity;
import org.impensa.service.exception.ImpensaException;

/**
 *
 * @author manosahu
 */
public interface IRoleDAO {

    /**
     * 
     * @param roleId
     * @return
     * @throws ImpensaException 
     */
    public RoleDMO findByRoleId(final String roleId) throws ImpensaException;

    /**
     *
     * @param RoleSearchCriteria
     * @return
     * @throws ImpensaException
     */
    public Set<RoleDMO> findBy(RoleSearchCriteria RoleSearchCriteria) throws ImpensaException;

    /**
     * This creates user from the input roleDMO
     *
     * @param roleDMO
     * @return created roleDMO
     * @throws ImpensaException
     */
    public RoleDMO createRole(final RoleDMO roleDMO) throws ImpensaException;

    /**
     * This updates the user entity. This takes RoleUpdateDMO as input. It
     * essentially works in org and role insert and delete id sets. It also has
     * userUpdateDMO which will be used to update various user's attribute.
     * However ImpensaException will be thrown if an attempt is made to update
     * userId of the object. TODO think about the above statement for its
     * validity.
     *
     * @param userUpdateDMO
     * @return updated roleDMO
     * @throws ImpensaException
     */
    public RoleDMO updateRole(final RoleUpdateDMO userUpdateDMO) throws ImpensaException;

    /**
     * This deletes the user identified by roleDMO
     *
     * @param roleDMO
     * @return true on success , false otherwise
     * @throws ImpensaException
     */
    public boolean deleteRole(final RoleDMO roleDMO) throws ImpensaException;

    /**
     * So client code make calls to this API for conversion. Let's us stop her
     * directly invoking DomainEntityConverter. That being static , we would end
     * up having lesser control.
     *
     * @param roleDMO
     * @return
     * @throws ImpensaException
     */
    public RoleEntity convertTo(final RoleDMO roleDMO) throws ImpensaException;

    /**
     * reciprocal of convertTo method.
     *
     * @param role
     * @return
     * @throws org.impensa.service.dao.role.ImpensaException
     */
    public RoleDMO convertFrom(final RoleEntity role) throws ImpensaException;

    /**
     *
     * @param roles
     * @return
     * @throws org.impensa.service.dao.role.ImpensaException
     */
    public Set<RoleDMO> convertFrom(Set<RoleEntity> roles) throws ImpensaException;

}
