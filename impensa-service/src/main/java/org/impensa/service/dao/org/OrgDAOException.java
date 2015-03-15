/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.dao.org;

import org.impensa.service.dao.exception.DAOException;

/**
 *
 * @author manosahu
 */
public class OrgDAOException extends DAOException {

    private static final long serialVersionUID = 1L;

    public OrgDAOException() {
    }

    public OrgDAOException(Object msg) {
        super(msg.toString());
    }

    public OrgDAOException(Throwable t) {
        super(t);
    }

    public OrgDAOException(Object msg, Throwable t) {
        super(msg.toString(), t);
    }
}
