package conservatory.control;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;

import conservatory.database.CorsoDAO;
import conservatory.database.DocenteDAO;
import conservatory.database.EsameDAO;
import conservatory.database.StudenteDAO;
import conservatory.database.VerbaleDAO;
import conservatory.entity.EntityCorso;
import conservatory.entity.EntityDocente;
import conservatory.exception.OperationException;

import static org.junit.jupiter.api.Assertions.*;
import org.mockito.*;

@ExtendWith(MockitoExtension.class)
class UnitTestGestore {

	//Create a mock for every dependency of the gestore
    @Mock
    private CorsoDAO courseDaoMock;
    @Mock
    private DocenteDAO teacherDaoMock;
    @Mock
    private EsameDAO examDaoMock;
    @Mock
    private StudenteDAO studentDaoMock;
    @Mock
    private VerbaleDAO reportDaoMock;
    @Mock
    private ExternalService externalServiceMock;

    //Inject all the mocks above into the gestore constructor.
    @InjectMocks
    private GestoreCorsiDiStudioConservatorio gestore;

    @Test
    void testAssociationTeacherCourse_OK() throws Exception {
        
        EntityDocente docente = new EntityDocente("Mario", "Rossi", "T123456");
        EntityCorso corso = new EntityCorso("C0001", "Electronics", 6, null, null, null);

        when(teacherDaoMock.readTeacher("T123456")).thenReturn(docente);
        when(courseDaoMock.readCourse("C0001")).thenReturn(corso);
        when(courseDaoMock.readAssociationTeacherCourse("C0001")).thenReturn(null);

        assertDoesNotThrow(() -> gestore.AssociationTeacherCourse("C0001", "T123456"));

        verify(courseDaoMock).updateAssociationTeacherCourse("C0001", "T123456");
    }
    
    @Test
    void testAssociationTeacherCourse_Fail_AlreadyAssigned() throws Exception {
        
        EntityDocente teacher = new EntityDocente("Mario", "Rossi", "T123456");
        EntityCorso course = new EntityCorso("C0001", "Electronics", 6, null, null, null);

        when(teacherDaoMock.readTeacher("T123456")).thenReturn(teacher);
        when(courseDaoMock.readCourse("C0001")).thenReturn(course);
        //Simulates that the course is already assigned
        when(courseDaoMock.readAssociationTeacherCourse("C0001")).thenReturn("CFG3456");

        // Verify that gestore throws the correct exception
        OperationException ex = assertThrows(OperationException.class, () -> {
            gestore.AssociationTeacherCourse("C0001", "T123456");
        });

        assertEquals("Course has already been assigned to a teacher", ex.getMessage());
        
        //Verify that update has never been called
        verify(courseDaoMock, never()).updateAssociationTeacherCourse(anyString(), anyString());
    }
}