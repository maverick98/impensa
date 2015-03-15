/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.dao.user;

import org.impensa.service.dao.exception.DAOException;

/**
 *
 * @author manosahu
 */
public class UserDAOException extends DAOException {

    private static final long serialVersionUID = 1L;

    public UserDAOException() {
    }

    public UserDAOException(Object msg) {
        super(msg.toString());
    }

    public UserDAOException(Throwable t) {
        super(t);
    }

    public UserDAOException(Object msg, Throwable t) {
        super(msg.toString(), t);
    }
}
