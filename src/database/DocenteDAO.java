package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.EntityDocente;
import exception.DAOException;
import exception.DBConnectionException;

public class DocenteDAO {
	public static void createDocente(EntityDocente eD) throws DAOException, DBConnectionException {
	    try {
	        Connection conn = DBManager.getConnection();

	        String query = "INSERT INTO DOCENTE VALUES (?, ?, ?);";
			
	        try {
	            PreparedStatement stmt = conn.prepareStatement(query);

	            stmt.setString(1, eD.getNomeDocente());
	            stmt.setString(2, eD.getCognomeDocente());
	            stmt.setString(3, eD.getMatricola());

	            stmt.executeUpdate();

	        } catch (SQLException e) {
	            throw new DAOException("Errore scrittura docente");
	        } finally {
	            DBManager.closeConnection();
	        }

	    } catch (SQLException e) {
	        throw new DBConnectionException("Errore connessione database");
	    }
	}
	
	public static EntityDocente readDocente(String matricola) throws DAOException, DBConnectionException {

		EntityDocente eD = null;

		try {

			Connection conn = DBManager.getConnection();
			String query = "SELECT * FROM DOCENTE WHERE MATRICOLA=?;";
			
			try {

				PreparedStatement stmt = conn.prepareStatement(query);
				stmt.setString(1, matricola);
				ResultSet result = stmt.executeQuery();

				if(result.next()) {
					eD = new EntityDocente(result.getString(1), result.getString(2), matricola);	
				}
			}catch(SQLException e) {
				throw new DAOException("Errore lettura docente");
			}finally {
				DBManager.closeConnection();
			}
		}catch(SQLException e) {
			throw new DBConnectionException("Errore di connessione DB");
		}
		return eD;
	}

}
