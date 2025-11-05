package conservatory.database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conservatory.entity.EntityCorso;
import conservatory.exception.DAOException;
import conservatory.exception.DBConnectionException;

public class CorsoDAO {
	public static EntityCorso readCourse(String courseCode) throws DAOException, DBConnectionException {

		EntityCorso eC = null;

		try {

			Connection conn = DBManager.getConnection();
			String query = "SELECT * FROM course WHERE courseCode=?;";
			
			try {

				PreparedStatement stmt = conn.prepareStatement(query);

				stmt.setString(1, courseCode);

				ResultSet result = stmt.executeQuery();

				if(result.next()) {
					eC = new EntityCorso(courseCode, result.getString(2), result.getInt(3), result.getString(4),result.getString(5),result.getString(6));
				}

			}catch(SQLException e) {
				throw new DAOException("Course reading error");
			}finally {
				DBManager.closeConnection();
			}
			
		}catch(SQLException e) {
			throw new DBConnectionException("DB connection error");
		}

		return eC;
	}
	
	public static void createCourse(EntityCorso eC) throws DAOException, DBConnectionException {
	    try {
	        Connection conn = DBManager.getConnection();

	        String query = "INSERT INTO course VALUES (?, ?, ?, ?, ?, ?);";
			
	        try {
	            PreparedStatement stmt = conn.prepareStatement(query);

	            stmt.setString(1, eC.getcourseCode());
                stmt.setString(2, eC.getcourseName());
                stmt.setInt(3, eC.getCFU());
                stmt.setString(4, null);
                stmt.setString(5, eC.getpreOf());
                stmt.setString(6, eC.getpreFor());

	            stmt.executeUpdate();

	        } catch (SQLException e) {
	            throw new DAOException("Course writing error");
	        } finally {
	            DBManager.closeConnection();
	        }

	    } catch (SQLException e) {
	        throw new DBConnectionException("DB connection error");
	    }
	}

	public static String preOf(Connection conn, String courseCode) throws DAOException, DBConnectionException {
	    String preOf = null;

	    //try (Connection conn = DBManager.getConnection()) {
	        String query = "SELECT preOf FROM course WHERE courseCode = ?;";
	        try (PreparedStatement stmt = conn.prepareStatement(query)) {
	            stmt.setString(1, courseCode);
	            ResultSet result = stmt.executeQuery();
	            if (result.next()) {
	                preOf = result.getString("preOf"); 
	                System.out.println(preOf);
	            }
	        } catch (SQLException e) {
	            throw new DAOException("Error while retrieving preparatory courses for the exam");
	        }
	   /* } catch (SQLException e) {
	        throw new DBConnectionException("DB connection error");
	    }*/

	    return preOf;
	}

public static String readAssociationTeacherCourse(String courseCode) throws DAOException, DBConnectionException {
        String teacherID = null;

        try {
            Connection conn = DBManager.getConnection();
            String query = "SELECT teacherID FROM course WHERE courseCode = ?;";
            try {
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, courseCode);

                ResultSet result = stmt.executeQuery();

                if (result.next()) {
                    teacherID = result.getString("teacherID");
                }
            } catch (SQLException e) {
                throw new DAOException("Error reading teacher-course association");
            } finally {
                DBManager.closeConnection();
            }
        } catch (SQLException e) {
            throw new DBConnectionException("DB connection error");
        }

        return teacherID;
    } 
    
public static void updateAssociationTeacherCourse(String courseCode, String teacherID) throws DAOException, DBConnectionException {
    try {
        Connection conn = DBManager.getConnection();
        String query = "UPDATE course SET teacherID = ? WHERE courseCode = ?;";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, teacherID);
            stmt.setString(2, courseCode);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error updating course association to teacher");
        } finally {
            DBManager.closeConnection();
        }
    } catch (SQLException e) {
        throw new DBConnectionException("DB connection error");
    }
  }
}
 
            
                
        
