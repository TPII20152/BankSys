package banksys.persistence.exception;

public class AccountNotFoundException extends PersistenceException {

	private static final long serialVersionUID = 1L;

	public AccountNotFoundException(String number) {
		super("Account not found! ", number);
	}

}
