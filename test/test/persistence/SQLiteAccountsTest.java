package test.persistence;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import banksys.account.AbstractAccount;
import banksys.account.OrdinaryAccount;
import banksys.account.SavingsAccount;
import banksys.account.SpecialAccount;
import banksys.account.TaxAccount;
import banksys.persistence.SQLiteAccounts;
import banksys.persistence.exception.AccountCreationException;
import banksys.persistence.exception.AccountDeletionException;
import banksys.persistence.exception.AccountNotFoundException;
import junit.framework.TestCase;

public class SQLiteAccountsTest extends TestCase {
	SQLiteAccounts sqliteAccounts;
	OrdinaryAccount ordinaryAccount1,ordinaryAccount2;
	SpecialAccount specialAccount1,specialAccount2;
	SavingsAccount savingsAccount1,savingsAccount2;
	TaxAccount taxAccount1, taxAccount2;
	ArrayList<String> accountsCreated = new ArrayList<String>();
	
	@Before
	public void setUp() throws Exception {
		sqliteAccounts = new SQLiteAccounts();
		ordinaryAccount1 = new OrdinaryAccount("101");
		ordinaryAccount1.credit(200);
		ordinaryAccount2 = new OrdinaryAccount("102");
		ordinaryAccount2.credit(300);
		specialAccount1 = new SpecialAccount("103");
		specialAccount1.credit(400);
		specialAccount2 = new SpecialAccount("104");
		specialAccount2.credit(400);
		savingsAccount1 = new SavingsAccount("105");
		savingsAccount1.credit(500);
		savingsAccount2 = new SavingsAccount("106");
		savingsAccount2.credit(600);
		taxAccount1 = new TaxAccount("107");
		taxAccount1.credit(700);
		taxAccount2 = new TaxAccount("108");
		taxAccount2.credit(800);
	}

	@After
	public void tearDown() throws Exception {
		for(String numero: accountsCreated){
			sqliteAccounts.delete(numero);
		}
		/*sqliteAccounts.delete(c1.getNumber());
		sqliteAccounts.delete(c2.getNumber());
		sqliteAccounts.delete(ce1.getNumber());
		sqliteAccounts.delete(ce2.getNumber());
		sqliteAccounts.delete(cp1.getNumber());
		sqliteAccounts.delete(cp2.getNumber());
		sqliteAccounts.delete(ci1.getNumber());
		sqliteAccounts.delete(ci2.getNumber());*/
	}

	@Test
	public void testCreate() {
		int number = sqliteAccounts.numberOfAccounts();
		try {
			sqliteAccounts.create(ordinaryAccount1);
			accountsCreated.add(ordinaryAccount1.getNumber());
		} catch (AccountCreationException e) {
			System.out.println(e.getMessage());
		}
		assertEquals(number, sqliteAccounts.numberOfAccounts()+1);
	}

	@Test
	public void testRetrieve() {
		try {
			sqliteAccounts.create(ordinaryAccount1);
			accountsCreated.add(ordinaryAccount1.getNumber());
		} catch (AccountCreationException e) {
			System.out.println(e.getMessage());
		}
		AbstractAccount retrievedAccount;
		try {
			retrievedAccount = sqliteAccounts.retrieve(ordinaryAccount1.getNumber());
			assertNotNull(retrievedAccount);
			assertEquals(ordinaryAccount1.getNumber(), retrievedAccount.getNumber());
			assertEquals(ordinaryAccount1.getBalance(), retrievedAccount.getBalance());
		} catch (AccountNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	/*@Test
	public void testInserirBonus() {
		fail("Not yet implemented");
	}

	@Test
	public void testAtualizarOrdinaryAccount() {
		fail("Not yet implemented");
	}

	@Test
	public void testAtualizarBonus() {
		fail("Not yet implemented");
	}
*/
	@Test
	public void testDelete() {
		
		try {
			sqliteAccounts.create(ordinaryAccount1);
			accountsCreated.add(ordinaryAccount1.getNumber());
			sqliteAccounts.delete(ordinaryAccount1.getNumber());
		} catch (AccountCreationException | AccountDeletionException | AccountNotFoundException e) {
			System.out.println(e.getMessage());
		}
		AbstractAccount retrievedAccount;
		try {
			retrievedAccount = sqliteAccounts.retrieve(ordinaryAccount1.getNumber());
			assertNull(retrievedAccount);
			//assertEquals(ordinaryAccount1.getNumber(), retrievedAccount.getNumber());
			//assertEquals(ordinaryAccount1.getBalance(), retrievedAccount.getBalance());
		} catch (AccountNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetType() {
		int typeOrdinaryAccount = sqliteAccounts.getType(ordinaryAccount1);
		int typeSpecialAccount = sqliteAccounts.getType(specialAccount1);
		int typeSavingsAccount = sqliteAccounts.getType(savingsAccount1);
		int typeTaxAccount = sqliteAccounts.getType(taxAccount1);
		assertEquals(1, typeOrdinaryAccount);
		assertEquals(2, typeSpecialAccount);
		assertEquals(3, typeSavingsAccount);
		assertEquals(4, typeTaxAccount);
	}

	/*@Test
	public void testCriarOrdinaryAccount() {
		fail("Not yet implemented");
	}

	@Test
	public void testCriarSpecialAccount() {
		fail("Not yet implemented");
	}

	@Test
	public void testListar() {
		fail("Not yet implemented");
	}*/

	@Test
	public void testnumberOfAccounts() {
		int quantidade = sqliteAccounts.numberOfAccounts();
		assertNotNull(quantidade);
	}

}
