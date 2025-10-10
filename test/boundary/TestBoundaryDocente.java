package boundary;

import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.sql.Date;
import java.util.Scanner;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import control.GestoreCorsiDiStudioConservatorio;
import exception.OperationException;

@ExtendWith(MockitoExtension.class)
class TestBoundaryDocente {

    @Mock
    private GestoreCorsiDiStudioConservatorio gestoreMock;

    @BeforeAll
    static void initAll() {
        System.out.println("START TEST SUITE");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("END TEST SUITE");
    }

    @BeforeEach
    void setUp() {
        // Replace the singleton instance used by getInstance() with the mock
        GestoreCorsiDiStudioConservatorio.setInstance(gestoreMock);
    }

    @AfterEach
    void tearDown() {
        System.out.println("END TEST");
    }

    @Test
    void testClosingReport_CompletamentoCorretto() throws Exception {
        //User input
        String simulatedInput = "REP01\n1234567\n";
        BoundaryDocente.scan = new Scanner(simulatedInput);

        // Mock behaviors
        when(gestoreMock.getUsernamesByReport("REP01"))
                .thenReturn(Arrays.asList("studente1"));
        doNothing().when(gestoreMock).checkReport("REP01");
        doNothing().when(gestoreMock).checkPIN(1234567, "REP01", "studente1");
        doNothing().when(gestoreMock).ClosingReport1("REP01", "studente1");
        doNothing().when(gestoreMock).ClosingReport2("REP01");

        // Execute
        BoundaryDocente.ClosingReport();

        // Verify
        verify(gestoreMock).checkReport("REP01");
        verify(gestoreMock).checkPIN(1234567, "REP01", "studente1");
        verify(gestoreMock).ClosingReport1("REP01", "studente1");
        verify(gestoreMock).ClosingReport2("REP01");
    }

    @Test
    void testClosingReport_ReportNonEsiste() throws Exception {
        String simulatedInput = "XXXX\n";
        BoundaryDocente.scan = new Scanner(simulatedInput);

        doThrow(new OperationException("Report not found"))
            .when(gestoreMock).checkReport("XXXX");

        BoundaryDocente.ClosingReport();

        verify(gestoreMock).checkReport("XXXX");
    }

    @Test
    void testClosingReport_OK() throws Exception {
        when(gestoreMock.getUsernamesByReport("REP01"))
            .thenReturn(Arrays.asList("studente1"));
        doNothing().when(gestoreMock).ClosingReport1(anyString(), anyString());
        doNothing().when(gestoreMock).ClosingReport2(anyString());

        String simulatedInput = "REP01\n1234567\n";
        BoundaryDocente.scan = new Scanner(simulatedInput);

        BoundaryDocente.ClosingReport();

        verify(gestoreMock, times(1)).ClosingReport2("REP01");
    }
}


//WITHOUT MOCK??
/*
public class TestBoundaryDocente {

    private GestoreCorsiDiStudioConservatorio gestore;

    @Before
    public void setUp() {
        gestore = GestoreCorsiDiStudioConservatorio.getInstance();
    }

    @Test
    public void testOpeningReport_ValidInput() throws Exception {
        String reportCode = "REP01";
        String teacherID = "DOC0001";
        Date date = Date.valueOf(LocalDate.now());

        boolean teacherExists = gestore.checkTeacher(teacherID);
        assertTrue("Teacher should exist in DB", teacherExists);

        gestore.OpeningReport(reportCode, date, teacherID);

        boolean reportExists = gestore.checkReport(reportCode);
        assertTrue("Report should exist after creation", reportExists);
    }

    @Test
    public void testClosingReport_Valid() throws Exception {
        String reportCode = "REP01";
        String username = "studente1";

        assertTrue("Report must exist", gestore.checkReport(reportCode));

        gestore.ClosingReport1(reportCode, username);
        gestore.ClosingReport2(reportCode);

        assertTrue("Report should be marked as closed", gestore.isReportClosed(reportCode));
    }

    @After
    public void tearDown() {
        System.out.println("END TEST");
    }
}
*/