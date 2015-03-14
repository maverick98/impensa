/*
 *  Copyleft(BigBang<-->BigCrunch)  Manoranjan Sahu
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
