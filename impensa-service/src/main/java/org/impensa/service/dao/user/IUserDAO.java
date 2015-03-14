/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch Manoranjan Sahu. All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.dao.user;

/**
 * This is the DAO to interact with User entity in impensa
 * @author manosahu
 */
public interface IUserDAO {

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
     * This is same as deleteUser which takes userDMO.
     * The only difference the user is identified by the input userId
     * @param userId
     * @return
     * @throws UserDAOException 
     */
    public boolean deleteUser(final String userId) throws UserDAOException;

}
