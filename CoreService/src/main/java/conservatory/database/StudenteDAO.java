package conservatory.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import conservatory.entity.EntityStudente;
import conservatory.exception.DAOException;

@Repository //Bean of Spring
public class StudenteDAO {

    //Spring will inject Connection Pool here
    private final DataSource dataSource;

    //The constructor receives the DataSource (Dependency Injection)
    @Autowired
    public StudenteDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public EntityStudente readStudent(String username) throws DAOException {
    	
        EntityStudente eS = null;
        String query = "SELECT * FROM student WHERE username = ?;";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                eS = new EntityStudente(
                    username, 
                    result.getString("password"), 
                    result.getInt("pin"), 
                    result.getInt("idCDS")
                );	
            }
        } catch (SQLException e) {
            //throw new DAOException("Errore DB during la lettura dello studente: " + e.getMessage());
            throw new DAOException("Student reading error");
        }
        return eS;
    }

    public void createStudent(EntityStudente eS) throws DAOException {
       
        String query = "INSERT INTO student VALUES (?, ?, ?, ?);";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, eS.getUsername());
            stmt.setString(2, eS.getPassword());
            stmt.setInt(3, eS.getPin());
            stmt.setInt(4, eS.getidCDS());

            stmt.executeUpdate();

        } catch (SQLException e) {
            //throw new DAOException("Errore DB during la scrittura dello studente: " + e.getMessage());
            throw new DAOException("Student writing error");
        }
    }
}					
               
            