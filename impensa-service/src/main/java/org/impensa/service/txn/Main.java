/**
 * This file is part of impensa. CopyLeft (C) BigBang<->BigCrunch.All Rights are
 * left.
 *
 * 1) Modify it if you can understand. 2) If you distribute a modified version,
 * you must do it at your own risk.
 *
 */
package org.impensa.service.txn;

import org.commons.xml.XMLUtil;

/**
 *
 * @author msahu98
 */
public class Main {

    public static void main(String args[]) {
        ExpenseTemplate txnExpenseTemplate = new ExpenseTemplate();
        txnExpenseTemplate.setName("My Transaction expense template");
        txnExpenseTemplate.setDescription("This template contains my expenditure template. This is likely to evolve over time.");
        Category loanCategory = new Category();
        loanCategory.setName("Loan");
        loanCategory.setDescription("This keeps track of the loan I have at the moment");
                
        Category houseLoanCategory = new Category();
        houseLoanCategory.setName("House Loan");
        houseLoanCategory.setDescription("This is my house loan details");
        
        TxnData txnNode1 = new TxnData();
        txnNode1.setName("my first loan");
        
        Attribute attribute1 = new Attribute();
        attribute1.setKey("Name");
        attribute1.setValue("Steve Waugh");
        txnNode1.getAttributes().add(attribute1);
        
        Attribute attribute11 = new Attribute();
        attribute11.setKey("Loan Amount");
        attribute11.setValue("75,00,000");
        txnNode1.getAttributes().add(attribute11);
        
        Attribute attribute111 = new Attribute();
        attribute111.setKey("Loan Tenure");
        attribute111.setValue("20 years");
        txnNode1.getAttributes().add(attribute111);
        
        Attribute attribute1111 = new Attribute();
        attribute1111.setKey("Bank");
        attribute1111.setValue("HDFC Bank ltd.");
        txnNode1.getAttributes().add(attribute1111);
        
        houseLoanCategory.setTxnData(txnNode1);
        loanCategory.getCategory().add(houseLoanCategory);

        Category carLoanCategory = new Category();
        carLoanCategory.setName("Car Loan");
        carLoanCategory.setDescription("This is my car loan details");
                
        loanCategory.getCategory().add(carLoanCategory);

        Category personalLoanCategory = new Category();
        personalLoanCategory.setName("Personal Loan");
        personalLoanCategory.setDescription("This is my personal details.");
        loanCategory.getCategory().add(personalLoanCategory);

        
        Category healthCategory = new Category();
        healthCategory.setName("health");
        healthCategory.setDescription("This is to keep track money spent on health");
        
        Category beautyCategory = new Category();
        beautyCategory.setName("Beauty");
        beautyCategory.setDescription("money spent on beauty");
        
        healthCategory.getCategory().add(beautyCategory);
        
        Category medicineCategory = new Category();
        medicineCategory.setName("Medicine");
        medicineCategory.setDescription("money spent on medicines");
        healthCategory.getCategory().add(medicineCategory);
        
        txnExpenseTemplate.getCategory().add(loanCategory);
        txnExpenseTemplate.getCategory().add(healthCategory);
        
        
        XMLUtil.marshal(txnExpenseTemplate, "Txn.xml");
        ExpenseTemplate result = XMLUtil.unmarshal("Txn.xml", ExpenseTemplate.class);
        System.out.println(result.getCategory().get(0).getCategory().get(1).getDescription());
    }

}
