package src.banksys.persistence;

import src.banksys.account.AbstractAccount;
import src.banksys.persistence.exception.AccountCreationException;
import src.banksys.persistence.exception.AccountDeletionException;
import src.banksys.persistence.exception.AccountNotFoundException;

public interface IAccountRepository {

	public void create(AbstractAccount account) throws AccountCreationException;

	public void delete(String number) throws AccountDeletionException;

	public AbstractAccount retrieve(String number) throws AccountNotFoundException;

	public AbstractAccount[] list();

	public int mumberOfAccounts();
}
