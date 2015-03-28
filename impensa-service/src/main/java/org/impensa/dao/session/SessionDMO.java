/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.dao.session;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import org.common.bean.MappingBean;
import org.impensa.db.entity.SessionEntity;

/**
 *
 * @author manosahu
 */
@XmlRootElement
@MappingBean(name = SessionEntity.class)
public class SessionDMO {

    private String userId;

    private int attempts;

    private Date loginTime;

    private Date logoutTime;

    private Boolean locked = false;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Date logoutTime) {
        this.logoutTime = logoutTime;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public boolean isLoggedIn() {
        boolean loggedIn;
        if (this.getLoginTime() != null && this.getLogoutTime() == null) {
            loggedIn = true;
        } else {
            loggedIn = false;
        }
        return loggedIn;
    }

}
