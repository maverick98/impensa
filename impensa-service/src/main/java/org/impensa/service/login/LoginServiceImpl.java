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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.commons.logger.ILogger;
import org.commons.logger.LoggerFactory;
import org.commons.string.StringUtil;
import org.impensa.dao.session.ISessionDAO;
import org.impensa.dao.user.IUserDAO;
import org.impensa.dao.user.UserDMO;
import org.common.crypto.EncryptionUtil;
import org.impensa.dao.role.RoleDMO;
import org.impensa.dao.session.SessionDMO;
import org.impensa.dao.tenant.ITenantDAO;
import org.impensa.dao.tenant.TenantDMO;
import org.impensa.dao.user.UserUpdateDMO;
import org.impensa.db.TenantGraphDatabaseService;
import org.impensa.exception.ImpensaException;
import org.impensa.exception.ValidationErrorCode;
import org.impensa.service.role.IRoleService;
import org.impensa.txn.Txn;
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
    private ITenantDAO tenantDAOImpl;

    @Autowired
    private IUserDAO userDAOImpl;

    @Autowired
    private ISessionDAO sessionDAOImpl;

    @Autowired
    private IRoleService roleServiceImpl;

    public IRoleService getRoleServiceImpl() {
        return roleServiceImpl;
    }

    public void setRoleServiceImpl(IRoleService roleServiceImpl) {
        this.roleServiceImpl = roleServiceImpl;
    }

    public ITenantDAO getTenantDAOImpl() {
        return tenantDAOImpl;
    }

    public void setTenantDAOImpl(ITenantDAO tenantDAOImpl) {
        this.tenantDAOImpl = tenantDAOImpl;
    }

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

    public SessionDMO loginTenantFirstTime(String tenantId, String plainPassword) throws ImpensaException {
        this.preLoginCheck(tenantId, plainPassword, tenantId);
        SessionDMO sessionDMO = new SessionDMO();
        sessionDMO.setTenantId(tenantId);
        this.setCurrentSession(sessionDMO);
        return this.loginTenantFirstTimeInternal(tenantId, plainPassword);

    }

    @Txn
    public SessionDMO loginTenantFirstTimeInternal(String tenantId, String plainPassword) throws ImpensaException {
        RoleDMO tenantRoleDMO = this.getRoleServiceImpl().createTenantRole();
        UserDMO tenantUserDMO = new UserDMO();
        tenantUserDMO.setUserId(tenantId);
        try {
            tenantUserDMO.setEncryptedPassword(EncryptionUtil.encrypt(plainPassword));
        } catch (Exception ex) {
            Logger.getLogger(LoginServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.getUserDAOImpl().createUser(tenantUserDMO);
        UserUpdateDMO tenantUserUpdateDMO = new UserUpdateDMO();
        tenantUserUpdateDMO.setUserUpdate(tenantUserDMO);
        tenantUserUpdateDMO.getInsertRoleIdSet().add(tenantRoleDMO.getRoleId());
        this.getUserDAOImpl().updateUser(tenantUserUpdateDMO);
        return this.login(tenantId, plainPassword, tenantId);
    }

    private boolean preLoginCheck(String userId, String plainPassword, String tenantId) throws ImpensaException {
        if (StringUtil.isNullOrEmpty(userId)) {
            throw new ImpensaException(ValidationErrorCode.VALUE_NULL_OR_EMPTY).set("userId", "null or empty");
        }
        if (StringUtil.isNullOrEmpty(plainPassword)) {
            throw new ImpensaException(ValidationErrorCode.VALUE_NULL_OR_EMPTY).set("plainPassword", "null or empty");
        }
        if (StringUtil.isNullOrEmpty(tenantId)) {
            throw new ImpensaException(ValidationErrorCode.VALUE_NULL_OR_EMPTY).set("tenantId", "null or empty");
        }
        TenantDMO tenantDMO = this.getTenantDAOImpl().isTenantRegistered(tenantId);
        if (tenantDMO == null) {
            throw new ImpensaException(LoginErrorCode.TENANT_NOT_REGISTERED).set("tenantId", tenantId);
        }
        boolean tenantDatabaseCreated = this.getTenantDAOImpl().isTenantDatabaseCreated(tenantId);
        if (!tenantDatabaseCreated) {
            throw new ImpensaException(LoginErrorCode.TENANT_DB_NOT_CREATED).set("tenantId", tenantId);
        } else {
            //Register the tenantGraphDatabaseService with spring 
            TenantGraphDatabaseService tenantGraphDatabseService = this.getTenantDAOImpl().findTenantGraphDatabaseService(tenantId);
            if (tenantGraphDatabseService == null) {
                this.getTenantDAOImpl().registerTenantDatabaseService(tenantId);
            }
        }
        return true;
    }

    @Override
    @Txn
    public SessionDMO login(String userId, String plainPassword, String tenantId) throws ImpensaException {
        this.preLoginCheck(userId, plainPassword, tenantId);
        if (this.isLoggedIn(userId)) {
            return this.getCurrentSession();
        }

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
            sessionDMO.setUserDMO(userDMO);
            sessionDMO.setAttempts(1);
        }
        String encryptedPassword = this.encrypt(plainPassword);
        if (userDMO.getEncryptedPassword().equals(encryptedPassword)) {

            sessionDMO.setLoginTime(new Date());
            this.getSessionDAOImpl().persistSession(sessionDMO);
            this.setCurrentSession(sessionDMO);
        } else {

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
                this.getTenantDAOImpl().shutdownTenantDatabaseService(sessionDMO.getTenantId());
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
            if (sessionDMO.getUserDMO().getUserId().equals(userId)) {

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
