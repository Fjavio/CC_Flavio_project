package conservatory.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource; //Spring DataSource

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import conservatory.entity.EntityCorso;
import conservatory.exception.DAOException;

@Repository //Bean ("Repository") of Spring
public class CorsoDAO {
	
	//Spring will inject Connection Pool here: H2 in testing, MySQL in production
    private final DataSource dataSource;

    //The constructor receives the DataSource (Dependency Injection)
    @Autowired
    public CorsoDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

	public EntityCorso readCourse(String courseCode) throws DAOException {

		EntityCorso eC = null;
		String query = "SELECT * FROM course WHERE courseCode = ?;";

		try (Connection conn = dataSource.getConnection();
	            PreparedStatement stmt = conn.prepareStatement(query)) {

	            stmt.setString(1, courseCode);
	            ResultSet result = stmt.executeQuery();

	            if (result.next()) {
	                eC = new EntityCorso(
	                    courseCode, 
	                    result.getString("courseName"), 
	                    result.getInt("cfu"), 
	                    result.getString("teacherID"), 
	                    result.getString("preOf"), 
	                    result.getString("preFor")
	                );
	            }
	        } catch (SQLException e) {
	            //throw new DAOException("Errore DB durante la lettura del corso: " + e.getMessage());
	            throw new DAOException("Course reading error");
	        }
	      return eC;
	}
	
	public void createCourse(EntityCorso eC) throws DAOException {
	 
	        String query = "INSERT INTO course VALUES (?, ?, ?, ?, ?, ?);";
	        try (Connection conn = dataSource.getConnection();
	                PreparedStatement stmt = conn.prepareStatement(query)) {

	               stmt.setString(1, eC.getcourseCode());
	               stmt.setString(2, eC.getcourseName());
	               stmt.setInt(3, eC.getCfu());
	               stmt.setString(4, null);
	               stmt.setString(5, eC.getpreOf());
	               stmt.setString(6, eC.getpreFor());

	               stmt.executeUpdate();
	           } catch (SQLException e) {
	               //throw new DAOException("Errore DB durante la scrittura del corso: " + e.getMessage());
	               throw new DAOException("Course writing error");
	           }
            
	}

	public String preOf(String courseCode) throws DAOException {
        String preOf = null;
        String query = "SELECT preOf FROM course WHERE courseCode = ?;";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, courseCode);
            ResultSet result = stmt.executeQuery();
            
            if (result.next()) {
                preOf = result.getString("preOf");
            }
        } catch (SQLException e) {
            //throw new DAOException("Errore DB recupero corsi propedeutici: " + e.getMessage());
            throw new DAOException("Error while retrieving preparatory courses for the exam");
        }
        return preOf;
    }
	
	public String readAssociationTeacherCourse(String courseCode) throws DAOException {
        String teacherID = null;
        String query = "SELECT teacherID FROM course WHERE courseCode = ?;";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, courseCode);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                teacherID = result.getString("teacherID");
            }
        } catch (SQLException e) {
            //throw new DAOException("Errore DB lettura associazione docente-corso: " + e.getMessage());
            throw new DAOException("Error reading teacher-course association");
        }
        return teacherID;
    }
	
	public void updateAssociationTeacherCourse(String courseCode, String teacherID) throws DAOException {
        String query = "UPDATE course SET teacherID = ? WHERE courseCode = ?;";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, teacherID);
            stmt.setString(2, courseCode);
            stmt.executeUpdate();
        } catch (SQLException e) {
            //throw new DAOException("Errore DB aggiornamento associazione docente-corso: " + e.getMessage());
            throw new DAOException("Error updating course association to teacher");
        }
    }

}
 
            
                
        
