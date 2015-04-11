/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.dao.expensetemplate;

import org.common.bean.BeanConverter;
import org.common.di.AppContainer;
import org.commons.logger.ILogger;
import org.commons.logger.LoggerFactory;

import org.impensa.db.entity.ExpenseTemplateEntity;
import org.impensa.db.repository.ExpenseTemplateRepository;
import org.impensa.exception.BeanConversionErrorCode;
import org.impensa.exception.ImpensaException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author manosahu
 */
//@Component
public class ExpenseTemplateDAOImpl implements IExpenseTemplateDAO {

    private static final ILogger logger = LoggerFactory.getLogger(ExpenseTemplateDAOImpl.class.getName());

    @Autowired
    private ExpenseTemplateRepository expenseTemplateRepository;

    public ExpenseTemplateRepository getExpenseTemplateRepository() {
        //return AppContainer.getInstance().getBean("expenseTemplateRepository",ExpenseTemplateRepository.class);
        return expenseTemplateRepository;
    }

    public void setExpenseTemplateRepository(ExpenseTemplateRepository expenseTemplateRepository) {
        this.expenseTemplateRepository = expenseTemplateRepository;
    }

    @Override
    public ExpenseTemplateEntity convertTo(final ExpenseTemplate expenseTemplate) throws ImpensaException {
        if (expenseTemplate == null) {
            return null;
        }
        ExpenseTemplateEntity expenseTemplateEntity;
        try {
            expenseTemplateEntity = BeanConverter.toMappingBean(expenseTemplate, ExpenseTemplateEntity.class);
        } catch (Exception ex) {

            throw ImpensaException.wrap(ex)
                    .setErrorCode(BeanConversionErrorCode.TO_MAPPING_BEAN)
                    .set("expenseTemplate", expenseTemplate);
        }
        return expenseTemplateEntity;
    }

    @Override
    public ExpenseTemplate convertFrom(final ExpenseTemplateEntity expenseTemplateEntity) throws ImpensaException {
        if (expenseTemplateEntity == null) {
            return null;
        }
        ExpenseTemplate expenseTemplate;
        try {
            expenseTemplate = BeanConverter.fromMappingBean(expenseTemplateEntity, ExpenseTemplate.class);
        } catch (Exception ex) {
            throw ImpensaException.wrap(ex)
                    .setErrorCode(BeanConversionErrorCode.FROM_MAPPING_BEAN)
                    .set("expenseTemplateEntity", expenseTemplateEntity);

        }
        return expenseTemplate;
    }

    @Override
    public ExpenseTemplate save(ExpenseTemplate expenseTemplate) throws ImpensaException {
        this.getExpenseTemplateRepository().save(this.convertTo(expenseTemplate));
        return expenseTemplate;
    }

    @Override
    public ExpenseTemplate findByTenantId(String tenantId) throws ImpensaException {
        ExpenseTemplateEntity expenseTemplateEntity = this.getExpenseTemplateRepository().findByTenantId(tenantId);
        ExpenseTemplate et = this.convertFrom(expenseTemplateEntity);
        return et;
    }

}
