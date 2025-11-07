package conservatory.bdd;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.mockito.Mockito;

import conservatory.control.GestoreCorsiDiStudioConservatorio;

import conservatory.database.CorsoDAO;
import conservatory.database.DocenteDAO;
import conservatory.database.EsameDAO;
import conservatory.database.StudenteDAO;
import conservatory.database.VerbaleDAO;
import conservatory.entity.EntityEsame;
import conservatory.entity.EntityStudente;
import conservatory.exception.DAOException;
import conservatory.exception.OperationException;
import conservatory.exception.PropedeuticitaException;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ReportSteps {

    private final CorsoDAO courseDaoMock;
    private final DocenteDAO teacherDaoMock;
    private final EsameDAO examDaoMock;
    private final StudenteDAO studentDaoMock;
    private final VerbaleDAO reportDaoMock;

	private final GestoreCorsiDiStudioConservatorio gestore;
    private EntityStudente currentStudent;
    private String currentReportCode;
    private String currentCourseCode;

    public ReportSteps() {
        //create mocks
        this.courseDaoMock = Mockito.mock(CorsoDAO.class);
        this.teacherDaoMock = Mockito.mock(DocenteDAO.class);
        this.examDaoMock = Mockito.mock(EsameDAO.class);
        this.studentDaoMock = Mockito.mock(StudenteDAO.class);
        this.reportDaoMock = Mockito.mock(VerbaleDAO.class);

        //inject mocks
        this.gestore = new GestoreCorsiDiStudioConservatorio(
            courseDaoMock, teacherDaoMock, examDaoMock, studentDaoMock, reportDaoMock
        );
        
        this.currentStudent = null;
        this.currentReportCode = null;
        this.currentCourseCode = null;
    }
    
    @Given("the following student exists:")
    public void the_following_student_exists(DataTable dataTable) throws DAOException {
        Map<String, String> data = dataTable.asMaps().get(0);
        String username = data.get("username");
        String password = data.get("password");
        int pin = Integer.parseInt(data.get("pin"));
        int idCDS = Integer.parseInt(data.get("idCDS"));

        currentStudent = new EntityStudente(username, password, pin, idCDS);
        when(studentDaoMock.readStudent(username)).thenReturn(currentStudent);
    }

    @Given("an open report with code {string} contains an exam for student {string}")
    public void an_open_report_contains_exam_for_student(String reportCode, String username) throws DAOException {
        this.currentReportCode = reportCode;
        when(examDaoMock.getUsernamesByReport(reportCode)).thenReturn(Collections.singletonList(username));
    }

    @Given("the following course exists:")
    public void the_following_course_exists(DataTable dataTable) throws DAOException {
        Map<String, String> data = dataTable.asMaps().get(0);
        this.currentCourseCode = data.get("courseCode");
        String prerequisite = data.get("prerequisite");
        when(courseDaoMock.preOf(eq(currentCourseCode))).thenReturn(prerequisite);
    }
    
    @Given("an open report {string} for course {string} contains an exam for student {string}")
    public void an_open_report_for_course_contains_exam(String reportCode, String courseCode, String username) throws DAOException {
        this.currentReportCode = reportCode;
        this.currentCourseCode = courseCode;
        when(examDaoMock.getUsernamesByReport(reportCode)).thenReturn(Collections.singletonList(username));
    }

    @Given("an open report {string} for course {string} contains a valid exam with vote {int} for student {string}")
    public void an_open_report_contains_valid_exam(String reportCode, String courseCode, int vote, String username) throws DAOException {
        an_open_report_for_course_contains_exam(reportCode, courseCode, username);
    }

    @Given("the student {string} has not passed preparatory exams")
    public void the_student_has_not_passed_any_exams(String username) throws DAOException, PropedeuticitaException {
        when(examDaoMock.readPassedExams(eq(username))).thenReturn(new ArrayList<>());
   
        //Configure the mock to throw the exception
        doThrow(new PropedeuticitaException("The prerequisites are not respected"))
            .when(examDaoMock).checkPrerequisites(eq(currentReportCode), eq(username));
    }

    @Given("the student {string} has already passed the following exams:")
    public void the_student_has_already_passed_exams(String username, DataTable dataTable) throws DAOException, PropedeuticitaException {
        List<EntityEsame> passedExams = new ArrayList<>();
        for (Map<String, String> row : dataTable.asMaps()) {
            String courseCode = row.get("courseCode");
            java.util.Date fakePassingDate = new java.util.Date();
            passedExams.add(new EntityEsame(25, false, "ok", fakePassingDate, "OLD09", courseCode, username)); 
        }
        when(examDaoMock.readPassedExams(eq(username))).thenReturn(passedExams);
        
        //Configure the happy path to avoid problems with the previous test
        doNothing().when(examDaoMock).checkPrerequisites(eq(currentReportCode), eq(username));
    }

    @When("the teacher closes report {string} providing incorrect PIN {int} for student {string}")
    public void the_teacher_closes_report_with_incorrect_pin(String reportCode, int pin, String username) throws OperationException {
        gestore.checkPIN(pin, reportCode, username);
    }

    @When("the teacher closes report {string} providing the correct PIN for {string}")
    public void the_teacher_closes_report_with_correct_pin(String reportCode, String username) throws OperationException {
        gestore.checkPIN(currentStudent.getPIN(), reportCode, username);
        gestore.ClosingReport1(reportCode, username);
        gestore.ClosingReport2(reportCode);
    }
    
    @Then("the exam for {string} in report {string} should be deleted due to the PIN mismatch")
    public void the_exam_should_be_deleted_for_pin_mismatch(String username, String reportCode) throws DAOException {
        verify(examDaoMock).checkPIN(anyInt(), eq(reportCode), eq(username));
    }

    @Then("the exam for {string} is deleted due to missing prerequisites")
    public void the_exam_is_deleted_for_missing_prerequisites(String username) throws DAOException {
        verify(examDaoMock, times(1)).deleteExam(eq(currentReportCode), eq(username));
    }

    @Then("the report closing is successful and the votes are finalized for report {string}")
    public void the_report_closing_is_successful(String reportCode) throws DAOException {
        verify(examDaoMock).checkVotes(eq(reportCode));
        verify(examDaoMock, never()).deleteExam(any(), any());
    }
}