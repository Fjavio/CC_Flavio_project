package conservatory.control;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;

import conservatory.database.CorsoDAO;
import conservatory.database.DBManager;
import conservatory.database.DocenteDAO;
import conservatory.database.EsameDAO;
import conservatory.database.StudenteDAO;
import conservatory.database.VerbaleDAO;
import conservatory.entity.EntityCorso;
import conservatory.entity.EntityDocente;
import conservatory.entity.EntityEsame;
import conservatory.entity.EntityStudente;
import conservatory.entity.EntityVerbale;
import conservatory.exception.DAOException;
import conservatory.exception.DBConnectionException;
import conservatory.exception.OperationException;
import conservatory.exception.PropedeuticitaException;

public class GestoreCorsiDiStudioConservatorio {
	
	public GestoreCorsiDiStudioConservatorio() {
		
	}
  
	public void AssociationTeacherCourse(String courseCode, String teacherID) throws OperationException{
		EntityDocente eD = null;
		EntityCorso eC = null;
		
		try {
			eD = DocenteDAO.readTeacher(teacherID);
			eC = CorsoDAO.readCourse(courseCode);
			if(eD == null || eC == null) {
				throw new OperationException("Teacher or Course not found");
			}
			eC.teacherID=CorsoDAO.readAssociationTeacherCourse(courseCode);
			
		   if(eC.teacherID != null) {
				throw new OperationException("Course already assigned");
			}
		   CorsoDAO.updateAssociationTeacherCourse(courseCode, teacherID);
		}catch(DBConnectionException dbEx) {
			throw new OperationException("\nInternal application problem encountered!\n");
		}catch(DAOException ex) {
			throw new OperationException("Oops, something went wrong...");
		}
	}
		
	public void createAndInsertExam(int vote, boolean honors, String teacherNotes, String reportCode, String courseCode, String username) throws OperationException {
		try {
		EntityEsame exam = new EntityEsame(vote, honors, teacherNotes, null, reportCode, courseCode, username);
        EsameDAO.createExam(exam);
		}catch(DBConnectionException dbEx) {
	        throw new OperationException("\nInternal application problem encountered!\n");
	    } catch(DAOException ex) {
	        throw new OperationException("Oops, something went wrong...");
	    }
    }
	
	public void createAndInsertCourse(String courseCode, String courseName, int CFU, String preOf, String preFor) throws OperationException {
	    try {
	        EntityCorso course = new EntityCorso(courseCode, courseName, CFU, null, preOf, preFor);
	        CorsoDAO.createCourse(course);
	    } catch (DBConnectionException dbEx) {
	        throw new OperationException("\nInternal application problem encountered!\n");
	    } catch (DAOException ex) {
	        throw new OperationException("Oops, something went wrong...");
	    }
	}

	public void createAndInsertTeacher(String teacherName, String teacherSurname, String ID) throws OperationException {
		try {
		EntityDocente teacher = new EntityDocente(teacherName, teacherSurname, ID);
        DocenteDAO.createTeacher(teacher);
		}catch(DBConnectionException dbEx) {
	        throw new OperationException("\nInternal application problem encountered!\n");
	    } catch(DAOException ex) {
	        throw new OperationException("Oops, something went wrong...");
	    }
    }
	
	public void OpeningReport(String reportCode, Date reportDate, String ID) throws OperationException {
	    try {
	        EntityVerbale eB = new EntityVerbale(reportDate, reportCode, ID);
	        VerbaleDAO.createReport(eB);
	    } catch(DBConnectionException dbEx) {
	        throw new OperationException("\nInternal application problem encountered!\n");
	    } catch(DAOException ex) {
	        throw new OperationException("Oops, something went wrong...");
	    }
	}
	
	public void checkReport(String reportCode) throws OperationException {    
		EntityVerbale eV = null;
		try {
		    eV = VerbaleDAO.readReport(reportCode);
		
		    if(eV == null) {
			   throw new OperationException("Report not opened");
		    }
		    
		    List<EntityEsame> exams = EsameDAO.readExam(reportCode);
            for (EntityEsame exam : exams) {
               if (exam.getpassingDate() != null) {
                  throw new OperationException("Report already closed");
               }
            }
		}catch(DBConnectionException dbEx) {
			throw new OperationException("\nInternal application problem encountered!\n");
		}catch(DAOException ex) {
			throw new OperationException("Oops, something went wrong...");
		}
	}
	
	public boolean checkStudent(String username) throws OperationException {
		EntityStudente eS = null;
	    try {
	    	eS = StudenteDAO.readStudent(username);
	        if(eS == null) {
				   return false;
			    }
	       return true;
	    } catch(DBConnectionException dbEx) {
			throw new OperationException("\nInternal application problem encountered!\n");
		}catch(DAOException ex) {
			throw new OperationException("Oops, something went wrong...");
		}
	}
	
	public boolean checkCourse(String courseCode) throws OperationException {
		EntityCorso eC = null;
	    try {
	    	eC = CorsoDAO.readCourse(courseCode);
	        if(eC == null) {
				   return false;
			    }
	       return true;
	    } catch(DBConnectionException dbEx) {
			throw new OperationException("\nInternal application problem encountered!\n");
		}catch(DAOException ex) {
			throw new OperationException("Oops, something went wrong...");
		}
	}
	
	public boolean checkTeacher(String ID) throws OperationException {
		EntityDocente eD = null;
	    try {
	    	eD = DocenteDAO.readTeacher(ID);
	        if(eD == null) {
				   return false;
			    }
	       return true;
	    } catch(DBConnectionException dbEx) {
			throw new OperationException("\nInternal application problem encountered!\n");
		}catch(DAOException ex) {
			throw new OperationException("Oops, something went wrong...");
		}
	}
	
	public List<String> getUsernamesByReport(String reportCode) throws OperationException {
	    try {
	        return EsameDAO.getUsernamesByReport(reportCode);
	    } catch(DBConnectionException dbEx) {
			throw new OperationException("\nInternal application problem encountered!\n");
		}catch(DAOException ex) {
			throw new OperationException("Oops, something went wrong...");
		}
	}
	
	public void checkPIN(int insertedPin, String reportCode, String username) throws OperationException {
	    try {
	        EsameDAO.checkPIN(insertedPin, reportCode, username);
	    } catch(DBConnectionException dbEx) {
			throw new OperationException("\nInternal application problem encountered!\n");
		}catch(DAOException ex) {
			throw new OperationException("Oops, something went wrong...");
		}
	}

	public void ClosingReport1(String reportCode, String username) throws OperationException{
        try {
           EsameDAO.checkPrerequisites(reportCode, username);
        } catch (PropedeuticitaException pEx) {
	    System.out.println(pEx.getMessage());
	    try {
			EsameDAO.deleteExam(reportCode, username);
		} catch (DAOException e) {
			throw new OperationException("Oops, something went wrong...");
		} catch (DBConnectionException e) {
			throw new OperationException("\nInternal application problem encountered!\n");
		}
	    }
        catch(DBConnectionException dbEx) {
			throw new OperationException("\nInternal application problem encountered!\n");
		}catch(DAOException ex) {
			throw new OperationException("Oops, something went wrong...");
		}
    }
	
    public void ClosingReport2(String reportCode) throws OperationException{
            try {
               EsameDAO.checkVotes(reportCode);
            } catch(DBConnectionException dbEx) {
    			throw new OperationException("\nInternal application problem encountered!\n");
    		}catch(DAOException ex) {
    			throw new OperationException("Oops, something went wrong...");
    		}
    }

}
