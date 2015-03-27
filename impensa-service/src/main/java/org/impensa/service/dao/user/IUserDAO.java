/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.dao.user;

import java.util.Set;
import org.impensa.service.db.entity.UserEntity;
import org.impensa.service.exception.ImpensaException;

/**
 * This is the DAO to interact with User entity in impensa
 * @author manosahu
 */
public interface IUserDAO {
    
    public Set<UserDMO> findBy(UserSearchCriteria userSearchCriteria) throws ImpensaException;
    /**
     * This retrieves the user by the userId.
     * @param userId
     * @return
     * @throws ImpensaException 
     */
    public UserDMO findByUserId(final String userId) throws ImpensaException;

    /**
     * This creates user from the input userDMO
     * @param userDMO
     * @return created userDMO 
     * @throws ImpensaException 
     */
    public UserDMO createUser(final UserDMO userDMO) throws ImpensaException;

    /**
     * This updates the user entity.
     * This takes UserUpdateDMO as input.
     * It essentially works in org and role insert and delete id sets.
     * It also has userUpdateDMO which will be used to update various user's attribute.
     * However ImpensaException will be thrown if an attempt is made to update userId of the object.
     * TODO think about the above statement for its validity.
     * @param userUpdateDMO
     * @return updated userDMO
     * @throws ImpensaException 
     */
    public UserDMO updateUser(final UserUpdateDMO userUpdateDMO) throws ImpensaException;

    /**
     * This deletes the user identified by userDMO
     * @param userDMO
     * @return true on success , false otherwise
     * @throws ImpensaException 
     */
    public boolean deleteUser(final UserDMO userDMO) throws ImpensaException;

    /**
     * So client code make calls to this API for conversion.
     * Let's us stop her directly invoking DomainEntityConverter. That being static
     * , we would end up having lesser control.
     * @param userDMO
     * @return
     * @throws ImpensaException 
     */
    public UserEntity convertTo(final UserDMO userDMO) throws ImpensaException ;

    /**
     * reciprocal of convertTo method.
     * @param user
     * @return
     * @throws ImpensaException 
     */
    public UserDMO convertFrom(final UserEntity user) throws ImpensaException ;
    
    /**
     * 
     * @param users
     * @return
     * @throws ImpensaException 
     */
    public Set<UserDMO> convertFrom(Set<UserEntity> users) throws ImpensaException;
    
    
   
}
