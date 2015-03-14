/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch . All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */

package org.impensa.service.db.entity.mapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author manosahu
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Property {

    String name();
}
