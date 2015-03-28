/**
 * This file is part of impensa. CopyLeft (C) BigBang<->BigCrunch.All Rights are
 * left.
 *
 * 1) Modify it if you can understand. 2) If you distribute a modified version,
 * you must do it at your own risk.
 *
 */
package org.impensa.dao;

import org.impensa.exception.ErrorCode;

/**
 *
 * @author msahu98
 */
public enum Neo4JErrorCode implements ErrorCode {

    TXN_NOT_CREATED(1000),
    GRAHDB_SERVICE_NOT_FOUND(1001);
    private final int number;

    private Neo4JErrorCode(int number) {
        this.number = number;
    }

    @Override
    public int getNumber() {
        return number;
    }

}
