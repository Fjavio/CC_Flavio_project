package conservatory.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conservatory.entity.EntityStudente;
import conservatory.exception.DAOException;
import conservatory.exception.DBConnectionException;

public class StudenteDAO {
	public static EntityStudente readStudent(String username) throws DAOException, DBConnectionException {

		EntityStudente eS = null;

		try {

			Connection conn = DBManager.getConnection();
			String query = "SELECT * FROM student WHERE username=?;";
			
			try {

				PreparedStatement stmt = conn.prepareStatement(query);

				stmt.setString(1, username);

				ResultSet result = stmt.executeQuery();

				if(result.next()) {
					eS = new EntityStudente(username, result.getString(2), result.getInt(3), result.getInt(4));	
				}

			}catch(SQLException e) {
				throw new DAOException("Student reading error");
			}finally {
				DBManager.closeConnection();
			}
			
		}catch(SQLException e) {
			throw new DBConnectionException("DB connection error");
		}

		return eS;
	}
	
	public static void createStudent(EntityStudente eS) throws DAOException, DBConnectionException {
        try {
            Connection conn = DBManager.getConnection();

            String query = "INSERT INTO student VALUES (?, ?, ?, ?);";

            try {
                PreparedStatement stmt = conn.prepareStatement(query);

                stmt.setString(1, eS.getUsername());
                stmt.setString(2, eS.getPassword());
                stmt.setInt(3, eS.getPIN());
                stmt.setInt(4, eS.getidCDS());

                stmt.executeUpdate();

            } catch (SQLException e) {
                throw new DAOException("Student writing error");
            } finally {
                DBManager.closeConnection();
            }

        } catch (SQLException e) {
            throw new DBConnectionException("DB connection error");
        }
    }
	
}

