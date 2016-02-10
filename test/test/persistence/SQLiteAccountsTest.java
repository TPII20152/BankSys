package test.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.Connection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import banksys.account.AbstractAccount;
import banksys.account.OrdinaryAccount;
import banksys.account.SpecialAccount;
import banksys.account.exception.NegativeAmountException;
import banksys.persistence.AccountVector;
import banksys.persistence.SQLiteAccounts;
import banksys.persistence.SQLiteConnector;
import banksys.persistence.exception.AccountCreationException;
import banksys.persistence.exception.AccountDeletionException;
import banksys.persistence.exception.AccountNotFoundException;

public class SQLiteAccountsTest {

	SQLiteAccounts database;
	Connection rawDbConnection;

	@Before
	public void setUp() throws Exception {
		database = new SQLiteAccounts();
		database.createTables();
		rawDbConnection = SQLiteConnector.getConnection();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreate() {
		//We have to make sure account 100 si not in the database
		try {
			database.delete("100");
		} catch (Exception e) {

		}

		try {
			int previousNumber = database.numberOfAccounts();
			OrdinaryAccount acc = new OrdinaryAccount("100");
			database.create(acc);
			assertEquals(previousNumber+1, database.numberOfAccounts(), 0);
		} catch (AccountCreationException e) {
			System.out.println("should not enter this catch block");
		}	
	}

	@Test(expected=AccountCreationException.class)
	public void testCreateExistingAccount() throws AccountCreationException {
		database.create(new OrdinaryAccount("200"));
		database.create(new OrdinaryAccount("200"));

		try {
			database.delete("200");
		} catch (Exception e) {
			System.out.println("Should not enter this catch block once we make sure the account is created");
		}
	}

	@Test
	public void testRetrieve() {
		OrdinaryAccount acc = new OrdinaryAccount("150");

		try {
			database.delete("150");
		} catch (Exception e) {

		}

		try {
			database.create(acc);
			assertEquals(acc.getBalance(), database.retrieve("150").getBalance(),0);
			assertEquals(acc.getType(), database.retrieve("150").getType());
			assertEquals(acc.getNumber(), database.retrieve("150").getNumber());
		} catch (AccountCreationException e) {

		} catch (AccountNotFoundException e) {

		}

	}

	@Test(expected=AccountNotFoundException.class)
	public void testRetrieveNonExistingAccount() throws AccountNotFoundException {
		AbstractAccount acc = database.retrieve("800");
	}

	@Test
	public void testInsertAndRetrieveBonus() {
		SpecialAccount acc = new SpecialAccount("714");
		acc.setBonus(20);
		database.insertBonus(acc, rawDbConnection);
		assertEquals(acc.getBonus(), database.retrieveBonus("714", rawDbConnection), 0);
	}

	@Test
	public void testUpdate() {
		try {
			database.delete("670");
		} catch (Exception e) {

		}

		try {
			OrdinaryAccount acc1 = new OrdinaryAccount("670");
			database.create(acc1);
			acc1.credit(200);
			database.update(acc1);
			assertEquals(acc1.getBalance(), database.retrieve("670").getBalance(), 0);
		} catch (AccountCreationException e) {
			System.out.println("Should not enter this catch block");
		} catch (NegativeAmountException e) {
			System.out.println("nor this");
		} catch (AccountNotFoundException e) {
			System.out.println("or this");
		}
	}

	@Test
	public void testUpdateBonus() {
		SpecialAccount acc = new SpecialAccount("713");
		acc.setBonus(20);
		database.insertBonus(acc, rawDbConnection);
		acc.setBonus(30);
		database.updateBonus(acc, rawDbConnection);
		assertEquals(acc.getBonus(), database.retrieveBonus("713", rawDbConnection), 0);
	}

	@Test
	public void testDelete() {
		try {
			database.create(new OrdinaryAccount("200"));
			assertEquals(1, database.numberOfAccounts(), 0);
			database.delete("200");
			assertEquals(0, database.numberOfAccounts(), 0);

		} catch (AccountCreationException acex) {
			System.out.println("Should not enter this catch block");
		} catch (AccountDeletionException adex) {
			System.out.println("Should not enter this catch block either");
		} catch (AccountNotFoundException e) {

		}
	}

	@Test
	public void testCreateAccount() {
		assertEquals(OrdinaryAccount.class, database.createAccount(1, "15").getClass());
	}

	@Test
	public void testCreateSpecialAccount() {
		assertEquals(SpecialAccount.class, database.createSpecialAccount("13", 0.1).getClass());
	}

	@Test
	public void testList() {
		try {
			database.delete("670");
		} catch (Exception e) {
			System.out.println("Should not enter this catch block once we make sure the account is created");
		}

		try {
			OrdinaryAccount acc1 = new OrdinaryAccount("670");
			database.create(acc1);
			acc1.credit(200);
			database.update(acc1);
			assertEquals(acc1.getBalance(), database.retrieve("670").getBalance(), 0);
		} catch (AccountCreationException e) {
			System.out.println("Should not enter this catch block");
		} catch (NegativeAmountException e) {
			System.out.println("nor this");
		} catch (AccountNotFoundException e) {
			System.out.println("or this");
		}
	}

	@Test
	public void testNumberOfAccounts() {
		try {
			database.delete("777");
		} catch (Exception e) {
			
		}
		
		try {
			int number = database.numberOfAccounts();
			database.create(new OrdinaryAccount("777"));
			assertEquals(number + 1, database.numberOfAccounts(), 0);
		} catch (AccountCreationException e) {
			
		}
	}

	@Test
	public void testInsertAndSearchLog() {
		try {
			database.create(new OrdinaryAccount("456"));
		} catch (AccountCreationException acex) {}
		
		database.insertLog("456", "TESTE");
		
		assertEquals("TESTE", ((String[]) database.searchLog("456").get(0))[1]);
		
		
	}

}
