package test.controller;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import banksys.account.AbstractAccount;
import banksys.account.OrdinaryAccount;
import banksys.account.SavingsAccount;
import banksys.account.SpecialAccount;
import banksys.account.TaxAccount;
import banksys.control.BankController;
import banksys.control.exception.BankTransactionException;
import banksys.persistence.SQLiteAccounts;

public class BankControllerTest {
	
	static BankController bank;
	static AbstractAccount acc, ordinaryAccount, savingsAccount, taxAccount, specialAccount;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		bank = new BankController(new SQLiteAccounts());
		ordinaryAccount = new OrdinaryAccount("101");
		specialAccount = new SpecialAccount("102");
		savingsAccount = new SavingsAccount("103");
		taxAccount = new TaxAccount("104");
	}

	@AfterClass
	public static void tearDownAfterClass(){
		try{
			bank.removeAccount(ordinaryAccount.getNumber());
			bank.removeAccount(specialAccount.getNumber());
			bank.removeAccount(savingsAccount.getNumber());
			bank.removeAccount(taxAccount.getNumber());
		} catch(Exception e){
			
		}
	}
	/*@Test
	public void addAcountTest() {
		
		fail("falta implementar");
	}
	
	@Test
	public void removeAcountTest() {
		fail("falta implementar");
		
	}*/
	
	@Test
	public void doCreditTest() {
		try {
			String number = ordinaryAccount.getNumber();
			bank.doCredit(number, 10);
			double result = bank.getBalance(number);
			assertEquals(10.0, result, 0);
		} catch (BankTransactionException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void doDebitTest() {
		try {
			String number = ordinaryAccount.getNumber();
			bank.doDebit(number, 5);
			double result = bank.getBalance(number);
			assertEquals(5, result, 0);
		} catch (BankTransactionException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getBalanceTest() {
		try {
			double result = bank.getBalance(savingsAccount.getNumber());
			assertEquals(0, result, 0);
		} catch (BankTransactionException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void doTransferTest() {
		try {
			String numberFrom = ordinaryAccount.getNumber();
			String numberTo = taxAccount.getNumber();
			bank.doCredit(numberFrom, 10);
			bank.doTransfer(numberFrom, numberTo, 10);
			double result = bank.getBalance(numberTo);
			assertEquals(10, result, 0);
		} catch (BankTransactionException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void doEarnBonusTest(){
		String number = specialAccount.getNumber();
		try {
			bank.doCredit(number, 10);
			bank.doEarnBonus(number);
			double result = bank.getBalance(number);
			assertEquals(10.1, result, 0);
		} catch (BankTransactionException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void doEarnInterestTest(){
		String number = savingsAccount.getNumber();
		try {
			bank.doCredit(number, 10);
			bank.doEarnInterest(number);
			double result = bank.getBalance(number);
			assertEquals(10.01, result, 0);
		} catch (BankTransactionException e) {
			e.printStackTrace();
		}
		
	}
}
