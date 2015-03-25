/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.dao.user;

import org.impensa.service.login.LoginException;
import java.util.Set;
import org.impensa.service.db.entity.UserEntity;

/**
 * This is the DAO to interact with User entity in impensa
 * @author manosahu
 */
public interface IUserDAO {
    
    public Set<UserDMO> findBy(UserSearchCriteria userSearchCriteria) throws UserDAOException;
    /**
     * This retrieves the user by the userId.
     * @param userId
     * @return
     * @throws UserDAOException 
     */
    public UserDMO findByUserId(final String userId) throws UserDAOException;

    /**
     * This creates user from the input userDMO
     * @param userDMO
     * @return created userDMO 
     * @throws UserDAOException 
     */
    public UserDMO createUser(final UserDMO userDMO) throws UserDAOException;

    /**
     * This updates the user entity.
     * This takes UserUpdateDMO as input.
     * It essentially works in org and role insert and delete id sets.
     * It also has userUpdateDMO which will be used to update various user's attribute.
     * However UserDAOException will be thrown if an attempt is made to update userId of the object.
     * TODO think about the above statement for its validity.
     * @param userUpdateDMO
     * @return updated userDMO
     * @throws UserDAOException 
     */
    public UserDMO updateUser(final UserUpdateDMO userUpdateDMO) throws UserDAOException;

    /**
     * This deletes the user identified by userDMO
     * @param userDMO
     * @return true on success , false otherwise
     * @throws UserDAOException 
     */
    public boolean deleteUser(final UserDMO userDMO) throws UserDAOException;

    /**
     * So client code make calls to this API for conversion.
     * Let's us stop her directly invoking DomainEntityConverter. That being static
     * , we would end up having lesser control.
     * @param userDMO
     * @return
     * @throws UserDAOException 
     */
    public UserEntity convertTo(final UserDMO userDMO) throws UserDAOException ;

    /**
     * reciprocal of convertTo method.
     * @param user
     * @return
     * @throws UserDAOException 
     */
    public UserDMO convertFrom(final UserEntity user) throws UserDAOException ;
    
    /**
     * 
     * @param users
     * @return
     * @throws UserDAOException 
     */
    public Set<UserDMO> convertFrom(Set<UserEntity> users) throws UserDAOException;
    
    
   
}
