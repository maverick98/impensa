/**
 * This file is part of impensa. CopyLeft (C) BigBang<->BigCrunch.All Rights are
 * left.
 *
 * 1) Modify it if you can understand. 2) If you distribute a modified version,
 * you must do it at your own risk.
 *
 */
package org.impensa.service.login;

import org.impensa.exception.*;

/**
ErrorCode*
 * @author msahu98
 */
public enum LoginErrorCode implements ErrorCode {

    USER_ID_NOT_EXIST(3000),
    USER_NOT_LOGGED_IN(3500),
    ACCOUNT_LOCKED(4000),
    ENCRYPTION_ERROR(5000),
    TENANT_NOT_REGISTERED(6000),
    TENANT_DB_NOT_CREATED(7000);

    private final int number;

    private LoginErrorCode(int number) {
        this.number = number;
    }

    @Override
    public int getNumber() {
        return number;
    }

}
