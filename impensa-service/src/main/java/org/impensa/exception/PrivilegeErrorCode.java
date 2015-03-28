/**
 * This file is part of impensa. CopyLeft (C) BigBang<->BigCrunch.All Rights are
 * left.
 *
 * 1) Modify it if you can understand. 2) If you distribute a modified version,
 * you must do it at your own risk.
 *
 */
package org.impensa.exception;

/**
 *
 * @author msahu98
 */
public enum PrivilegeErrorCode implements ErrorCode {

    INSUFFICIENT_PRIVILEGE(7000);

    private final int number;

    private PrivilegeErrorCode(int number) {
        this.number = number;
    }

    @Override
    public int getNumber() {
        return number;
    }

}
