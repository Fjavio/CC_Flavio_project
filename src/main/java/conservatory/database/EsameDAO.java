package conservatory.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conservatory.entity.EntityEsame;
import conservatory.entity.EntityStudente;
import conservatory.entity.EntityVerbale;
import conservatory.exception.DAOException;
import conservatory.exception.DBConnectionException;
import conservatory.exception.PropedeuticitaException;

import java.sql.Date;

public class EsameDAO {
public static void createExam(EntityEsame eE) throws DAOException, DBConnectionException {
    try {
        Connection conn = DBManager.getConnection();

        String query = "INSERT INTO EXAM VALUES (?, ?, ?, ?, ?, ?, ?);";
		
        try {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, eE.getvote());
            stmt.setBoolean(2, eE.ishonors());
            stmt.setString(3, eE.getteacherNotes());
            stmt.setDate(4, null);
            stmt.setString(5, eE.getreportCode());
            stmt.setString(6, eE.getcourseCode());
            stmt.setString(7, eE.getUsername());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Exam writing error");
        } finally {
            DBManager.closeConnection();
        }

    } catch (SQLException e) {
        throw new DBConnectionException("DB connection error");
    }
}

public static List<EntityEsame> readExam(String reportCode) throws DAOException, DBConnectionException {

	List<EntityEsame> exams = new ArrayList<>();

	try {

		Connection conn = DBManager.getConnection();
		String query = "SELECT * FROM EXAM WHERE REPORTCODE=?;";
		
		try {

			PreparedStatement stmt = conn.prepareStatement(query);

			stmt.setString(1, reportCode);

			ResultSet result = stmt.executeQuery();

			while(result.next()) {
				EntityEsame exam = new EntityEsame(result.getInt(1), result.getBoolean(2), result.getString(3), result.getDate(4), reportCode, result.getString(6), result.getString(7));
				exams.add(exam);
			}

		}catch(SQLException e) {
			throw new DAOException("Exam reading error");
		}finally {
			DBManager.closeConnection();
		}
		
	}catch(SQLException e) {
		throw new DBConnectionException("DB connection error");
	}

	return exams;
}

public static List<EntityEsame> readPassedExams(Connection conn, String username) throws DAOException, DBConnectionException {

	List<EntityEsame> exams = new ArrayList<>();
	
	//try {

		//Connection conn = DBManager.getConnection();
		String query = "SELECT * FROM EXAM WHERE USERNAME=? AND PASSINGDATE IS NOT NULL;";

		try {
	
			PreparedStatement stmt = conn.prepareStatement(query);

			stmt.setString(1, username);

			ResultSet result = stmt.executeQuery();

			while(result.next()) {
				EntityEsame exam = new EntityEsame(result.getInt(1), result.getBoolean(2), result.getString(3), result.getDate(4), result.getString(5), result.getString(6), username);
				exams.add(exam);
			}

		}catch(SQLException e) {
			throw new DAOException("Error reading student exams");
		}/*finally {
			DBManager.closeConnection();
		}*/
		
	/*}catch(SQLException e) {
		throw new DBConnectionException("DB connection error");
	}*/

	return exams;
}

public static List<String> getUsernamesByReport(String reportCode) throws DAOException, DBConnectionException {
    List<String> usernames = new ArrayList<>();
    try {
        Connection conn = DBManager.getConnection();
        String query = "SELECT USERNAME FROM EXAM WHERE REPORTCODE = ?;";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, reportCode);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                usernames.add(rs.getString("username"));
            }
        } catch (SQLException e) {
            throw new DAOException("Error retrieving usernames");
        } finally {
            DBManager.closeConnection();
        }
    } catch (SQLException e) {
        throw new DBConnectionException("DB connection error");
    }
    return usernames;
}

