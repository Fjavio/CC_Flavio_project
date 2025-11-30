package conservatory.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import conservatory.entity.EntityVerbale;
import conservatory.exception.DAOException;

@Repository //Bean of Spring
public class VerbaleDAO { //report
	
    //Spring will inject Connection Pool here
    private final DataSource dataSource;

    //The constructor receives the DataSource (Dependency Injection)
    @Autowired
    public VerbaleDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }
		
    public EntityVerbale readReport(String reportCode) throws DAOException {
    	
        EntityVerbale eV = null;
        String query = "SELECT * FROM report WHERE reportCode = ?;";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, reportCode);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                eV = new EntityVerbale(
                    reportCode, 
                    result.getDate("reportDate"), 
                    result.getString("teacherID")
                );	
            }
        } catch (SQLException e) {
            throw new DAOException("DB error during report reading: " + e.getMessage());
        }
        return eV;
    }
		
    public void createReport(EntityVerbale eB) throws DAOException {
       
        String query = "INSERT INTO report VALUES (?, ?, ?);";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
           
            stmt.setString(1, eB.getreportCode());
            stmt.setDate(2, new Date(eB.getreportDate().getTime()));
            stmt.setString(3, eB.getteacherID());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("DB error during report writing: " + e.getMessage());
        }
    }         
}
