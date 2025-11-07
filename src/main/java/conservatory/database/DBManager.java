//Refactoring because hito says "single source of truth using dependency injection."
//DBManager with the static connection and hardcoded URL is the exact opposite of this.

/*package conservatory.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
private static Connection conn = null;
	
	private DBManager() {}
	
	public static Connection getConnection() throws SQLException {
			
			if(conn == null || conn.isClosed()) {
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionecorsidistudioconservatorio", "root", "");
			}
			
			return conn;
		
	}
	
	
	public static void closeConnection() throws SQLException {
		
			if(conn != null) {
				conn.close();
			}
	}
}
*/