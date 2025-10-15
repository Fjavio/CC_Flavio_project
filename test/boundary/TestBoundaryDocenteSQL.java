package boundary;

import static org.junit.Assume.assumeTrue;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.Random;
import org.junit.jupiter.api.*;

import control.GestoreCorsiDiStudioConservatorio;
import exception.OperationException;

/**
 * INTEGRATION TEST WITH REAL DATABASE
 * Make sure that:
 *  - The database is running
 *  - The Teacher table contains a teacher with ID "C123456"
 */

@TestMethodOrder(OrderAnnotation.class)
public class TestBoundaryDocenteSQL {

    private GestoreCorsiDiStudioConservatorio gestore;
    static String lastReportCode;
    
    @BeforeAll
    static void setupAll() {
        System.out.println("START INTEGRATION TEST");
    }

    @BeforeEach
    void setup() {
        gestore = GestoreCorsiDiStudioConservatorio.getInstance();
    }

    @AfterEach
    void tearDown() {
    	gestore = null;
		assumeTrue(gestore == null);
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("END INTEGRATION TEST");
    }
    
    @Test
    @Order(1)
    void testOpeningReport_OK() throws Exception {
    	
    	lastReportCode = "" + (10000 + new Random().nextInt(90000));
    	
        //Input
        BoundaryDocente.scan = new Scanner(
        	lastReportCode + "\n" +               // code report
            LocalDate.now() + "\n" + // data
            "C123456\n" +            // real id teacher
            "30\n" +                 // vote
            "false\n" +              // honors
            "excellent\n" +       // notes
            "A1234\n" +             // real course code
            "flavio\n" +          // real username
            "no\n"                   // no other exams
        );

        BoundaryDocente.OpeningReport();
    }

    @Test
    @Order(3)
    void testOpeningReport_noUsername() throws Exception {
       
        lastReportCode = "" + (10000 + new Random().nextInt(90000));

        BoundaryDocente.scan = new Scanner(
        	lastReportCode + "\n" +       
            LocalDate.now() + "\n" + 
            "C123456\n" +            
            "25\n" +                
            "false\n" +            
            "some errors\n" +            
            "A1234\n" +              
            "fakeUser\n" +         
            "no\n"                   
        );

        BoundaryDocente.OpeningReport();
    }

    
    @Test
    @Order(2)
    void testClosingReport() throws Exception {
        // Let's assume that a report already exists from previous OpeningReport
    	BoundaryDocente.scan = new Scanner(
                lastReportCode + "\n" +
                "1234567\n"  // real PIN
            );

        assertDoesNotThrow(() -> BoundaryDocente.ClosingReport());
    }
}
