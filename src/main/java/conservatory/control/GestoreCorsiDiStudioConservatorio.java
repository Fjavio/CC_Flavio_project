package conservatory.control;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import conservatory.database.CorsoDAO;
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
import conservatory.exception.OperationException;
import conservatory.exception.PropedeuticitaException;

@Service //Bean of Spring
public class GestoreCorsiDiStudioConservatorio {
	
	private final CorsoDAO courseDAO;
    private final DocenteDAO teacherDAO;
    private final EsameDAO examDAO;
    private final StudenteDAO studentDAO;
    private final VerbaleDAO reportDAO;

    // Dependy injection
    @Autowired
    public GestoreCorsiDiStudioConservatorio(CorsoDAO courseDAO, DocenteDAO teacherDAO, 
                                            EsameDAO examDAO, StudenteDAO studentDAO, 
                                            VerbaleDAO reportDAO) {
        this.courseDAO = courseDAO;
        this.teacherDAO = teacherDAO;
        this.examDAO = examDAO;
        this.studentDAO = studentDAO;
        this.reportDAO = reportDAO;
    }
  
	public void AssociationTeacherCourse(String courseCode, String teacherID) throws OperationException, IllegalArgumentException {
		
		EntityDocente eD = null;
		EntityCorso eC = null;
		
        //NEW: Format validation (safeguard clauses because API calls directly control (no more boundary))
        if (courseCode == null || courseCode.length() != 5) {
            throw new IllegalArgumentException("Course code must be 5 characters long");
        }
        if (teacherID == null || teacherID.length() != 7) {
            throw new IllegalArgumentException("Teacher ID must be 7 characters long");
        }

        try {
            eD = teacherDAO.readTeacher(teacherID);
            eC = courseDAO.readCourse(courseCode);

            if (eD == null || eC == null) {
                throw new OperationException("Teacher or Course not found");
            }
            if (courseDAO.readAssociationTeacherCourse(courseCode) != null) {
                throw new OperationException("Course has already been assigned to a teacher");
            }

            courseDAO.updateAssociationTeacherCourse(courseCode, teacherID);
        } catch(DAOException ex) {
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
            examDAO.createExam(exam);
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
	        courseDAO.createCourse(course);
	    } catch (DAOException ex) {
	        throw new OperationException("Oops, something went wrong...");
	    }
	}
	
	public void createAndInsertTeacher(String ID, String teacherName, String teacherSurname) 
            throws OperationException, IllegalArgumentException {
        
        if (ID == null || ID.length() != 7) {
            throw new IllegalArgumentException("Teacher ID must be 7 characters long");
        }
        if (teacherName == null || teacherName.trim().isEmpty()) {
            throw new IllegalArgumentException("Teacher name cannot be empty");
        }

		try {
		    EntityDocente teacher = new EntityDocente(ID, teacherName, teacherSurname);
            teacherDAO.createTeacher(teacher);
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
	        EntityVerbale eB = new EntityVerbale(reportCode, reportDate, ID);
	        reportDAO.createReport(eB);
	    } catch(DAOException ex) {
	        //throw new OperationException("Oops, something went wrong...");
	    	throw new OperationException("Error while opening report: " + ex.getMessage());
	    }
	}
	
	public void checkReport(String reportCode) throws OperationException, IllegalArgumentException {    
       
		EntityVerbale eV = null;
		
        if (reportCode == null || reportCode.length() != 5) {
            throw new IllegalArgumentException("Report code must be 5 characters long.");
        }
        
		try {
		    eV = reportDAO.readReport(reportCode);
		
		    if(eV == null) {
			   throw new OperationException("Report not found or not yet opened.");
		    }
		    
		    List<EntityEsame> exams = examDAO.readExam(reportCode);
            for (EntityEsame exam : exams) {
               if (exam.getpassingDate() != null) {
                  throw new OperationException("Report is already closed and finalized.");
               }
            }
		} catch(DAOException ex) {
			throw new OperationException("Oops, something went wrong...");
		}
	}
	
	public boolean checkStudent(String username) throws OperationException, IllegalArgumentException {
        
		EntityStudente eS = null;
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
		
        
	    try {
	    	eS = studentDAO.readStudent(username);
	        return (eS != null);
	    } catch(DAOException ex) {
			throw new OperationException("Oops, something went wrong...");
		}
	}
	
	public boolean checkCourse(String courseCode) throws OperationException, IllegalArgumentException {
        
		EntityCorso eC = null;
        if (courseCode == null || courseCode.length() != 5) {
            throw new IllegalArgumentException("Course code must be 5 characters long");
        }
        
	    try {
	    	eC = courseDAO.readCourse(courseCode);
	        return (eC != null);
	    } catch(DAOException ex) {
			throw new OperationException("Oops, something went wrong...");
		}
	}
	
	public boolean checkTeacher(String ID) throws OperationException, IllegalArgumentException {
       
		EntityDocente eD = null;
        if (ID == null || ID.length() != 7) {
            throw new IllegalArgumentException("Teacher ID must be 7 characters long");
        }
        
	    try {
	    	eD = teacherDAO.readTeacher(ID);
	        return (eD != null);
	    } catch(DAOException ex) {
			throw new OperationException("Oops, something went wrong...");
		}
	}
	
	public List<String> getUsernamesByReport(String reportCode) throws OperationException, IllegalArgumentException {
        
        if (reportCode == null || reportCode.length() != 5) {
            throw new IllegalArgumentException("Report code must be 5 characters long");
        }
        
	    try {
	        return examDAO.getUsernamesByReport(reportCode);
	    } catch(DAOException ex) {
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
	        examDAO.checkPIN(insertedPin, reportCode, username);
	    } catch(DAOException ex) {
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
           examDAO.checkPrerequisites(reportCode, username);
        } catch (PropedeuticitaException pEx) {
        	System.out.println(pEx.getMessage());
            try {
                examDAO.deleteExam(reportCode, username);
            } catch (DAOException e) {
    			throw new OperationException("Oops, something went wrong...");
    		} 
    	    }
            catch(DAOException ex) {
    			throw new OperationException("Oops, something went wrong...");
    		}
    }
    
    public void ClosingReport2(String reportCode) throws OperationException, IllegalArgumentException {
        
        if (reportCode == null || reportCode.length() != 5) {
            throw new IllegalArgumentException("Report code must be 5 characters long");
        }

        try {
           examDAO.checkVotes(reportCode);
        } catch(DAOException ex) {
			throw new OperationException("Oops, something went wrong...");
		}
    }

}
