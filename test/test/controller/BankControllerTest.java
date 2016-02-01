package test.controller;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.AssertionFailedError;
import banksys.account.AbstractAccount;
import banksys.account.OrdinaryAccount;
import banksys.account.SavingsAccount;
import banksys.account.SpecialAccount;
import banksys.control.BankController;
import banksys.control.exception.BankTransactionException;
import banksys.persistence.AccountVector;
import banksys.persistence.IAccountRepository;
import banksys.persistence.SQLiteAccounts;
import banksys.persistence.exception.AccountCreationException;
import banksys.persistence.exception.AccountDeletionException;
import banksys.persistence.exception.AccountNotFoundException;

public class BankControllerTest {
	
	static BankController bank;
	static AbstractAccount acc;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		bank = new BankController(new SQLiteAccounts());
		acc = new OrdinaryAccount("1");
		bank.addAccount(acc);
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
			bank.doCredit("1", 10);
			double result = bank.getBalance("1");
			assertEquals(10.0, result, 0);
		} catch (BankTransactionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void doDebitTest() {
		try {
			bank.doDebit("1", 5);
			double result = bank.getBalance("1");
			assertEquals(5, result, 0);
		} catch (BankTransactionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getBalanceTest() {
		try {
			double result = bank.getBalance("1");
			assertEquals(0, result, 0);
		} catch (BankTransactionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void doTransferTest() {
		try {
			AbstractAccount acc2 = new OrdinaryAccount("2");
			bank.addAccount(acc2);
			bank.doCredit("1", 10);
			bank.doTransfer("1", "2", 10);
			double result = bank.getBalance("2");
			assertEquals(10, result, 0);
		} catch (BankTransactionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void doEarnBonusTest(){
		AbstractAccount acc2 = new SpecialAccount("3");
		try {
			bank.addAccount(acc2);
			bank.doCredit("3", 10);
			bank.doEarnBonus("3");
			double result = bank.getBalance("3");
			assertEquals(10.1, result, 0);
		} catch (BankTransactionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void doEarnInterestTest(){
		AbstractAccount acc2 = new SavingsAccount("4");
		try {
			bank.addAccount(acc2);
			bank.doCredit("4", 10);
			bank.doEarnInterest("4");
			double result = bank.getBalance("4");
			assertEquals(10.01, result, 0);
		} catch (BankTransactionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
