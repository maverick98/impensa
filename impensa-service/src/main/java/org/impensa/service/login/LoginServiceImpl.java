/*
 *  Copyleft(BigBang<-->BigCrunch)  Manoranjan Sahu
 *  
 */
package org.impensa.service.login;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.commons.logger.ILogger;
import org.commons.logger.LoggerFactory;
import org.commons.string.StringUtil;
import org.impensa.service.dao.session.ISessionDAO;
import org.impensa.service.dao.session.SessionDAOException;
import org.impensa.service.dao.session.SessionDMO;
import org.impensa.service.dao.user.IUserDAO;
import org.impensa.service.dao.user.UserDAOException;
import org.impensa.service.dao.user.UserDMO;
import org.impensa.service.util.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author manosahu
 */
@Service
public class LoginServiceImpl implements ILoginService {

    private static final ILogger logger = LoggerFactory.getLogger(LoginServiceImpl.class.getName());

    @Autowired
    private IUserDAO userDAOImpl;

    @Autowired
    private ISessionDAO sessionDAOImpl;

    public ISessionDAO getSessionDAOImpl() {
        return sessionDAOImpl;
    }

    public void setSessionDAOImpl(ISessionDAO sessionDAOImpl) {
        this.sessionDAOImpl = sessionDAOImpl;
    }

    
    public IUserDAO getUserDAOImpl() {
        return userDAOImpl;
    }

    public void setUserDAOImpl(IUserDAO userDAOImpl) {
        this.userDAOImpl = userDAOImpl;
    }

    @Override
    public boolean login(String userId, String plainPassword) throws LoginException {
        if (StringUtil.isNullOrEmpty(userId)) {
            throw new LoginException("null or empty userId");
        }
        if (StringUtil.isNullOrEmpty(plainPassword)) {
            throw new LoginException("null or empty password");
        }
        boolean loginSuccess = false;
        UserDMO userDMO;
        try {
            userDMO = this.getUserDAOImpl().findByUserId(userId);
            if (userDMO == null) {
                throw new LoginException("No userid {" + userId + "} exists in impensa.");
            }
            SessionDMO sessionDMO = this.getSessionDAOImpl().findByUserId(userId);
            if(sessionDMO != null){
                int attempts = sessionDMO.getAttempts();
                attempts = attempts +1;
                sessionDMO.setAttempts(attempts);
                this.getSessionDAOImpl().persistSession(sessionDMO);
                throw new LoginException("account with userId {"+userId+"} is locked... contact your system admin");
            }else{
                sessionDMO = new SessionDMO();
                sessionDMO.setUserId(userId);
                sessionDMO.setAttempts(1);
                
            }
            String encryptedPassword = this.encrypt(plainPassword);
            if (userDMO.getEncryptedPassword().equals(encryptedPassword)) {
                loginSuccess = true;
                sessionDMO.setLoginTime(new Date());
                this.getSessionDAOImpl().persistSession(sessionDMO);
            } else {
                loginSuccess = false;
            }

        } catch (UserDAOException ex) {
            logger.error("error while fetching user {" + userId + "}", ex);
            throw new LoginException("error while fetching user {" + userId + "}", ex);
        } catch (SessionDAOException ex) {
            logger.error("error while fetching session for  user {" + userId + "}", ex);
            throw new LoginException("error while fetching session for  user {" + userId + "}", ex);
        }

        return loginSuccess;
    }

    @Override
    public boolean logout(String userId) throws LoginException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String encrypt(String plainPassword) throws LoginException {
        try {
            return EncryptionUtil.encrypt(plainPassword);
        } catch (Exception ex) {
            throw new LoginException(ex);
        }
    }

    @Override
    public boolean isLoggedIn(String userId) throws LoginException {
        SessionDMO sessionDMO = null;
        try {
            sessionDMO = this.getSessionDAOImpl().findByUserId(userId);
        } catch (SessionDAOException ex) {
            logger.error("unable to fetch session details", ex);
        }
        
        return sessionDMO !=null ? sessionDMO.getLoginTime()!=null:false;
    }

}
