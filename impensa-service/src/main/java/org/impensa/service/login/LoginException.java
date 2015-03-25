/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.login;

import org.impensa.service.dao.exception.DAOException;

/**
 *
 * @author manosahu
 */
public class LoginException extends DAOException {

    private static final long serialVersionUID = 1L;

    public LoginException() {
    }

    public LoginException(Object msg) {
        super(msg.toString());
    }

    public LoginException(Throwable t) {
        super(t);
    }

    public LoginException(Object msg, Throwable t) {
        super(msg.toString(), t);
    }
}
