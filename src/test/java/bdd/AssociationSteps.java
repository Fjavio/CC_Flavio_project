package bdd;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import control.GestoreCorsiDiStudioConservatorio;
import database.CorsoDAO;
import database.DocenteDAO;
import entity.EntityCorso;
import entity.EntityDocente;
import exception.DAOException;
import exception.DBConnectionException;
import exception.OperationException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AssociationSteps {

	private final GestoreCorsiDiStudioConservatorio gestore;
	private OperationException caughtException;
	    
	// Add a constructor to initialize the state for each new scenario
    public AssociationSteps() {
    	this.gestore = new GestoreCorsiDiStudioConservatorio();
        this.caughtException = null; // Explicitly reset the exception state
    }
    
	@Given("a teacher with ID {string} exists")
    public void a_teacher_with_id_exists(String teacherID) throws DBConnectionException, DAOException {
        EntityDocente teacher = new EntityDocente("Claudia", "Carrara", teacherID);
        Hooks.mockedDocenteDAO.when(() -> DocenteDAO.readTeacher(teacherID)).thenReturn(teacher);
    }

    @Given("a course with code {string} exists")
    public void a_course_with_code_exists(String courseCode) throws DBConnectionException, DAOException {
        EntityCorso course = new EntityCorso(courseCode, "CloudComputing", 6, null, "", "");
        Hooks.mockedCorsoDAO.when(() -> CorsoDAO.readCourse(courseCode)).thenReturn(course);
    }

    @Given("the course {string} is not yet assigned to any teacher")
    public void the_course_is_not_yet_assigned_to_any_teacher(String courseCode) throws DBConnectionException, DAOException {
        Hooks.mockedCorsoDAO.when(() -> CorsoDAO.readAssociationTeacherCourse(courseCode)).thenReturn(null);
    }

    @Given("the course {string} is already assigned to teacher {string}")
    public void the_course_is_already_assigned_to_teacher(String courseCode, String existingTeacherID) throws DBConnectionException, DAOException {
        Hooks.mockedCorsoDAO.when(() -> CorsoDAO.readAssociationTeacherCourse(courseCode)).thenReturn(existingTeacherID);
    }

    @When("the secretariat associates the teacher {string} with the course {string}")
    public void the_secretariat_associates_the_teacher_with_the_course(String teacherID, String courseCode) {
        try {
            gestore.AssociationTeacherCourse(courseCode, teacherID);
        } catch (OperationException e) {
            caughtException = e;
        }
    }

    @When("the secretariat tries to associate teacher {string} with course {string}")
    public void the_secretariat_tries_to_associate_teacher_with_course(String teacherID, String courseCode) {
        try {
            gestore.AssociationTeacherCourse(courseCode, teacherID);
        } catch (OperationException e) {
            caughtException = e;
        }
    }

    @Then("the association is successful and the database is updated")
    public void the_association_is_successful_and_the_database_is_updated() {
        assertNull("No exception should have been raised", caughtException);
        Hooks.mockedCorsoDAO.verify(() -> CorsoDAO.updateAssociationTeacherCourse(anyString(), anyString()), times(1));
    }

    @Then("the operation fails with the message {string}")
    public void the_operation_fails_with_the_message(String expectedMessage) {
        assertNotNull("Exception should have been raised", caughtException);
        assertEquals(expectedMessage, caughtException.getMessage());
    }
}