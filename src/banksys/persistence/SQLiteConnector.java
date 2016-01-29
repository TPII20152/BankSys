package banksys.persistence;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnector {
	private static final String dbURL="jdbc:sqlite:contas.sqlite";
	private static Connection connection;
    public static Connection getConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            //String dbURL = "jdbc:sqlite:product.db";
            connection = DriverManager.getConnection(dbURL);
            /*if (connection != null) {
                System.out.println("Connected to the database");
                DatabaseMetaData dm = (DatabaseMetaData) connection.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Driver version: " + dm.getDriverVersion());
                System.out.println("Product name: " + dm.getDatabaseProductName());
                System.out.println("Product version: " + dm.getDatabaseProductVersion());
                connection.setAutoCommit(false);
                //connection.close();
            }*/
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return connection;
    }
}