public static void checkVotes(String reportCode) throws DAOException, DBConnectionException {     
	  try {  
		  EntityVerbale report = VerbaleDAO.readReport(reportCode);
		  Connection conn = DBManager.getConnection();  
		  //Retrieve all exams with null passing date and specified verbal code    
		  String querySelect = "SELECT * FROM EXAM WHERE REPORTCODE = ?;";   
		  try {
		  PreparedStatement stmtSelect = conn.prepareStatement(querySelect);     
		  stmtSelect.setString(1, reportCode); 
		  ResultSet result = stmtSelect.executeQuery();        
		  while(result.next()) {     
			  int vote = result.getInt(1);            
			  boolean honors = result.getBoolean(2);   
			  String username = result.getString(7);    
			  //Let's check the criteria for deleting exams      
			  if (vote < 18 || (honors && vote != 30) || vote>30) {        
				  String queryDelete = "DELETE FROM EXAM WHERE REPORTCODE = ? AND USERNAME = ?;";        
				  PreparedStatement stmtDelete = conn.prepareStatement(queryDelete);    
				  stmtDelete.setString(1, reportCode);                   
				  stmtDelete.setString(2, username); 
				  stmtDelete.executeUpdate();          
				  }  
			  } 
		    java.util.Date dateReportUtil = report.getreportDate();
		    Date dateReportSql = new Date(dateReportUtil.getTime());
		    String queryUpdate = "UPDATE EXAM SET PASSINGDATE = ? WHERE REPORTCODE = ?;";
		        PreparedStatement stmtUpdate = conn.prepareStatement(queryUpdate);
		        stmtUpdate.setDate(1, dateReportSql); 
		        stmtUpdate.setString(2, reportCode);
		        stmtUpdate.executeUpdate();
		  }  catch (SQLException e) {
		            throw new DAOException("Error while updating exams");
		        } finally {
		            DBManager.closeConnection();
		        }

		    } catch (SQLException e) {
		        throw new DBConnectionException("DB connection error");
		    }
}

public static void checkPrerequisites(String reportCode, String username) throws DAOException, DBConnectionException, PropedeuticitaException {
    try {
    	Connection conn = DBManager.getConnection();
        String querySelect = "SELECT * FROM EXAM WHERE REPORTCODE = ? AND USERNAME = ?;";
        try {
        	PreparedStatement stmtSelect = conn.prepareStatement(querySelect);
            stmtSelect.setString(1, reportCode);
            stmtSelect.setString(2, username);
            ResultSet result = stmtSelect.executeQuery();

            if(result.next()) {
                String courseCode = result.getString(6);
                
                List<EntityEsame> passedExams = readPassedExams(conn, username);
                
                String preOfString = CorsoDAO.preOf(conn, courseCode);
                
                //no preparatory exams
                if (preOfString == null || preOfString.trim().isEmpty()) {
                    return;
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
        } finally {
            DBManager.closeConnection();
        }
    } catch (SQLException e) {
        throw new DBConnectionException("DB connection error");
    }
}


public static void checkPIN(int insertedPin, String reportCode, String username) throws DAOException, DBConnectionException {
    try {
    	EntityStudente student = StudenteDAO.readStudent(username);
        int studentPin = student.getPIN();
        Connection conn = DBManager.getConnection();
        String querySelect = "SELECT * FROM EXAM WHERE USERNAME = ? AND REPORTCODE = ?;";
        try {
            PreparedStatement stmtSelect = conn.prepareStatement(querySelect);
            stmtSelect.setString(1, username);
            stmtSelect.setString(2, reportCode);
            ResultSet rs = stmtSelect.executeQuery();
            if (rs.next()) {
                if (studentPin != insertedPin) {
                    String queryDelete = "DELETE FROM EXAM WHERE USERNAME = ? AND REPORTCODE = ?;";
                    PreparedStatement stmtDelete = conn.prepareStatement(queryDelete);
                    stmtDelete.setString(1, username);
                    stmtDelete.setString(2, reportCode);
                    System.out.println("Student " + username + " 's exam eliminated due to incorrect PIN");
                    stmtDelete.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error checking PINs and deleting exams");
        } finally {
            DBManager.closeConnection();
        }
    } catch (SQLException e) {
        throw new DBConnectionException("DB connection error");
    }
}

public static void deleteExam(String reportCode, String username) throws DAOException, DBConnectionException {
    try {
        Connection conn = DBManager.getConnection();
        String queryDelete = "DELETE FROM EXAM WHERE USERNAME = ? AND REPORTCODE = ?;";
        try {
            PreparedStatement stmtDelete = conn.prepareStatement(queryDelete);
            stmtDelete.setString(1, username);
            stmtDelete.setString(2, reportCode);
            stmtDelete.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error while deleting exam");
        } finally {
            DBManager.closeConnection();
        }
    } catch (SQLException e) {
        throw new DBConnectionException("DB connection error");
    }
}

}