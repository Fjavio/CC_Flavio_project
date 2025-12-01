package conservatory.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import conservatory.entity.EntityEsame;
import conservatory.entity.EntityStudente;
import conservatory.entity.EntityVerbale;
import conservatory.exception.DAOException;
import conservatory.exception.PropedeuticitaException;

@Repository //Bean of spring
public class EsameDAO {
	
	//Inject Connection Pool
    private final DataSource dataSource;

    //Inject other DAOs on which it depends
    private final StudenteDAO studentDAO;
    private final CorsoDAO courseDAO;
    private final VerbaleDAO reportDAO;

    //The constructor receives all dependencies
    @Autowired
    public EsameDAO(DataSource dataSource, StudenteDAO studentDAO, CorsoDAO courseDAO, VerbaleDAO reportDAO) {
        this.dataSource = dataSource;
        this.studentDAO = studentDAO;
        this.courseDAO = courseDAO;
        this.reportDAO = reportDAO;
    }
    
    public void createExam(EntityEsame eE) throws DAOException {
        String query = "INSERT INTO exam VALUES (?, ?, ?, ?, ?, ?, ?);";
		
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, eE.getvote());
            stmt.setBoolean(2, eE.ishonors());
            stmt.setString(3, eE.getteacherNotes());
            stmt.setDate(4, null);
            stmt.setString(5, eE.getreportCode());
            stmt.setString(6, eE.getcourseCode());
            stmt.setString(7, eE.getUsername());

