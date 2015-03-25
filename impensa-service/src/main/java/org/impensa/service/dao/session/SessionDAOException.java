/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.dao.session;

import org.impensa.service.dao.exception.DAOException;

/**
 *
 * @author manosahu
 */
public class SessionDAOException extends DAOException {

    private static final long serialVersionUID = 1L;

    public SessionDAOException() {
    }

    public SessionDAOException(Object msg) {
        super(msg.toString());
    }

    public SessionDAOException(Throwable t) {
        super(t);
    }

    public SessionDAOException(Object msg, Throwable t) {
        super(msg.toString(), t);
    }
}
