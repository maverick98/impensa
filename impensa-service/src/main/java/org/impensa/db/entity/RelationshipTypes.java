/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.db.entity;

/**
 * @author: manosahu
 */
public final class RelationshipTypes {
   
    public static final String USER_ORG_ASSIGNED = "USER_ORG_ASSIGNED";
    public static final String USER_ROLE_ASSIGNED = "USER_ROLE_ASSIGNED";
    public static final String ORG_ROLE_ASSIGNED = "ORG_ROLE_ASSIGNED";
    public static final String ROLE_FUNCTION_ASSIGNED = "ROLE_FUNCTION_ASSIGNED";
    public static final String EXPENSE_HAS_CATEGORIES = "EXPENSE_HAS_CATEGORIES";
    public static final String CATEGORY_HAS_SUBCATEGORIES= "CATEGORY_HAS_SUBCATEGORIES";
    public static final String CATEGORY_HAS_TXNDATA= "CATEGORY_HAS_TXNDATA";
    public static final String TXNDATA_HAS_ATTRIBUTES= "TXNDATA_HAS_ATTRIBUTES";
    
}
