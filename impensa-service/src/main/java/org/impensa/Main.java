package org.impensa;

import org.commons.xml.XMLUtil;
import org.impensa.dao.expensetemplate.Attribute;
import org.impensa.dao.expensetemplate.Category;
import org.impensa.dao.expensetemplate.ExpenseTemplate;
import org.impensa.dao.expensetemplate.ExpenseTemplateDAOImpl;
import org.impensa.dao.expensetemplate.IExpenseTemplateDAO;
import org.impensa.dao.expensetemplate.TxnData;
import org.impensa.dao.function.FunctionDAOImpl;
import org.impensa.dao.function.IFunctionDAO;
import org.impensa.startup.ImpensaStartup;
import org.neo4j.graphdb.GraphDatabaseService;

/**
 * @author: rkowalewski
 */
public class Main {

    
    public static void main(String[] args) throws Throwable {

        ImpensaStartup.startup();
        //Neo4jPersister neo4jPersister = (Neo4jPersister) context.getBean("neo4jPersister");
        //neo4jPersister.createTestData();
        GraphDatabaseService gdb  =AppContainer.getInstance().getBean(GraphDatabaseService.class);
        System.out.println("msahu gdb is "+gdb);
        IFunctionDAO functionDAO = (IFunctionDAO) AppContainer.getInstance().getBean("functionDAOImpl");
        functionDAO.cacheFunctions();
        
       ExpenseTemplate txnExpenseTemplate = new ExpenseTemplate();
        txnExpenseTemplate.setTenantId("tenant1");
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

        boolean xmlCreated = XMLUtil.marshal(txnExpenseTemplate, "Txn.xml");
     //   AssertJUnit.assertEquals(xmlCreated, true);
        ExpenseTemplate result = XMLUtil.unmarshal("Txn.xml", ExpenseTemplate.class);
        System.out.println(result.getCategory().get(0).getCategory().get(1).getDescription());
     //   AssertJUnit.assertNotNull(result);
        
        getExepnseTemplateDAO().save(txnExpenseTemplate);
        
        ExpenseTemplate et = getExepnseTemplateDAO().findByTenantId("tenant1");
     //   AssertJUnit.assertNotNull(et);
         System.out.println(et.getName());
        
        

    }
    
       public static  IExpenseTemplateDAO getExepnseTemplateDAO() {
        return AppContainer.getInstance().getBean(ExpenseTemplateDAOImpl.class);
        //return (IFunctionDAO) context.getBean("functionDAOImpl");

    }
}
