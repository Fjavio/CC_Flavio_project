package conservatory.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource; //for spring

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import conservatory.entity.EntityDocente;
import conservatory.exception.DAOException;

@Repository //Bean of spring
public class DocenteDAO {
	
	//Spring will inject Connection Pool here: H2 in testing, MySQL in production
    private final DataSource dataSource;

    //The constructor receives the DataSource (Dependency Injection)
    @Autowired
    public DocenteDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public void createTeacher(EntityDocente eD) throws DAOException {
        
        String query = "INSERT INTO teacher VALUES (?, ?, ?);";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, eD.getTeacherID());
            stmt.setString(2, eD.getTeacherName()); 
            stmt.setString(3, eD.getTeacherSurname());

            stmt.executeUpdate();

        } catch (SQLException e) {
            //throw new DAOException("Errore DB durante la scrittura del docente: " + e.getMessage());
            throw new DAOException("Teacher writing error");
        }
    }
	
    public EntityDocente readTeacher(String teacherID) throws DAOException {
        EntityDocente eD = null;
        String query = "SELECT * FROM teacher WHERE teacherID = ?;";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, teacherID);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                eD = new EntityDocente(
                    result.getString("teacherName"), 
                    result.getString("teacherSurname"), 
                    teacherID
                );
            }
        } catch (SQLException e) {
            //throw new DAOException("Errore DB durante la lettura del docente: " + e.getMessage());
            throw new DAOException("Teacher reading error");
        }
        return eD;
    }
}
