package test.account;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import banksys.account.SavingsAccount;

public class SavingsAccountTest {
	SavingsAccount acc;
	
	@Before
	public void setUp() throws Exception {
		acc = new SavingsAccount("300");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testEarnInterest() {
		acc.setBalance(100);
		acc.earnInterest();
		assertEquals(100.1,acc.getBalance(), 0);
	}

}
