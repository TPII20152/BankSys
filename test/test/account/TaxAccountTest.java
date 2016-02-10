package test.account;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import banksys.account.TaxAccount;
import banksys.account.exception.InsufficientFundsException;
import banksys.account.exception.NegativeAmountException;

public class TaxAccountTest {
	private TaxAccount acc;
	
	@Before
	public void setUp() throws Exception {
		acc = new TaxAccount("400");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test(expected=InsufficientFundsException.class)
	public void testDebitWithoutFunds() throws InsufficientFundsException {
		acc.setBalance(100);
		try {
			acc.debit(100);
		} catch (NegativeAmountException naex) {
			System.out.println("Should Not Enter This Catch");
		}
	}
	
	@Test
	public void testDebit() {
		acc.setBalance(200);
		try {
			acc.debit(100);
			assertEquals(99.9, acc.getBalance(), 0);
		} catch (Exception ex) {
			System.out.println("Should Not Enter This Catch");
		}
	}

}
