package conservatory.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conservatory.entity.EntityDocente;
import conservatory.exception.DAOException;
import conservatory.exception.DBConnectionException;

public class DocenteDAO {
	public static void createTeacher(EntityDocente eD) throws DAOException, DBConnectionException {
	    try {
	        Connection conn = DBManager.getConnection();

	        String query = "INSERT INTO teacher VALUES (?, ?, ?);";
			
	        try {
	            PreparedStatement stmt = conn.prepareStatement(query);

	            stmt.setString(1, eD.getteacherName());
	            stmt.setString(2, eD.getteacherSurname());
	            stmt.setString(3, eD.getID());

	            stmt.executeUpdate();

	        } catch (SQLException e) {
	            throw new DAOException("Teacher writing error");
	        } finally {
	            DBManager.closeConnection();
	        }

	    } catch (SQLException e) {
	        throw new DBConnectionException("DB connection error");
	    }
	}
	
	public static EntityDocente readTeacher(String ID) throws DAOException, DBConnectionException {

		EntityDocente eD = null;

		try {

			Connection conn = DBManager.getConnection();
			String query = "SELECT * FROM teacher WHERE ID=?;";
			
			try {

				PreparedStatement stmt = conn.prepareStatement(query);
				stmt.setString(1, ID);
				ResultSet result = stmt.executeQuery();

				if(result.next()) {
					eD = new EntityDocente(result.getString(1), result.getString(2), ID);	
				}
			}catch(SQLException e) {
				throw new DAOException("Teacher reading error");
			}finally {
				DBManager.closeConnection();
			}
		}catch(SQLException e) {
			throw new DBConnectionException("DB connection error");
		}
		return eD;
	}

}
