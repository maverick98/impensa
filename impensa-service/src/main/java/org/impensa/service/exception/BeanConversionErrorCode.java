/**
 * This file is part of impensa. CopyLeft (C) BigBang<->BigCrunch.All Rights are
 * left.
 *
 * 1) Modify it if you can understand. 2) If you distribute a modified version,
 * you must do it at your own risk.
 *
 */
package org.impensa.service.exception;

/**
 *
 * @author msahu98
 */
public enum BeanConversionErrorCode implements ErrorCode {

    TO_MAPPING_BEAN(5),
    FROM_MAPPING_BEAN(6);

    private final int number;

    private BeanConversionErrorCode(int number) {
        this.number = number;
    }

    @Override
    public int getNumber() {
        return number;
    }

}
