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
	public static final String TABLE_ACCOUNTS = "contas";
	public static final String TABLE_BONUS = "bonus";
	
	public SQLiteAccounts() {
		super();
	}

	  public void createDB()
	  {
		    connection = SQLiteConnector.getConnection();
	        Statement stmt = null;
	        
	          try {
				stmt = connection.createStatement();
			
	          String sqlTables = "CREATE TABLE IF NOT EXISTS " + TABLE_ACCOUNTS+
	                       " (numero INTEGER PRIMARY KEY ," +
	                       " saldo  REAL NOT NULL, " + 
	                       " tipo   INTEGER     NOT NULL) ";
	          stmt.executeUpdate(sqlTables);
	          stmt.close();
	          stmt = connection.createStatement();
				
	          String sqlBonus = "CREATE TABLE IF NOT EXISTS bonus " +
	                       "(numero INTEGER PRIMARY KEY ," +
	                       " bonus  REAL NOT NULL) "; 
	          stmt.executeUpdate(sqlBonus);
	          stmt.close();
	          connection.close();
	          } catch (SQLException e) {
					e.printStackTrace();
				}
	        System.out.println("Tables created successfully");
	  }
	   
	  public void create(AbstractAccount conta) throws AccountCreationException {
		  if(retrieve(conta.getNumber()) == null){
			  connection = SQLiteConnector.getConnection();
			    int tipo = getTipo(conta);
		        Statement stmt = null;
		        try {
		          String values = "VALUES ("+conta.getNumber() + ", "+ conta.getBalance()+", "+tipo+");";
		          stmt = connection.createStatement();
		          String sql = "INSERT INTO "+  TABLE_ACCOUNTS + "(numero,saldo,tipo) " + values;
		          if(getTipo(conta)==2){
			    	  inserirBonus((SpecialAccount) conta);
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
			  throw new AccountCreationException("Error creating account ",conta.getNumber());
		  }
	  }
	   
	  public AbstractAccount retrieve(String numero)
	  {
		  	connection = SQLiteConnector.getConnection();
	        AbstractAccount conta = null;
	        Statement stmt = null;
	        try {
	          stmt = connection.createStatement();
	          ResultSet rs = stmt.executeQuery( "SELECT * FROM "+TABLE_ACCOUNTS+
	        		  							" WHERE numero = "+ numero+";");
	             
	          if(rs.next()){
	            	 String num = rs.getString("numero");
		             double  saldo = rs.getDouble("saldo");
		             int tipo = rs.getInt("tipo");
		             if(tipo == 2){
		            	 Statement stmtBonus = connection.createStatement();
		            	 ResultSet rsBonus = stmt.executeQuery( "SELECT bonus FROM "+TABLE_BONUS+
		  							" WHERE numero = "+ numero+";");
		            	 if(rsBonus.next()){
		            		 double bonus = rs.getDouble("bonus");
		            		 conta = createSpecialAccount(num, bonus);
				             if(saldo >= 0) conta.credit(saldo); 
				             	else conta.debit(saldo);
				             }
		            	 rsBonus.close();
		            	 stmtBonus.close();	 
		             } else{
		            	 conta = createOrdinaryAccount(tipo, num);
			             if(saldo >= 0) conta.credit(saldo); 
			             	else conta.debit(saldo);
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
	        return conta;
	  }
	  
	  public void inserirBonus(SpecialAccount conta)
	  {
	    try {
	      connection = SQLiteConnector.getConnection();  
	      Statement stmt = connection.createStatement();
	      String values = "VALUES ("+conta.getNumber() + ", "+ conta.getBonus()+");";
          stmt = connection.createStatement();
          String sql = "INSERT INTO "+  TABLE_BONUS + " (numero,bonus) " + values;
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
	  
	  public void atualizarOrdinaryAccount(AbstractAccount conta)
	  {
		connection = SQLiteConnector.getConnection();  
	    Statement stmt = null;
	    try {
	      stmt = connection.createStatement();
	      //String values = "VALUES ("
	      String sql = "UPDATE "+TABLE_ACCOUNTS+" set saldo="+conta.getBalance() + " WHERE numero="+conta.getNumber()+";";
	      stmt.executeUpdate(sql);
	      //connection.commit();
	      if(getTipo(conta)==2){
	    	  atualizarBonus((SpecialAccount) conta);
	      }
	      stmt.close();
	      connection.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Operation done successfully");
	  }
	  
	  public void atualizarBonus(SpecialAccount conta)
	  {
	    try {
	      connection = SQLiteConnector.getConnection();  
		  Statement stmt = null;  
	      stmt = connection.createStatement();
	      //String values = "VALUES ("
	      String sql = "UPDATE "+TABLE_BONUS+" set bonus="+conta.getBonus() + " WHERE numero="+conta.getNumber()+";";
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
	  
	  public void delete(String numero) throws AccountDeletionException
	  {
		  AbstractAccount conta = retrieve(numero);
		  if(conta != null){
		    connection = SQLiteConnector.getConnection();
	        Statement stmt = null;
	        try {
	          stmt = connection.createStatement();
	          String sql = "DELETE from "+TABLE_ACCOUNTS+" where numero="+numero+";";
	          stmt.executeUpdate(sql);
	          //connection.commit();
	          stmt.close();
	          
	          if(getTipo(conta)==2){
	        	  stmt = connection.createStatement();
		          String sqlBonus = "DELETE from "+TABLE_BONUS+" where numero="+numero+";";
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
		  } else throw new AccountDeletionException("",numero);
	  }
	  public int getTipo(AbstractAccount conta){
		  if(conta instanceof SpecialAccount){
			  return 2;
		  } else if(conta instanceof SavingsAccount){
			  return 3;
		  }  else if(conta instanceof OrdinaryAccount){
			  return 1;
		  }  else {
			  return 4;
		  }
	  }
	  public AbstractAccount createOrdinaryAccount(int tipo, String num){
		  
		  switch(tipo){
		  	case 1: return new OrdinaryAccount(num);
		  	case 2: return new SpecialAccount(num);
		  	case 3: return new SavingsAccount(num);
		  	case 4: return new TaxAccount(num);
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
		int tamanho = numberOfAccounts();	
        AbstractAccount conta = null;
        AbstractAccount[] contas = new AbstractAccount[tamanho];
        Statement stmt = null;
        if(tamanho != 0 ){
        	connection = SQLiteConnector.getConnection();
        	try {
                stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery( "SELECT * FROM "+TABLE_ACCOUNTS+";");
                int i = 0;   
                while(rs.next()){
                  	 String num = rs.getString("numero");
      	             double  saldo = rs.getDouble("saldo");
      	             int tipo = rs.getInt("tipo");
      	             conta = createOrdinaryAccount(tipo, num);
      	             if(saldo >= 0) conta.credit(saldo); 
      	             	else conta.debit(saldo);
      	             contas[i] = conta;
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
		return contas;
	}

	@Override
	public int numberOfAccounts() {
		connection = SQLiteConnector.getConnection();
        int quantidade = 0;
        Statement stmt = null;
        try {
          stmt = connection.createStatement();
          ResultSet rs = stmt.executeQuery( "SELECT COUNT(*) FROM "+TABLE_ACCOUNTS+";");
             
          if(rs.next()){
            	 quantidade = rs.getInt("count(*)");
             }
          rs.close();
          stmt.close();
          connection.close();
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        System.out.println("Operation done successfully");
		return quantidade;
	}
}
