package banksys.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import banksys.account.AbstractAccount;
import banksys.account.OrdinaryAccount;
import banksys.account.SavingsAccount;
import banksys.account.SpecialAccount;
import banksys.account.TaxAccount;
import banksys.persistence.exception.AccountCreationException;
import banksys.persistence.exception.AccountDeletionException;
import banksys.persistence.exception.AccountNotFoundException;

public class SQLiteAccounts implements IAccountRepository{
	private Connection connection;
	public static final String TABLE_ACCOUNTS = "accounts";
	public static final String TABLE_BONUS = "bonus";
	public static final String TABLE_LOG = "log";
	public static final int ORDINARY_ACCOUNT = 1;
	public static final int SPECIAL_ACCOUNT = 2;
	public static final int SAVINGS_ACCOUNT = 3;
	public static final int TAX_ACCOUNT = 4;
	public SQLiteAccounts() {
		super();
	}

	public void createTables()
	{
		connection = SQLiteConnector.getConnection();
		Statement stmt = null;

		try {
			stmt = connection.createStatement();

			String sqlTables = "CREATE TABLE IF NOT EXISTS " + TABLE_ACCOUNTS+
					" (number TEXT PRIMARY KEY ," +
					" balance  REAL NOT NULL, " + 
					" type   INTEGER     NOT NULL) ";
			stmt.executeUpdate(sqlTables);
			stmt.close();

			String sqlLog = "CREATE TABLE IF NOT EXISTS " + TABLE_LOG+
					" (id INTEGER PRIMARY KEY AUTOINCREMENT," +
					" number  TEXT, " + 
					" message TEXT NOT NULL) ";
			stmt.executeUpdate(sqlLog);
			stmt.close();
			stmt = connection.createStatement();

			String sqlBonus = "CREATE TABLE IF NOT EXISTS " + TABLE_BONUS+
					" (number TEXT PRIMARY KEY ," +
					" bonus  REAL NOT NULL) "; 
			stmt.executeUpdate(sqlBonus);
			stmt.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Tables created successfully");
	}

	public void create(AbstractAccount account) throws AccountCreationException {
		AbstractAccount existingAccount = null;
		try{
			existingAccount = retrieve(account.getNumber());
		} catch (AccountNotFoundException ignore){
		}
		if(existingAccount == null){
			connection = SQLiteConnector.getConnection();
			int type = getType(account);
			Statement stmt = null;
			try {
				String values = "VALUES ('"+account.getNumber() + "', "+ account.getBalance()+", "+type+");";
				stmt = connection.createStatement();
				String sql = "INSERT INTO "+  TABLE_ACCOUNTS + "(number,balance,type) " + values;
				if(getType(account)==SPECIAL_ACCOUNT){
					insertBonus((SpecialAccount) account, connection);
				}             
				stmt.executeUpdate(sql);
				stmt.close();
				connection.close();
			} catch ( SQLException e ) {
				System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			}

		} else {
			throw new AccountCreationException("Error creating account ",account.getNumber());
		}
	}

	public AbstractAccount retrieve(String number) throws AccountNotFoundException
	{
		AbstractAccount account = null;
		try (Connection connection = SQLiteConnector.getConnection();
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery( "SELECT * FROM "+TABLE_ACCOUNTS+
						" WHERE number = '"+ number+"';")){

			if(rs.next()){
				String num = rs.getString("number");
				double  balance = rs.getDouble("balance");
				int type = rs.getInt("type");
				if(type == SPECIAL_ACCOUNT){
					double bonus = retrieveBonus(number, connection);
					account = createSpecialAccount(num, bonus);
					account.setBalance(balance);
				} else{
					account = createAccount(type, num);
					account.setBalance(balance); 
				}
			} else throw new AccountNotFoundException(number);
			/*rs.close();
	          stmt.close();
	          connection.close();*/
		} catch ( SQLException e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
		return account;
	}

	public double retrieveBonus(String number, Connection connection){
		double bonus = 0.0;
		try(Statement stmtBonus = connection.createStatement();
				ResultSet rsBonus = stmtBonus.executeQuery( "SELECT * FROM "+TABLE_BONUS+
						" WHERE number = '"+ number+"';")){

			if(rsBonus.next()){
				bonus = rsBonus.getDouble("bonus");
			}
		} catch (SQLException e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
		return bonus;
	}

	public void insertBonus(SpecialAccount account, Connection connection)
	{
		String sql = "INSERT INTO "+  TABLE_BONUS + " (number,bonus) VALUES(?,?)";  
		try(PreparedStatement pstmt = connection.prepareStatement(sql)) {

			pstmt.setString(1, account.getNumber());
			pstmt.setDouble(2, account.getBonus());
			pstmt.executeUpdate();
		} catch ( SQLException e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
	}

	public void update(AbstractAccount account)
	{
		connection = SQLiteConnector.getConnection();  
		Statement stmt = null;
		try {
			stmt = connection.createStatement();
			String sql = "UPDATE "+TABLE_ACCOUNTS+" set balance="+account.getBalance() + " WHERE number='"+account.getNumber()+"';";
			stmt.executeUpdate(sql);
			//connection.commit();
			if(getType(account)==SPECIAL_ACCOUNT){
				updateBonus((SpecialAccount) account, connection);
			}
			stmt.close();
			connection.close();
		} catch ( SQLException e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
	}

	public void updateBonus(SpecialAccount account, Connection connection)
	{
		String sql = "UPDATE "+TABLE_BONUS+" set bonus=? WHERE number=?";
		try(PreparedStatement pstmt = connection.prepareStatement(sql)){
			pstmt.setDouble(1, account.getBonus());
			pstmt.setString(2, account.getNumber());
			pstmt.executeUpdate();
		} catch ( SQLException e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
	}

	public void delete(String number) throws AccountDeletionException, AccountNotFoundException
	{
		AbstractAccount account = retrieve(number);
		if(account != null){
			connection = SQLiteConnector.getConnection();
			Statement stmt = null;
			try {
				stmt = connection.createStatement();
				String sql = "DELETE from "+TABLE_ACCOUNTS+" where number="+number+";";
				stmt.executeUpdate(sql);
				stmt.close();

				if(getType(account)==SPECIAL_ACCOUNT){
					stmt = connection.createStatement();
					String sqlBonus = "DELETE from "+TABLE_BONUS+" where number="+number+";";
					stmt.executeUpdate(sqlBonus);
					stmt.close();
				}

				connection.close();
			} catch ( Exception e ) {
				System.err.println( e.getClass().getName() + ": " + e.getMessage() );
				System.exit(0);
			}
			System.out.println("Operation done successfully");
		} else throw new AccountDeletionException("",number);
	}
	public int getType(AbstractAccount account){
		/*if(account instanceof SpecialAccount){
			return SPECIAL_ACCOUNT;
		} else if(account instanceof SavingsAccount){
			return SAVINGS_ACCOUNT;
		}  else if(account instanceof OrdinaryAccount){
			return ORDINARY_ACCOUNT;
		}  else {
			return TAX_ACCOUNT;
		}*/return account.getType();
	}
	public AbstractAccount createAccount(int type, String num){

		switch(type){
		case ORDINARY_ACCOUNT: return new OrdinaryAccount(num);
		case SPECIAL_ACCOUNT: return new SpecialAccount(num);
		case SAVINGS_ACCOUNT: return new SavingsAccount(num);
		case TAX_ACCOUNT: return new TaxAccount(num);
		default: return null;
		}
	}

	public SpecialAccount createSpecialAccount(String num,double bonus){

		SpecialAccount SpecialAccount = new SpecialAccount(num);
		SpecialAccount.setBonus(bonus);
		return SpecialAccount;
	}

	@Override
	public AbstractAccount[] list() {
		int size = numberOfAccounts();	
		AbstractAccount account = null;
		AbstractAccount[] accounts = new AbstractAccount[size];
		Statement stmt = null;
		if(size != 0 ){
			connection = SQLiteConnector.getConnection();
			try {
				stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery( "SELECT * FROM "+TABLE_ACCOUNTS+";");
				int i = 0;   
				while(rs.next()){
					String num = rs.getString("number");
					double  balance = rs.getDouble("balance");
					int type = rs.getInt("type");
					account = createAccount(type, num);
					account.setBalance(balance);
					accounts[i] = account;
					i++;
				}
				rs.close();
				stmt.close();
				connection.close();
			} catch ( Exception e ) {
				System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			}
			System.out.println("Operation done successfully");
		}
		return accounts;
	}

	@Override
	public int numberOfAccounts() {
		
		int number = 0;
		
		try(Connection connection = SQLiteConnector.getConnection();
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery( "SELECT COUNT(*) FROM "+TABLE_ACCOUNTS+";");
				) {
			//stmt = connection.createStatement();
			//ResultSet rs = stmt.executeQuery( "SELECT COUNT(*) FROM "+TABLE_ACCOUNTS+";");

			if(rs.next()){
				number = rs.getInt("count(*)");
			}
			/*rs.close();
			stmt.close();
			connection.close();*/
		} catch ( SQLException e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
		return number;
	}

	public void insertLog(String numAccount, String message)
	{
		String sql = "INSERT INTO "+  TABLE_LOG + " (number,message) VALUES('"+ numAccount +"','"+ message+ "')";  
		try {
			connection = SQLiteConnector.getConnection();
			Statement stmt = null;
			stmt = connection.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			connection.close();
		} catch ( SQLException e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
	}

	public ArrayList searchLog(String number)
	{
		String sql = "SELECT * FROM " +  TABLE_LOG + " WHERE number = '" + number + "'";  
		ArrayList<String[]> lista = new ArrayList<String[]>();
		String[] saida;
		try {
			connection = SQLiteConnector.getConnection();
			Statement stmt = null;
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				saida = new String[2];
				saida[0] = rs.getString("number");
				saida[1] = rs.getString("message");
				lista.add(saida);
			}
			stmt.close();
			connection.close();
			return lista;
		} catch ( SQLException e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			return null;
		}
	}
}