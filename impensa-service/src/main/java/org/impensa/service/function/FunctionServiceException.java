/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.function;

import org.impensa.service.dao.exception.DAOException;

/**
 *
 * @author manosahu
 */
public class FunctionServiceException  extends DAOException {

    private static final long serialVersionUID = 1L;

    public FunctionServiceException() {
    }

    public FunctionServiceException(Object msg) {
        super(msg.toString());
    }

    public FunctionServiceException(Throwable t) {
        super(t);
    }

    public FunctionServiceException(Object msg, Throwable t) {
        super(msg.toString(), t);
    }

}
