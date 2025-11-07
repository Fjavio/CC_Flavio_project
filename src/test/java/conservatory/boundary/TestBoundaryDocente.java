/*package conservatory.boundary;

import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.Scanner;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import conservatory.boundary.BoundaryDocente;
import conservatory.control.GestoreCorsiDiStudioConservatorio;
import conservatory.exception.OperationException;

//TEST WITH FAKE DATABASE (MOCKITO)

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
    	System.out.println("START TEST");
    	//Use "setter" method to INJECT the mock into the Boundary
        BoundaryDocente.setGestore(gestoreMock);
    }

    @AfterEach
    void tearDown() {
        System.out.println("END TEST");
        //Restore scanner
        BoundaryDocente.setScanner(new Scanner(System.in));
    }

    @Test
    void testClosingReport_DoneCorrectly() throws Exception {
        //User input
        String simulatedInput = "REP01\n1234567\n";
        BoundaryDocente.setScanner(new Scanner(simulatedInput));

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
    void testClosingReport_ReportNotExist() throws Exception {
        String simulatedInput = "XXXX\n";
        BoundaryDocente.setScanner(new Scanner(simulatedInput));

        doThrow(new OperationException("Report not found"))
            .when(gestoreMock).checkReport("XXXX");

        BoundaryDocente.ClosingReport();

        verify(gestoreMock).checkReport("XXXX");
    }
}*/