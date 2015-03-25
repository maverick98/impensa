/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.login;

/**
 * This is the DAO to interact with User entity in impensa
 * @author manosahu
 */
public interface ILoginService {
    
    public boolean login(String userId ,String plainPassword) throws LoginException;
    
    public boolean isLoggedIn(String userId ) throws LoginException;
    
    //debatable... shall we have LogoutException too... this is crazy
    //for now live with one Exception
    public boolean logout(String userId ) throws LoginException;
   
    public String encrypt(String plainPassword) throws LoginException;
}