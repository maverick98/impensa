/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.dao.expensetemplate;

import org.impensa.db.entity.ExpenseTemplateEntity;
import org.impensa.exception.ImpensaException;

/**
 *
 * @author manosahu
 */
public interface IExpenseTemplateDAO {

    public ExpenseTemplate findByTenantId(String tenantId) throws ImpensaException;
   
    public ExpenseTemplate save(final ExpenseTemplate expenseTemplate) throws ImpensaException;    

    /**
     * So client code make calls to this API for conversion. Let's us stop her
     * directly invoking DomainEntityConverter. That being static , we would end
     * up having lesser control.
     *
     * @param expenseTemplate
     * @return
     * @throws ImpensaException
     */
    public ExpenseTemplateEntity convertTo(final ExpenseTemplate expenseTemplate) throws ImpensaException;

    /**
     * reciprocal of convertTo method.
     *
     * @param expenseTemplateEntity
     * @return
     * @throws ImpensaException
     */
    public ExpenseTemplate convertFrom(final ExpenseTemplateEntity expenseTemplateEntity) throws ImpensaException;
}
