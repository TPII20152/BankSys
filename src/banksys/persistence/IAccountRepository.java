package banksys.persistence;

import banksys.account.AbstractAccount;
import banksys.persistence.exception.AccountCreationException;
import banksys.persistence.exception.AccountDeletionException;
import banksys.persistence.exception.AccountNotFoundException;

public interface IAccountRepository {

	public void create(AbstractAccount account) throws AccountCreationException, AccountNotFoundException;

	public void delete(String number) throws AccountDeletionException, AccountNotFoundException;

	public AbstractAccount retrieve(String number) throws AccountNotFoundException;

	public AbstractAccount[] list();

	public int numberOfAccounts();
	
	public void update(AbstractAccount account) throws AccountNotFoundException;
}
