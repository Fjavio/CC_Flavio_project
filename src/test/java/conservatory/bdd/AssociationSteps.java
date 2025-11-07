package conservatory.bdd;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.mockito.Mockito;

import conservatory.control.GestoreCorsiDiStudioConservatorio;

import conservatory.database.CorsoDAO;
import conservatory.database.DocenteDAO;
import conservatory.database.EsameDAO;
import conservatory.database.StudenteDAO;
import conservatory.database.VerbaleDAO;
import conservatory.entity.EntityCorso;
import conservatory.entity.EntityDocente;
import conservatory.exception.DAOException;
import conservatory.exception.OperationException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AssociationSteps {

    private final CorsoDAO courseDaoMock;
    private final DocenteDAO teacherDaoMock;
    private final EsameDAO examDaoMock;
    private final StudenteDAO studentDaoMock;
    private final VerbaleDAO reportDaoMock;

	private final GestoreCorsiDiStudioConservatorio gestore;
	private OperationException caughtException;
	    
    public AssociationSteps() {
        //create mocks
        this.courseDaoMock = Mockito.mock(CorsoDAO.class);
        this.teacherDaoMock = Mockito.mock(DocenteDAO.class);
        this.examDaoMock = Mockito.mock(EsameDAO.class);
        this.studentDaoMock = Mockito.mock(StudenteDAO.class);
        this.reportDaoMock = Mockito.mock(VerbaleDAO.class);

        //Inject mocks
        this.gestore = new GestoreCorsiDiStudioConservatorio(
            courseDaoMock, teacherDaoMock, examDaoMock, studentDaoMock, reportDaoMock
        );
        
        this.caughtException = null;
    }
    
	@Given("a teacher with ID {string} exists")
    public void a_teacher_with_id_exists(String teacherID) throws DAOException {
        EntityDocente teacher = new EntityDocente("Claudia", "Carrara", teacherID);
        when(teacherDaoMock.readTeacher(teacherID)).thenReturn(teacher);
    }

    @Given("a course with code {string} exists")
    public void a_course_with_code_exists(String courseCode) throws DAOException {
        EntityCorso course = new EntityCorso(courseCode, "CloudComputing", 6, null, "", "");
        when(courseDaoMock.readCourse(courseCode)).thenReturn(course);
    }

    @Given("the course {string} is not yet assigned to any teacher")
    public void the_course_is_not_yet_assigned_to_any_teacher(String courseCode) throws DAOException {
        when(courseDaoMock.readAssociationTeacherCourse(courseCode)).thenReturn(null);
    }

    @Given("the course {string} is already assigned to teacher {string}")
    public void the_course_is_already_assigned_to_teacher(String courseCode, String existingTeacherID) throws DAOException {
        when(courseDaoMock.readAssociationTeacherCourse(courseCode)).thenReturn(existingTeacherID);
    }

    @When("the secretariat associates the teacher {string} with the course {string}")
    public void the_secretariat_associates_the_teacher_with_the_course(String teacherID, String courseCode) {
        try {
            gestore.AssociationTeacherCourse(courseCode, teacherID);
        } catch (OperationException | IllegalArgumentException e) {
            caughtException = (OperationException) e;
        }
    }

    @When("the secretariat tries to associate teacher {string} with course {string}")
    public void the_secretariat_tries_to_associate_teacher_with_course(String teacherID, String courseCode) {
        try {
            gestore.AssociationTeacherCourse(courseCode, teacherID);
        } catch (OperationException | IllegalArgumentException e) {
            caughtException = (OperationException) e;
        }
    }

    @Then("the association is successful and the database is updated")
    public void the_association_is_successful_and_the_database_is_updated() throws DAOException {
        assertNull("No exception should have been raised", caughtException);
        verify(courseDaoMock, times(1)).updateAssociationTeacherCourse(anyString(), anyString());
    }

    @Then("the operation fails with the message {string}")
    public void the_operation_fails_with_the_message(String expectedMessage) {
        assertNotNull("Exception should have been raised", caughtException);
        assertEquals(expectedMessage, caughtException.getMessage());
    }
}