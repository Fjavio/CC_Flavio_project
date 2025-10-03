package database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

import entity.EntityVerbale;
import exception.DAOException;
import exception.DBConnectionException;

public class VerbaleDAO {
	
		public static EntityVerbale readVerbale(String codiceVerbale) throws DAOException, DBConnectionException {
			EntityVerbale eV = null;

			try {

				Connection conn = DBManager.getConnection();

				String query = "SELECT * FROM VERBALE WHERE CODICEVERBALE=?;";

				try {
					PreparedStatement stmt = conn.prepareStatement(query);

					stmt.setString(1, codiceVerbale);

					ResultSet result = stmt.executeQuery();

					if(result.next()) {
						eV = new EntityVerbale(result.getDate(1), codiceVerbale, result.getString(3));	
					}
				}catch(SQLException e) {
					throw new DAOException("Errore lettura verbale");
				} finally {
					DBManager.closeConnection();
				}

			}catch(SQLException e) {
				throw new DBConnectionException("Errore di connessione DB");
			}

			return eV;
		}
		
	public static void createVerbale(EntityVerbale eB) throws DAOException, DBConnectionException {
			
			try {
				
				Connection conn = DBManager.getConnection();

				String query = "INSERT INTO VERBALE VALUES (?,?,?);";

				try {
					PreparedStatement stmt = conn.prepareStatement(query);
					
					stmt.setDate(1, new Date(eB.getDataVerbale().getTime()));
					stmt.setString(2, eB.getCodiceVerbale());
					stmt.setString(3, eB.getMatricolaDocente());

					stmt.executeUpdate();

				}catch(SQLException e) {
					throw new DAOException("Errore scrittura verbale");
				} finally {
					DBManager.closeConnection();
				}

			}catch(SQLException e) {
				throw new DBConnectionException("Errore connessione database");
			}

	}         
                	        

}
