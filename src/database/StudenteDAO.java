package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.EntityStudente;
import exception.DAOException;
import exception.DBConnectionException;

public class StudenteDAO {
	public static EntityStudente readStudente(String username) throws DAOException, DBConnectionException {

		EntityStudente eS = null;

		try {

			Connection conn = DBManager.getConnection();
			String query = "SELECT * FROM STUDENTE WHERE USERNAME=?;";
			
			try {

				PreparedStatement stmt = conn.prepareStatement(query);

				stmt.setString(1, username);

				ResultSet result = stmt.executeQuery();

				if(result.next()) {
					eS = new EntityStudente(username, result.getString(2), result.getInt(3), result.getInt(4));	
				}

			}catch(SQLException e) {
				throw new DAOException("Errore lettura studente");
			}finally {
				DBManager.closeConnection();
			}
			
		}catch(SQLException e) {
			throw new DBConnectionException("Errore di connessione DB");
		}

		return eS;
	}
	
	public static void createStudente(EntityStudente eS) throws DAOException, DBConnectionException {
        try {
            Connection conn = DBManager.getConnection();

            String query = "INSERT INTO STUDENTE VALUES (?, ?, ?, ?);";

            try {
                PreparedStatement stmt = conn.prepareStatement(query);

                stmt.setString(1, eS.getUsername());
                stmt.setString(2, eS.getPassword());
                stmt.setInt(3, eS.getPIN());
                stmt.setInt(4, eS.getIdCorsodiStudio());

                stmt.executeUpdate();

            } catch (SQLException e) {
                throw new DAOException("Errore scrittura studente");
            } finally {
                DBManager.closeConnection();
            }

        } catch (SQLException e) {
            throw new DBConnectionException("Errore connessione database");
        }
    }
	
}

