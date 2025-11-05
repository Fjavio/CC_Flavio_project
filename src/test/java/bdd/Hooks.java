package bdd;

import static org.mockito.Mockito.mockStatic;

import org.mockito.MockedStatic;

import conservatory.database.CorsoDAO;
import conservatory.database.DocenteDAO;
import conservatory.database.EsameDAO;
import conservatory.database.StudenteDAO;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {

    // Make mock objects public and static so they can be accessed from other step classes
    public static MockedStatic<CorsoDAO> mockedCorsoDAO;
    public static MockedStatic<DocenteDAO> mockedDocenteDAO;
    public static MockedStatic<EsameDAO> mockedEsameDAO;
    public static MockedStatic<StudenteDAO> mockedStudenteDAO;

    @Before
    public void setupMocks() {
        // This single method will run once before each scenario
        // It creates all the mocks needed for the entire test suite.
        mockedCorsoDAO = mockStatic(CorsoDAO.class);
        mockedDocenteDAO = mockStatic(DocenteDAO.class);
        mockedEsameDAO = mockStatic(EsameDAO.class);
        mockedStudenteDAO = mockStatic(StudenteDAO.class);
    }

    @After
    public void tearDownMocks() {
        // This single method will run once after each scenario
        // It safely closes all mocks.
        mockedCorsoDAO.close();
        mockedDocenteDAO.close();
        mockedEsameDAO.close();
        mockedStudenteDAO.close();
    }
}