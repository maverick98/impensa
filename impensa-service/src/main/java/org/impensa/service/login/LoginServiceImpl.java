/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.login;

import java.util.Date;
import org.commons.logger.ILogger;
import org.commons.logger.LoggerFactory;
import org.commons.string.StringUtil;
import org.impensa.service.dao.session.ISessionDAO;
import org.impensa.service.dao.session.SessionDMO;
import org.impensa.service.dao.user.IUserDAO;
import org.impensa.service.dao.user.UserDMO;
import org.common.crypto.EncryptionUtil;
import org.impensa.service.exception.BeanConversionErrorCode;
import org.impensa.service.exception.ImpensaException;
import org.impensa.service.exception.ValidationErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author manosahu
 */
@Service
public class LoginServiceImpl implements ILoginService {

    private static final ILogger logger = LoggerFactory.getLogger(LoginServiceImpl.class.getName());

    private static final SessionLocal<SessionDMO> sessionLocal = new SessionLocal<SessionDMO>();

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
    public SessionDMO login(String userId, String plainPassword) throws ImpensaException {
        if (StringUtil.isNullOrEmpty(userId)) {
            throw new ImpensaException(ValidationErrorCode.VALUE_NULL_OR_EMPTY).set("userId", "null or empty");
        }
        if (StringUtil.isNullOrEmpty(plainPassword)) {
            throw new ImpensaException(ValidationErrorCode.VALUE_NULL_OR_EMPTY).set("plainPassword", "null or empty");
        }
        if (this.isLoggedIn(userId)) {
            return this.getCurrentSession();
        }
        boolean loginSuccess = false;
        UserDMO userDMO;
        SessionDMO sessionDMO;
        userDMO = this.getUserDAOImpl().findByUserId(userId);
        if (userDMO == null) {
            throw new ImpensaException(ValidationErrorCode.VALUE_NULL).set("userDMO", "null");
        }
        sessionDMO = this.getSessionDAOImpl().findByUserId(userId);
        if (sessionDMO != null) {
            if (sessionDMO.getLocked()) {
                throw new ImpensaException(LoginErrorCode.ACCOUNT_LOCKED).set("userId", userId);
            }
            int attempts = sessionDMO.getAttempts();
            attempts = attempts + 1;
            sessionDMO.setAttempts(attempts);
            this.getSessionDAOImpl().persistSession(sessionDMO);
            if (attempts > 4) {
                sessionDMO.setLocked(true);
                this.getSessionDAOImpl().persistSession(sessionDMO);
                throw new ImpensaException(LoginErrorCode.ACCOUNT_LOCKED).set("userId", userId);
            }
        } else {
            sessionDMO = new SessionDMO();
            sessionDMO.setUserId(userId);
            sessionDMO.setAttempts(1);

        }
        String encryptedPassword = this.encrypt(plainPassword);
        if (userDMO.getEncryptedPassword().equals(encryptedPassword)) {
            loginSuccess = true;
            sessionDMO.setLoginTime(new Date());
            this.getSessionDAOImpl().persistSession(sessionDMO);
            this.setCurrentSession(sessionDMO);
        } else {
            loginSuccess = false;
            this.getSessionDAOImpl().persistSession(sessionDMO);
        }

        return sessionDMO;
    }

    @Override
    public boolean logout(String userId) throws ImpensaException {
        boolean loggedOut;
        if (this.isLoggedIn(userId)) {
            SessionDMO sessionDMO;
            sessionDMO = this.getSessionDAOImpl().findByUserId(userId);
            if (sessionDMO != null) {
                sessionDMO.setLogoutTime(new Date());
                this.getSessionDAOImpl().persistSession(sessionDMO);
                this.setCurrentSession(null);
            }

            loggedOut = true;
        } else {
            loggedOut = false;
        }

        return loggedOut;
    }

    @Override
    public String encrypt(String plainPassword) throws ImpensaException {
        try {
            return EncryptionUtil.encrypt(plainPassword);
        } catch (Exception ex) {
            throw ImpensaException.wrap(ex)
                    .setErrorCode(LoginErrorCode.ENCRYPTION_ERROR);
                    
        }
    }

    @Override
    public boolean isLoggedIn(String userId) throws ImpensaException {
        SessionDMO sessionDMO;
        sessionDMO = this.getCurrentSession();
        boolean loggedIn = false;
        if (sessionDMO != null) {
            if (sessionDMO.getUserId().equals(userId)) {

                loggedIn = sessionDMO.isLoggedIn();
            }
        } else {
            loggedIn = false;
        }
        return loggedIn;
    }

    @Override
    public SessionDMO getCurrentSession() {
        return sessionLocal.get();
    }

    public void setCurrentSession(SessionDMO sessionDMO) {
        sessionLocal.set(sessionDMO);
    }

}
