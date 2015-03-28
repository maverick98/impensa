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
public enum ValidationErrorCode implements ErrorCode {

    VALUE_NULL(1),
    VALUE_EMPTY(2),
    VALUE_NULL_OR_EMPTY(3),
    VALUE_TOO_LONG(4);

    private final int number;

    private ValidationErrorCode(int number) {
        this.number = number;
    }

    @Override
    public int getNumber() {
        return number;
    }

}
