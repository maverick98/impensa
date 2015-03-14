/*
 *  Copyleft(BigBang<-->BigCrunch)  Manoranjan Sahu
 *  
 */
package org.impensa.service.dao.exception;

/**
 *
 * @author manosahu
 */
public class DAOException extends Exception {

    private static final long serialVersionUID = 1L;

    public DAOException() {
    }

    public DAOException(Object msg) {
        super(msg.toString());
    }

    public DAOException(Throwable t) {
        super(t);
    }

    public DAOException(Object msg, Throwable t) {
        super(msg.toString(), t);
    }

}
