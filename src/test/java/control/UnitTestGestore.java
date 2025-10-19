package control;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.*;
import org.mockito.junit.jupiter.MockitoExtension;
import database.DocenteDAO;
import entity.EntityCorso;
import entity.EntityDocente;
import database.CorsoDAO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;
import org.mockito.*;

@ExtendWith(MockitoExtension.class)
class UnitTestGestore {

    @Test
    void testAssociationTeacherCourse_OK() throws Exception {
        EntityDocente docente = new EntityDocente("Mario", "Rossi", "T123456");
        EntityCorso corso = new EntityCorso("C0001", "Electronics", 6, null, null, null);

        try (
            MockedStatic<DocenteDAO> mockDocenteDAO = mockStatic(DocenteDAO.class);
            MockedStatic<CorsoDAO> mockCorsoDAO = mockStatic(CorsoDAO.class)
        ) {
            mockDocenteDAO.when(() -> DocenteDAO.readTeacher("T123456")).thenReturn(docente);
            mockCorsoDAO.when(() -> CorsoDAO.readCourse("C0001")).thenReturn(corso);
            mockCorsoDAO.when(() -> CorsoDAO.readAssociationTeacherCourse("C0001")).thenReturn(null);

            GestoreCorsiDiStudioConservatorio gestore = new GestoreCorsiDiStudioConservatorio();

            assertDoesNotThrow(() -> gestore.AssociationTeacherCourse("C0001", "T123456"));

            mockCorsoDAO.verify(() -> CorsoDAO.updateAssociationTeacherCourse("C0001", "T123456"));
        }
    }
}