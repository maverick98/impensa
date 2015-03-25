/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.db.entity;

import java.util.Date;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.support.index.IndexType;

/**
 *
 * @author manosahu
 */
@NodeEntity
public class SessionEntity extends IdentifiableEntity {

    @Indexed(unique = true,indexType = IndexType.FULLTEXT, indexName = "userId")
    private String userId;
    @Indexed(indexType = IndexType.FULLTEXT, indexName = "attempts")
    private int attempts;
    @Indexed(indexType = IndexType.FULLTEXT, indexName = "loginTime")
    private Date loginTime;
    @Indexed(indexType = IndexType.FULLTEXT, indexName = "logoutTime")
    private Date logoutTime;
    @Indexed(indexType = IndexType.FULLTEXT, indexName = "locked")
    private Boolean locked = false;

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

   

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

}
