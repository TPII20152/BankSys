package banksys.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import banksys.account.AbstractAccount;
import banksys.account.OrdinaryAccount;
import banksys.account.SavingsAccount;
import banksys.account.SpecialAccount;
import banksys.account.TaxAccount;
import banksys.persistence.exception.AccountCreationException;
import banksys.persistence.exception.AccountDeletionException;

public class SQLiteAccounts implements IAccountRepository{
	private Connection connection;
	public static final String TABLE_ACCOUNTS = "accounts";
	public static final String TABLE_BONUS = "bonus";
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
	          stmt = connection.createStatement();
				
	          String sqlBonus = "CREATE TABLE IF NOT EXISTS " + TABLE_BONUS+
	                       " (number INTEGER PRIMARY KEY ," +
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
		  if(retrieve(account.getNumber()) == null){
			  connection = SQLiteConnector.getConnection();
			    int type = getTipo(account);
		        Statement stmt = null;
		        try {
		          String values = "VALUES ("+account.getNumber() + ", "+ account.getBalance()+", "+type+");";
		          stmt = connection.createStatement();
		          String sql = "INSERT INTO "+  TABLE_ACCOUNTS + "(number,balance,type) " + values;
		          if(getTipo(account)==SPECIAL_ACCOUNT){
			    	  insertBonus((SpecialAccount) account);
			      }             
		          stmt.executeUpdate(sql);
		          stmt.close();
		          connection.close();
		        } catch ( Exception e ) {
		          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		          System.exit(0);
		        }
		        System.out.println("Records created successfully");
		  } else {
			  throw new AccountCreationException("Error creating account ",account.getNumber());
		  }
	  }
	   
	  public AbstractAccount retrieve(String number)
	  {
		  	connection = SQLiteConnector.getConnection();
	        AbstractAccount account = null;
	        Statement stmt = null;
	        try {
	          stmt = connection.createStatement();
	          ResultSet rs = stmt.executeQuery( "SELECT * FROM "+TABLE_ACCOUNTS+
	        		  							" WHERE number = "+ number+";");
	             
	          if(rs.next()){
	            	 String num = rs.getString("number");
		             double  balance = rs.getDouble("balance");
		             int type = rs.getInt("type");
		             if(type == SPECIAL_ACCOUNT){
		            	 Statement stmtBonus = connection.createStatement();
		            	 ResultSet rsBonus = stmt.executeQuery( "SELECT bonus FROM "+TABLE_BONUS+
		  							" WHERE number = "+ number+";");
		            	 if(rsBonus.next()){
		            		 double bonus = rs.getDouble("bonus");
		            		 account = createSpecialAccount(num, bonus);
				             if(balance >= 0) account.credit(balance); 
				             	else account.debit(balance);
				             }
		            	 rsBonus.close();
		            	 stmtBonus.close();	 
		             } else{
		            	 account = createAccount(type, num);
			             if(balance >= 0) account.credit(balance); 
			             	else account.debit(balance);
			             }
	             }
	          rs.close();
	          stmt.close();
	          connection.close();
	        } catch ( Exception e ) {
	          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	          //System.exit(0);
	        }
	        System.out.println("Operation done successfully");
	        return account;
	  }
	  
	  public void insertBonus(SpecialAccount account)
	  {
	    try {
	      connection = SQLiteConnector.getConnection();  
	      Statement stmt = connection.createStatement();
	      String values = "VALUES ("+account.getNumber() + ", "+ account.getBonus()+");";
          stmt = connection.createStatement();
          String sql = "INSERT INTO "+  TABLE_BONUS + " (number,bonus) " + values;
	      //connection.commit();
          stmt.executeUpdate(sql);
	      stmt.close();
	      connection.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Operation done successfully");
	  }
	  
	  public void updateAccount(AbstractAccount account)
	  {
		connection = SQLiteConnector.getConnection();  
	    Statement stmt = null;
	    try {
	      stmt = connection.createStatement();
	      //String values = "VALUES ("
	      String sql = "UPDATE "+TABLE_ACCOUNTS+" set balance="+account.getBalance() + " WHERE number="+account.getNumber()+";";
	      stmt.executeUpdate(sql);
	      //connection.commit();
	      if(getTipo(account)==SPECIAL_ACCOUNT){
	    	  updateBonus((SpecialAccount) account);
	      }
	      stmt.close();
	      connection.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Operation done successfully");
	  }
	  
	  public void updateBonus(SpecialAccount account)
	  {
	    try {
	      connection = SQLiteConnector.getConnection();  
		  Statement stmt = null;  
	      stmt = connection.createStatement();
	      //String values = "VALUES ("
	      String sql = "UPDATE "+TABLE_BONUS+" set bonus="+account.getBonus() + " WHERE number="+account.getNumber()+";";
	      stmt.executeUpdate(sql);
	      //connection.commit();
	 
	      stmt.close();
	      connection.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Operation done successfully");
	  }
	  
	  public void delete(String number) throws AccountDeletionException
	  {
		  AbstractAccount account = retrieve(number);
		  if(account != null){
		    connection = SQLiteConnector.getConnection();
	        Statement stmt = null;
	        try {
	          stmt = connection.createStatement();
	          String sql = "DELETE from "+TABLE_ACCOUNTS+" where number="+number+";";
	          stmt.executeUpdate(sql);
	          //connection.commit();
	          stmt.close();
	          
	          if(getTipo(account)==SPECIAL_ACCOUNT){
	        	  stmt = connection.createStatement();
		          String sqlBonus = "DELETE from "+TABLE_BONUS+" where number="+number+";";
		          stmt.executeUpdate(sqlBonus);
		          //connection.commit();
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
	  public int getTipo(AbstractAccount account){
		  if(account instanceof SpecialAccount){
			  return SPECIAL_ACCOUNT;
		  } else if(account instanceof SavingsAccount){
			  return SAVINGS_ACCOUNT;
		  }  else if(account instanceof OrdinaryAccount){
			  return ORDINARY_ACCOUNT;
		  }  else {
			  return TAX_ACCOUNT;
		  }
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
      	             if(balance >= 0) account.credit(balance); 
      	             	else account.debit(balance);
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
		connection = SQLiteConnector.getConnection();
        int number = 0;
        Statement stmt = null;
        try {
          stmt = connection.createStatement();
          ResultSet rs = stmt.executeQuery( "SELECT COUNT(*) FROM "+TABLE_ACCOUNTS+";");
             
          if(rs.next()){
            	 number = rs.getInt("count(*)");
             }
          rs.close();
          stmt.close();
          connection.close();
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
		return number;
	}
}
