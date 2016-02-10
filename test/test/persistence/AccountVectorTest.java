package test.persistence;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import banksys.account.AbstractAccount;
import banksys.account.OrdinaryAccount;
import banksys.account.SpecialAccount;
import banksys.account.exception.InsufficientFundsException;
import banksys.account.exception.NegativeAmountException;
import banksys.persistence.AccountVector;
import banksys.persistence.exception.AccountCreationException;
import banksys.persistence.exception.AccountDeletionException;
import banksys.persistence.exception.AccountNotFoundException;

public class AccountVectorTest {
	
	private AccountVector accVec;
	
	@Before
	public void setUp() throws Exception {
		accVec = new AccountVector();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test (expected=AccountDeletionException.class)
	public void testDeleteWithoutAccount() throws AccountDeletionException {
		accVec.delete("3");
	}
	
	@Test
	public void testDelete() {
		try {
			accVec.create(new OrdinaryAccount("200"));
			assertEquals(1, accVec.numberOfAccounts(), 0);
			accVec.delete("200");
			assertEquals(0, accVec.numberOfAccounts(), 0);
			
		} catch (AccountCreationException acex) {
			System.out.println("Should not enter this catch block");
		} catch (AccountDeletionException adex) {
			System.out.println("Should not enter this catch block either");
		}
		
	}

	@Test
	public void testCreate() {
		try {
			accVec.create(new OrdinaryAccount("200"));
			assertEquals(1, accVec.numberOfAccounts(), 0);
		} catch (AccountCreationException ace) {
			System.out.println("Should not enter this catch block");
		}
	}
	
	@Test (expected=AccountCreationException.class)
	public void testCreateExistingAccount() throws AccountCreationException {
		accVec.create(new OrdinaryAccount("200"));
		accVec.create(new OrdinaryAccount("200"));
		
		try {
			accVec.delete("200");
		} catch (AccountDeletionException e) {
			System.out.println("Should not enter this catch block once we make sure the account is created");
		}
	}
	

	@Test
	public void testList() {
		AccountVector accVec = new AccountVector();
		assertArrayEquals(null, accVec.list());
		
		OrdinaryAccount acc1 = new OrdinaryAccount("100");
		OrdinaryAccount acc2 = new OrdinaryAccount("400");
		
		try {
			accVec.create(acc1);
			accVec.create(acc2);
			
			assertArrayEquals(new AbstractAccount[]{acc1, acc2}, accVec.list());
			
		} catch (AccountCreationException e) {
			System.out.println("Should not enter this catch block");
		}
		
	}

	@Test
	public void testNumberOfAccounts() {
		try {
			accVec.delete("200");
		} catch (AccountDeletionException ade) {
			
		} finally {
			assertEquals(0, accVec.numberOfAccounts(), 0);
		}
		
		try {
			accVec.create(new OrdinaryAccount("300"));
			assertEquals(1, accVec.numberOfAccounts(), 0);
			
		} catch (AccountCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testRetrieve() {
		OrdinaryAccount acc1 = new OrdinaryAccount("700");
		try {
			accVec.create(acc1);
			assertEquals(acc1, accVec.retrieve("700"));
		} catch (AccountCreationException e) {
			System.out.println("Should not enter this catch block");
		} catch (AccountNotFoundException e) {
			System.out.println("Should not enter this catch block either");
		}
	}
	
	@Test (expected=AccountNotFoundException.class)
	public void testRetrieveWithoutAccount() throws AccountNotFoundException {
		OrdinaryAccount acc1 = new OrdinaryAccount("700");
		assertEquals(acc1, accVec.retrieve("900"));
		
	}

	@Test
	public void testUpdate() {
		accVec = new AccountVector();
		
		try {
			OrdinaryAccount acc1 = new OrdinaryAccount("670");
			accVec.create(acc1);
			acc1.credit(200);
			accVec.update(acc1);
			assertEquals(acc1.getBalance(), accVec.retrieve("670").getBalance(), 0);
		} catch (AccountCreationException e) {
			System.out.println("Should not enter this catch block");
		} catch (NegativeAmountException e) {
			System.out.println("nor this");
		} catch (AccountNotFoundException e) {
			System.out.println("or this");
		}
	}
	
	@Test (expected=AccountNotFoundException.class)
	public void testUpdateNonExistingAccount() throws AccountNotFoundException {
		accVec = new AccountVector();
		accVec.update(new SpecialAccount("50"));
	}

}
