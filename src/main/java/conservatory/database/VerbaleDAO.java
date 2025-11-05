package conservatory.database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conservatory.entity.EntityVerbale;
import conservatory.exception.DAOException;
import conservatory.exception.DBConnectionException;

import java.sql.Date;

public class VerbaleDAO {
	
		public static EntityVerbale readReport(String reportCode) throws DAOException, DBConnectionException {
			EntityVerbale eV = null;

			try {

				Connection conn = DBManager.getConnection();

				String query = "SELECT * FROM REPORT WHERE REPORTCODE=?;";

				try {
					PreparedStatement stmt = conn.prepareStatement(query);

					stmt.setString(1, reportCode);

					ResultSet result = stmt.executeQuery();

					if(result.next()) {
						eV = new EntityVerbale(result.getDate(1), reportCode, result.getString(3));	
					}
				}catch(SQLException e) {
					throw new DAOException("Report reading error");
				} finally {
					DBManager.closeConnection();
				}

			}catch(SQLException e) {
				throw new DBConnectionException("DB connection error");
			}

			return eV;
		}
		
	public static void createReport(EntityVerbale eB) throws DAOException, DBConnectionException {
			
			try {
				
				Connection conn = DBManager.getConnection();

				String query = "INSERT INTO REPORT VALUES (?,?,?);";

				try {
					PreparedStatement stmt = conn.prepareStatement(query);
					
					stmt.setDate(1, new Date(eB.getreportDate().getTime()));
					stmt.setString(2, eB.getreportCode());
					stmt.setString(3, eB.getteacherID());

					stmt.executeUpdate();

				}catch(SQLException e) {
					throw new DAOException("Report writing error");
				} finally {
					DBManager.closeConnection();
				}

			}catch(SQLException e) {
				throw new DBConnectionException("DB connection error");
			}

	}         
                	        

}
