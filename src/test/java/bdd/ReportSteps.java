package bdd;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import control.GestoreCorsiDiStudioConservatorio;
import database.CorsoDAO;
import database.EsameDAO;
import database.StudenteDAO;
import entity.EntityEsame;
import entity.EntityStudente;
import exception.DAOException;
import exception.DBConnectionException;
import exception.OperationException;
import exception.PropedeuticitaException;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ReportSteps {

    private GestoreCorsiDiStudioConservatorio gestore = GestoreCorsiDiStudioConservatorio.getInstance();
    private EntityStudente currentStudent;
    private String currentReportCode;
    private String currentCourseCode;

    //Add a constructor to initialize the state
    public ReportSteps() {
        this.gestore = GestoreCorsiDiStudioConservatorio.getInstance();
        this.currentStudent = null;
        this.currentReportCode = null;
        this.currentCourseCode = null;
    }
    
    @Given("the following student exists:")
    public void the_following_student_exists(DataTable dataTable) throws DBConnectionException, DAOException {
        Map<String, String> data = dataTable.asMaps().get(0);
        String username = data.get("username");
        String password = data.get("password");
        int pin = Integer.parseInt(data.get("pin"));
        int idCDS = Integer.parseInt(data.get("idCDS"));

        currentStudent = new EntityStudente(username, password, pin, idCDS);
        Hooks.mockedStudenteDAO.when(() -> StudenteDAO.readStudent(username)).thenReturn(currentStudent);
    }

    @Given("an open report with code {string} contains an exam for student {string}")
    public void an_open_report_contains_exam_for_student(String reportCode, String username) throws DBConnectionException, DAOException {
        this.currentReportCode = reportCode;
        Hooks.mockedEsameDAO.when(() -> EsameDAO.getUsernamesByReport(reportCode)).thenReturn(Collections.singletonList(username));
    }

    @Given("the following course exists:")
    public void the_following_course_exists(DataTable dataTable) throws DBConnectionException, DAOException {
        Map<String, String> data = dataTable.asMaps().get(0);
        this.currentCourseCode = data.get("courseCode");
        String prerequisite = data.get("prerequisite");
        Hooks.mockedCorsoDAO.when(() -> CorsoDAO.preOf(any(Connection.class), eq(currentCourseCode))).thenReturn(prerequisite);
    }
    
    @Given("an open report {string} for course {string} contains an exam for student {string}")
    public void an_open_report_for_course_contains_exam(String reportCode, String courseCode, String username) throws DBConnectionException, DAOException {
        this.currentReportCode = reportCode;
        this.currentCourseCode = courseCode;
        Hooks.mockedEsameDAO.when(() -> EsameDAO.getUsernamesByReport(reportCode)).thenReturn(Collections.singletonList(username));
    }

    @Given("an open report {string} for course {string} contains a valid exam with vote {int} for student {string}")
    public void an_open_report_contains_valid_exam(String reportCode, String courseCode, int vote, String username) throws DBConnectionException, DAOException {
        an_open_report_for_course_contains_exam(reportCode, courseCode, username);
    }

    @Given("the student {string} has not passed preparatory exams")
    public void the_student_has_not_passed_any_exams(String username) throws DBConnectionException, DAOException {
        Hooks.mockedEsameDAO.when(() -> EsameDAO.readPassedExams(any(Connection.class), eq(username))).thenReturn(new ArrayList<>());
   
        Hooks.mockedEsameDAO.when(() -> EsameDAO.checkPrerequisites(eq(currentReportCode), eq(username)))
                           .thenThrow(new PropedeuticitaException("The prerequisites are not respected"));
    }

    @Given("the student {string} has already passed the following exams:")
    public void the_student_has_already_passed_exams(String username, DataTable dataTable) throws DBConnectionException, DAOException {
        List<EntityEsame> passedExams = new ArrayList<>();
        for (Map<String, String> row : dataTable.asMaps()) {
            String courseCode = row.get("courseCode");
            java.util.Date fakePassingDate = new java.util.Date(); //irrelevant 
            passedExams.add(new EntityEsame(25, false, "ok", fakePassingDate, "OLD09", courseCode, username)); 
        }
        Hooks.mockedEsameDAO.when(() -> EsameDAO.readPassedExams(any(Connection.class), eq(username))).thenReturn(passedExams);
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
    public void the_exam_should_be_deleted_for_pin_mismatch(String username, String reportCode) throws DBConnectionException, DAOException {
        Hooks.mockedEsameDAO.verify(() -> EsameDAO.checkPIN(anyInt(), eq(reportCode), eq(username)));
    }

    @Then("the exam for {string} is deleted due to missing prerequisites")
    public void the_exam_is_deleted_for_missing_prerequisites(String username) throws DBConnectionException, DAOException {
        Hooks.mockedEsameDAO.verify(() -> EsameDAO.deleteExam(eq(currentReportCode), eq(username)), times(1));
    }

    @Then("the report closing is successful and the votes are finalized for report {string}")
    public void the_report_closing_is_successful(String reportCode) throws DBConnectionException, DAOException {
        Hooks.mockedEsameDAO.verify(() -> EsameDAO.checkVotes(eq(reportCode)), times(1));
        Hooks.mockedEsameDAO.verify(() -> EsameDAO.deleteExam(any(), any()), never()); //verify never called deleteExam
    }
}