            stmt.executeUpdate();

        } catch (SQLException e) {
            //throw new DAOException("Errore DB durante la scrittura dell'esame: " + e.getMessage());
            throw new DAOException("Exam writing error");
        }
    }

    public List<EntityEsame> readExam(String reportCode) throws DAOException {
        List<EntityEsame> exams = new ArrayList<>();
        String query = "SELECT * FROM exam WHERE reportCode = ?;";
		
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, reportCode);
            ResultSet result = stmt.executeQuery();

            while(result.next()) {
                EntityEsame exam = new EntityEsame(
                    result.getInt("vote"), 
                    result.getBoolean("honors"), 
                    result.getString("teacherNotes"), 
                    result.getDate("passingDate"), 
                    reportCode, 
                    result.getString("courseCode"), 
                    result.getString("username")
                );
                exams.add(exam);
            }
        } catch(SQLException e) {
            //throw new DAOException("Errore DB durante la lettura degli esami: " + e.getMessage());
            throw new DAOException("Exam reading error");
        }
        return exams;
    }
    
    public List<EntityEsame> readPassedExams(String username) throws DAOException {
        List<EntityEsame> exams = new ArrayList<>();
        String query = "SELECT * FROM exam WHERE username = ? AND passingDate IS NOT NULL;";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
	
            stmt.setString(1, username);
            ResultSet result = stmt.executeQuery();

            while(result.next()) {
                EntityEsame exam = new EntityEsame(
                    result.getInt("vote"), 
                    result.getBoolean("honors"), 
                    result.getString("teacherNotes"), 
                    result.getDate("passingDate"), 
                    result.getString("reportCode"), 
                    result.getString("courseCode"), 
                    username
                );
                exams.add(exam);
            }
        } catch(SQLException e) {
            //throw new DAOException("Errore DB lettura esami superati: " + e.getMessage());
            throw new DAOException("Error reading student exams");
        }
        return exams;
    }

    public List<String> getUsernamesByReport(String reportCode) throws DAOException {
        List<String> usernames = new ArrayList<>();
        String query = "SELECT username FROM exam WHERE reportCode = ?;";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, reportCode);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                usernames.add(rs.getString("username"));
            }
        } catch (SQLException e) {
            //throw new DAOException("Errore DB recupero username da verbale: " + e.getMessage());
            throw new DAOException("Error retrieving usernames");
        }
        return usernames;
    }
    
    public void checkVotes(String reportCode) throws DAOException {
        
        Connection conn = null;
        
        //Transaction
        try {  
            EntityVerbale report = reportDAO.readReport(reportCode);
            if (report == null) {
                throw new DAOException("Impossible find report " + reportCode + " to validate votes");
            }
          
            String querySelect = "SELECT * FROM exam WHERE reportCode = ? AND passingDate IS NULL;";
            String queryDelete = "DELETE FROM exam WHERE reportCode = ? AND username = ?;";
            String queryUpdate = "UPDATE exam SET passingDate = ? WHERE reportCode = ? AND passingDate IS NULL;";

            conn = dataSource.getConnection();
            
            //Disable auto-commit for the transaction
            conn.setAutoCommit(false); 
            
            try (PreparedStatement stmtSelect = conn.prepareStatement(querySelect)) {
                stmtSelect.setString(1, reportCode); 
                ResultSet result = stmtSelect.executeQuery();        
                
                while(result.next()) {     
                    int vote = result.getInt("vote");            
                    boolean honors = result.getBoolean("honors");   
                    String username = result.getString("username");    
                    
                    if (vote < 18 || (honors && vote != 30) || vote > 30) {
                        try (PreparedStatement stmtDelete = conn.prepareStatement(queryDelete)) {
                            stmtDelete.setString(1, reportCode);                   
                            stmtDelete.setString(2, username); 
                            stmtDelete.executeUpdate();
                        }
                    }  
                } 
            }
            
            //Now publish all remaining valid exams
            java.util.Date dateReportUtil = report.getreportDate();
            Date dateReportSql = new Date(dateReportUtil.getTime());
        
            try (PreparedStatement stmtUpdate = conn.prepareStatement(queryUpdate)) {
                stmtUpdate.setDate(1, dateReportSql); 
                stmtUpdate.setString(2, reportCode);
                stmtUpdate.executeUpdate();
            }
            
            //Finalize the transaction
            conn.commit(); 

        } catch (SQLException e) {
            try {
            	//If something goes wrong, undo all changes
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException rollbackEx) {
                throw new DAOException("Error during transaction rollback: " + rollbackEx.getMessage());
            }
            throw new DAOException("Error while updating exams: " + e.getMessage());
            
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); //Reset auto-commit
                    conn.close(); //Returns the connection to the pool
                } catch (SQLException closeEx) {
                    System.err.println("Error while closing connection: " + closeEx.getMessage());
                }
            }
        }
    }
    
    public void checkPrerequisites(String reportCode, String username) throws DAOException, PropedeuticitaException {
    	
        try (Connection conn = dataSource.getConnection()) {
            String querySelect = "SELECT courseCode FROM exam WHERE reportCode = ? AND username = ?;";
        
            PreparedStatement stmtSelect = conn.prepareStatement(querySelect);
            stmtSelect.setString(1, reportCode);
            stmtSelect.setString(2, username);
            ResultSet result = stmtSelect.executeQuery();

            if (result.next()) {
                String courseCode = result.getString("courseCode");
                
                List<EntityEsame> passedExams = this.readPassedExams(username); 
                
                String preOfString = courseDAO.preOf(courseCode);
                
                if (preOfString == null || preOfString.trim().isEmpty()) {
                    return; // No prerequisites
                }
                
                boolean allPreparatoryPresent = true;
                String[] coursesCodes = preOfString.split(" ");
              
                for (String code : coursesCodes) {
                    boolean find = false;
                    for (EntityEsame passedExam : passedExams) {
                        if (passedExam.getcourseCode().equals(code)) {
                            find = true;
                            break;
                        }
                    }
                    if (!find) {
                        allPreparatoryPresent = false;
                        break;
                    }
                }

                if (!allPreparatoryPresent) {
                	throw new PropedeuticitaException("The prerequisites are not respected");
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error while checking exam prerequisites");
        }
    }

    public void checkPIN(int insertedPin, String reportCode, String username) throws DAOException {
        try {
           
    	    EntityStudente student = studentDAO.readStudent(username);
            if (student == null) {
                throw new DAOException("Impossible find student: " + username);
            }
            
            int studentPin = student.getPin();
            String querySelect = "SELECT * FROM exam WHERE username = ? AND reportCode = ?;";
            String queryDelete = "DELETE FROM exam WHERE username = ? AND reportCode = ?;";

            try (Connection conn = dataSource.getConnection()) {
                
                PreparedStatement stmtSelect = conn.prepareStatement(querySelect);
                stmtSelect.setString(1, username);
                stmtSelect.setString(2, reportCode);
                ResultSet rs = stmtSelect.executeQuery();
                
                if (rs.next()) {
                    if (studentPin != insertedPin) {
                        try (PreparedStatement stmtDelete = conn.prepareStatement(queryDelete)) {
                            stmtDelete.setString(1, username);
                            stmtDelete.setString(2, reportCode);
                            stmtDelete.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error checking PINs and deleting exams");
        }
    }
    
    public void deleteExam(String reportCode, String username) throws DAOException {
        String queryDelete = "DELETE FROM exam WHERE username = ? AND reportCode = ?;";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmtDelete = conn.prepareStatement(queryDelete)) {
            
            stmtDelete.setString(1, username);
            stmtDelete.setString(2, reportCode);
            stmtDelete.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error while deleting exam");
        }
    }

}