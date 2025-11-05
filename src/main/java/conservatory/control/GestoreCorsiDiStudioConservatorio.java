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
  
	public void AssociationTeacherCourse(String courseCode, String teacherID) throws OperationException, IllegalArgumentException {
		
		EntityDocente eD = null;
		EntityCorso eC = null;
		
        //NEW: Format validation (safeguard clauses because when API we can bypass boundary)
        if (courseCode == null || courseCode.length() != 5) {
            throw new IllegalArgumentException("Course code must be 5 characters long");
        }
        if (teacherID == null || teacherID.length() != 7) {
            throw new IllegalArgumentException("Teacher ID must be 7 characters long");
        }

        try {
            eD = DocenteDAO.readTeacher(teacherID);
            eC = CorsoDAO.readCourse(courseCode);

            if (eD == null || eC == null) {
                throw new OperationException("Teacher or Course not found");
            }
            if (CorsoDAO.readAssociationTeacherCourse(courseCode) != null) {
                throw new OperationException("Course has already been assigned to a teacher");
            }

            CorsoDAO.updateAssociationTeacherCourse(courseCode, teacherID);
        } catch(DBConnectionException dbEx) {
			throw new OperationException("\nInternal application problem encountered!\n");
		}catch(DAOException ex) {
			throw new OperationException("Oops, something went wrong...");
		}
    }
	
	public void createAndInsertExam(int vote, boolean honors, String teacherNotes, String reportCode, String courseCode, String username) 
            throws OperationException, IllegalArgumentException {
        
        if (vote < 18 || vote > 30) {
            throw new IllegalArgumentException("Vote must be between 18 and 30");
        }
        if (honors && vote != 30) {
            throw new IllegalArgumentException("Honors can only be assigned with a vote of 30");
        }
        if (reportCode == null || reportCode.length() != 5) {
            throw new IllegalArgumentException("Report code must be 5 characters long");
        }
        if (courseCode == null || courseCode.length() != 5) {
            throw new IllegalArgumentException("Course code must be 5 characters long");
        }
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        
		try {
		    EntityEsame exam = new EntityEsame(vote, honors, teacherNotes, null, reportCode, courseCode, username);
            EsameDAO.createExam(exam);
		} catch(DBConnectionException dbEx) {
	        throw new OperationException("\nInternal application problem encountered!\n");
	    } catch(DAOException ex) {
	        throw new OperationException("Oops, something went wrong...");
	    }
    }
	
	public void createAndInsertCourse(String courseCode, String courseName, int CFU, String preOf, String preFor) 
            throws OperationException, IllegalArgumentException {
        
        if (courseCode == null || courseCode.length() != 5) {
            throw new IllegalArgumentException("Course code must be 5 characters long");
        }
        if (courseName == null || courseName.trim().isEmpty()) {
            throw new IllegalArgumentException("Course name cannot be empty");
        }
        if (CFU <= 0) {
            throw new IllegalArgumentException("CFU must be a positive number");
        }

	    try {
	        EntityCorso course = new EntityCorso(courseCode, courseName, CFU, null, preOf, preFor);
	        CorsoDAO.createCourse(course);
	    } catch (DBConnectionException dbEx) {
	        throw new OperationException("\nInternal application problem encountered!\n");
	    } catch (DAOException ex) {
	        throw new OperationException("Oops, something went wrong...");
	    }
	}
	
	public void createAndInsertTeacher(String teacherName, String teacherSurname, String ID) 
            throws OperationException, IllegalArgumentException {
        
        if (ID == null || ID.length() != 7) {
            throw new IllegalArgumentException("Teacher ID must be 7 characters long");
        }
        if (teacherName == null || teacherName.trim().isEmpty()) {
            throw new IllegalArgumentException("Teacher name cannot be empty");
        }

		try {
		    EntityDocente teacher = new EntityDocente(teacherName, teacherSurname, ID);
            DocenteDAO.createTeacher(teacher);
		} catch(DBConnectionException dbEx) {
	        throw new OperationException("\nInternal application problem encountered!\n");
	    } catch(DAOException ex) {
	        throw new OperationException("Oops, something went wrong...");
	    }
    }
	
	public void OpeningReport(String reportCode, Date reportDate, String ID) throws OperationException, IllegalArgumentException {
        
        if (reportCode == null || reportCode.length() != 5) {
            throw new IllegalArgumentException("Report code must be 5 characters long");
        }
        if (ID == null || ID.length() != 7) {
            throw new IllegalArgumentException("Teacher ID must be 7 characters long");
        }
        if (reportDate == null) {
            throw new IllegalArgumentException("Report date cannot be null");
        }

	    try {
	        EntityVerbale eB = new EntityVerbale(reportDate, reportCode, ID);
	        VerbaleDAO.createReport(eB);
	    } catch(DBConnectionException dbEx) {
	        throw new OperationException("\nInternal application problem encountered!\n");
	    } catch(DAOException ex) {
	        throw new OperationException("Oops, something went wrong...");
	    }
	}
	
	public void checkReport(String reportCode) throws OperationException, IllegalArgumentException {    
       
		EntityVerbale eV = null;
		
        if (reportCode == null || reportCode.length() != 5) {
            throw new IllegalArgumentException("Report code must be 5 characters long.");
        }
        
		try {
		    eV = VerbaleDAO.readReport(reportCode);
		
		    if(eV == null) {
			   throw new OperationException("Report not found or not yet opened.");
		    }
		    
		    List<EntityEsame> exams = EsameDAO.readExam(reportCode);
            for (EntityEsame exam : exams) {
               if (exam.getpassingDate() != null) {
                  throw new OperationException("Report is already closed and finalized.");
               }
            }
		} catch(DBConnectionException dbEx) {
			throw new OperationException("\nInternal application problem encountered!\n");
		}catch(DAOException ex) {
			throw new OperationException("Oops, something went wrong...");
		}
	}
	
	public boolean checkStudent(String username) throws OperationException, IllegalArgumentException {
        
		EntityStudente eS = null;
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
		
        
	    try {
	    	eS = StudenteDAO.readStudent(username);
	        return (eS != null);
	    } catch(DBConnectionException dbEx) {
			throw new OperationException("\nInternal application problem encountered!\n");
		}catch(DAOException ex) {
			throw new OperationException("Oops, something went wrong...");
		}
	}
	
	public boolean checkCourse(String courseCode) throws OperationException, IllegalArgumentException {
        
		EntityCorso eC = null;
        if (courseCode == null || courseCode.length() != 5) {
            throw new IllegalArgumentException("Course code must be 5 characters long");
        }
        
	    try {
	    	eC = CorsoDAO.readCourse(courseCode);
	        return (eC != null);
	    } catch(DBConnectionException dbEx) {
			throw new OperationException("\nInternal application problem encountered!\n");
		}catch(DAOException ex) {
			throw new OperationException("Oops, something went wrong...");
		}
	}
	
	public boolean checkTeacher(String ID) throws OperationException, IllegalArgumentException {
       
		EntityDocente eD = null;
        if (ID == null || ID.length() != 7) {
            throw new IllegalArgumentException("Teacher ID must be 7 characters long");
        }
        
	    try {
	    	eD = DocenteDAO.readTeacher(ID);
	        return (eD != null);
	    } catch(DBConnectionException dbEx) {
			throw new OperationException("\nInternal application problem encountered!\n");
		}catch(DAOException ex) {
			throw new OperationException("Oops, something went wrong...");
		}
	}
	
	public List<String> getUsernamesByReport(String reportCode) throws OperationException, IllegalArgumentException {
        
        if (reportCode == null || reportCode.length() != 5) {
            throw new IllegalArgumentException("Report code must be 5 characters long");
        }
        
	    try {
	        return EsameDAO.getUsernamesByReport(reportCode);
	    } catch(DBConnectionException dbEx) {
			throw new OperationException("\nInternal application problem encountered!\n");
		}catch(DAOException ex) {
			throw new OperationException("Oops, something went wrong...");
		}
	}
	
	public void checkPIN(int insertedPin, String reportCode, String username) throws OperationException, IllegalArgumentException {
        
        if (reportCode == null || reportCode.length() != 5) {
            throw new IllegalArgumentException("Report code must be 5 characters long");
        }
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (String.valueOf(insertedPin).length() != 7) {
            throw new IllegalArgumentException("PIN must be 7 digits long");
        }
        
	    try {
	        EsameDAO.checkPIN(insertedPin, reportCode, username);
	    } catch(DBConnectionException dbEx) {
			throw new OperationException("\nInternal application problem encountered!\n");
		}catch(DAOException ex) {
			throw new OperationException("Oops, something went wrong...");
		}
	}
	
	public void ClosingReport1(String reportCode, String username) throws OperationException, IllegalArgumentException {
        
        if (reportCode == null || reportCode.length() != 5) {
            throw new IllegalArgumentException("Report code must be 5 characters long");
        }
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }

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
    
    public void ClosingReport2(String reportCode) throws OperationException, IllegalArgumentException {
        
        if (reportCode == null || reportCode.length() != 5) {
            throw new IllegalArgumentException("Report code must be 5 characters long");
        }

        try {
           EsameDAO.checkVotes(reportCode);
        } catch(DBConnectionException dbEx) {
			throw new OperationException("\nInternal application problem encountered!\n");
		}catch(DAOException ex) {
			throw new OperationException("Oops, something went wrong...");
		}
    }

}
