package test.account;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import banksys.account.AbstractAccount;
import banksys.account.OrdinaryAccount;
import banksys.account.exception.InsufficientFundsException;
import banksys.account.exception.NegativeAmountException;

public class OrdinaryAccountTest {
	private AbstractAccount acc;
	
	@Before
	public void setUp() throws Exception {
		fail("");
		//acc = new OrdinaryAccount("111");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetBalance() {
		assertEquals(0, acc.getBalance(), 0);
	}
	
	@Test
	public void testSetBalance() {
		assertEquals(0, acc.getBalance(), 0);
		acc.setBalance(100);
		assertEquals(100, acc.getBalance(), 0);
	}
	
	@Test
	public void testCredit() {
		acc.setBalance(0);
		try {
			assertEquals(0, acc.getBalance(), 0);
			acc.credit(30.0);
			assertEquals(30, acc.getBalance(), 0);
			acc.credit(-10);
		} catch(NegativeAmountException ex) {
			//System.out.println("Negative Exception, should not had debited. Balance: " + acc.getBalance());
			assertEquals(30, acc.getBalance(), 0);
		}
	}
	
	@Test(expected=InsufficientFundsException.class)
	public void testDebitWithoutBalance() throws InsufficientFundsException {
		acc.setBalance(0);
		try {
			acc.debit(10.0);
		} catch (NegativeAmountException ex) {
			ex.printStackTrace();
		}
	}
	
	@Test
	public void testDebit() {
		acc.setBalance(30);
		try {
			assertEquals(30, acc.getBalance(), 0);
			acc.debit(10);
			assertEquals(20, acc.getBalance(), 0);
			
		} catch(InsufficientFundsException ifex) {
			System.out.println("Should not enter this catch");
		} catch(NegativeAmountException naex) {
			System.out.println("Should not enter this catch");
		}
	}
	
	@Test (expected=NegativeAmountException.class)
	public void testNegativeDebit() throws NegativeAmountException {
		acc.setBalance(30);
		try {
			assertEquals(30, acc.getBalance(), 0);
			acc.debit(-10);
		} catch(InsufficientFundsException ifex) {
			System.out.println("Should not enter this catch");
		} 
	}
	
	@Test
	public void testGetNumber() {
		assertEquals("111", acc.getNumber());
	}

}
