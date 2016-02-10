package test.account;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import banksys.account.SpecialAccount;
import banksys.account.exception.NegativeAmountException;

public class SpecialAccountTest {
	private SpecialAccount acc;
	
	@Before
	public void setUp() throws Exception {
		acc = new SpecialAccount("200");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetBonus() {
		acc.setBalance(100);
		assertEquals(0, acc.getBonus(), 0);
	}
	
	@Test
	public void testSetBonus() {
		acc.setBalance(100);
		acc.setBonus(20);
		assertEquals(20, acc.getBonus(), 0);
		assertEquals(100, acc.getBalance(), 0);
	}
	
	@Test
	public void testEarnBonus() {
		acc.setBalance(100);
		acc.setBonus(20);
		acc.earnBonus();
		assertEquals(120, acc.getBalance(), 0);
	}
	
	@Test
	public void testCredit() {
		acc.setBalance(0);
		acc.setBonus(0);
		try {
			acc.credit(100);
			assertEquals(100, acc.getBalance(), 0);
			assertEquals(1, acc.getBonus(), 0);
		} catch(NegativeAmountException naex) {
			System.out.println("Should Not Enter This Catch");
		}
		
	}

}
