import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector extends Credentials{
	
	private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/quiz";
	private static final String usernameDB = username;
	private static final String passwordDB = password;
	
	public static Connection getConnection() throws SQLException {
		
		
		return DriverManager.getConnection(JDBC_URL, usernameDB, passwordDB);
	}

}
