package banksys.control;

import javax.swing.JOptionPane;

import banksys.account.AbstractAccount;
import banksys.account.SavingsAccount;
import banksys.account.SpecialAccount;
import banksys.account.exception.InsufficientFundsException;
import banksys.account.exception.NegativeAmountException;
import banksys.control.exception.BankTransactionException;
import banksys.control.exception.IncompatibleAccountException;
import banksys.persistence.IAccountRepository;
import banksys.persistence.SQLiteAccounts;
import banksys.persistence.exception.AccountCreationException;
import banksys.persistence.exception.AccountDeletionException;
import banksys.persistence.exception.AccountNotFoundException;

public class BankController {

	private static final String DESTINATION_ACCOUNT_NOT_FOUND = "destination account not found";
	private static final String ORIGIN_ACCOUNT_NOT_FOUND = "origin account not found";
	private static final String NOT_SAVINGS_ACCOUNT = "Account is not a Savings Account!";
	private static final String NOT_SPECIAL_ACCOUNT = "Account is not a Special Account!";
	private static final String OPERATION_NOT_DONE = "Operation not done!";
	private static final String ACCOUNT_REMOVED = "Account removed!";
	private static final String ACCOUNT_ALREADY_EXISTS = "Account already exists";
	private static final String ERROR = "Error";
	private static final String ACCOUNT_NOT_FOUND = "Account not found!";
	private static final String WARNING = "Warning";
	private static final String ACCOUNT_CREATED = "Account created";
	private SQLiteAccounts repository;
	private static final Object[] options = {"OK"};

	public BankController(SQLiteAccounts repository) {
		this.repository = repository;
	}

	public void addAccount(AbstractAccount account) throws BankTransactionException {
		try {
			this.repository.create(account);
			JOptionPane.showOptionDialog(null, ACCOUNT_CREATED, WARNING, JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		} catch (AccountCreationException ace) {
			JOptionPane.showOptionDialog(null, ACCOUNT_ALREADY_EXISTS, ERROR, JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
			throw new BankTransactionException(ace);
		}
	}

	public void removeAccount(String number) throws BankTransactionException {
		try {
			this.repository.delete(number);
			JOptionPane.showOptionDialog(null, ACCOUNT_REMOVED, WARNING, JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		} catch (AccountDeletionException | AccountNotFoundException ade) {
			JOptionPane.showOptionDialog(null, ACCOUNT_NOT_FOUND, ERROR, JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
			throw new BankTransactionException(ade);
		}
	}

	public void doCredit(String number, double amount) throws BankTransactionException {
		AbstractAccount account;
		try {
			account = this.repository.retrieve(number);
		} catch (AccountNotFoundException anfe) {
			throw new BankTransactionException(anfe);
		}
		try {
			account.credit(amount);
			
		} catch (NegativeAmountException nae) {
			throw new BankTransactionException(nae);
		}
		try {
			this.repository.update(account);
			JOptionPane.showOptionDialog(null, OPERATION_NOT_DONE, WARNING, JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		} catch (Exception e) {
			throw new BankTransactionException(e);
		}
	}

	public void doDebit(String number, double amount) throws BankTransactionException {
		AbstractAccount account;
		try {
			account = this.repository.retrieve(number);
		} catch (AccountNotFoundException anfe) {
			throw new BankTransactionException(anfe);
		}
		try {
			account.debit(amount);
			this.repository.update(account);
			JOptionPane.showOptionDialog(null, OPERATION_NOT_DONE, WARNING, JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		} catch (InsufficientFundsException | NegativeAmountException e) {
			throw new BankTransactionException(e);
		}
	}

	public double getBalance(String number) throws BankTransactionException {
		AbstractAccount conta;
		try {
			conta = this.repository.retrieve(number);
			return conta.getBalance();
		} catch (AccountNotFoundException anfe) {
			JOptionPane.showOptionDialog(null, ACCOUNT_NOT_FOUND, ERROR, JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
			throw new BankTransactionException(anfe);
		}

	}

	public void doTransfer(String fromNumber, String toNumber, double amount) throws BankTransactionException {
		AbstractAccount fromAccount;
		try {
			fromAccount = this.repository.retrieve(fromNumber);
		} catch (AccountNotFoundException anfe) {
			JOptionPane.showOptionDialog(null, ORIGIN_ACCOUNT_NOT_FOUND, ERROR, JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
			throw new BankTransactionException(anfe);
		}

		AbstractAccount toAccount;
		try {
			toAccount = this.repository.retrieve(toNumber);
		} catch (AccountNotFoundException anfe) {
			JOptionPane.showOptionDialog(null, DESTINATION_ACCOUNT_NOT_FOUND, ERROR, JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
			throw new BankTransactionException(anfe);
		}

		try {
			fromAccount.debit(amount);
			toAccount.credit(amount);
			this.repository.update(fromAccount);
			this.repository.update(toAccount);
			JOptionPane.showOptionDialog(null, OPERATION_NOT_DONE, WARNING, JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		} catch (InsufficientFundsException sie) {
			throw new BankTransactionException(sie);
		} catch (NegativeAmountException nae) {
			throw new BankTransactionException(nae);
		} catch (Exception anfe) {
			throw new BankTransactionException(anfe);
		}
	}

	public void doEarnInterest(String number) throws BankTransactionException, IncompatibleAccountException {
		AbstractAccount auxAccount;
		try {
			auxAccount = this.repository.retrieve(number);
		} catch (AccountNotFoundException anfe) {
			JOptionPane.showOptionDialog(null, ACCOUNT_NOT_FOUND, ERROR, JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
			throw new BankTransactionException(anfe);
		}

		if (auxAccount instanceof SavingsAccount) {
			((SavingsAccount) auxAccount).earnInterest();
			try {
				this.repository.update(auxAccount);
			} catch (Exception anfe) {
				throw new BankTransactionException(anfe);
			}
			JOptionPane.showOptionDialog(null, OPERATION_NOT_DONE, WARNING, JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		} else {
			JOptionPane.showOptionDialog(null, NOT_SAVINGS_ACCOUNT, ERROR, JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
			throw new IncompatibleAccountException(number);
		}
	}

	public void doEarnBonus(String number) throws BankTransactionException, IncompatibleAccountException {
		AbstractAccount auxAccount;
		try {
			auxAccount = this.repository.retrieve(number);
		} catch (AccountNotFoundException anfe) {
			JOptionPane.showOptionDialog(null, ACCOUNT_NOT_FOUND, ERROR, JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
			throw new BankTransactionException(anfe);
		}

		if (auxAccount instanceof SpecialAccount) {
			((SpecialAccount) auxAccount).earnBonus();
			try {
				this.repository.update(auxAccount);
			} catch (Exception anfe) {
				throw new BankTransactionException(anfe);
			}
			JOptionPane.showOptionDialog(null, OPERATION_NOT_DONE, WARNING, JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		} else {
			JOptionPane.showOptionDialog(null, NOT_SPECIAL_ACCOUNT, ERROR, JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
			throw new IncompatibleAccountException(number);
		}
	}
}
