package boundary;

import static org.junit.Assume.assumeTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.*;

import control.GestoreCorsiDiStudioConservatorio;
import exception.OperationException;

/**
 * INTEGRATION TEST WITH REAL DATABASE
 * Make sure that:
 *  - The database is running
 *  - The Teacher table contains a teacher with ID "C123456"
 */

public class TestBoundaryDocenteSQL {

    private GestoreCorsiDiStudioConservatorio gestore;

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
    void testOpeningReport_OK() throws Exception {
        //Input
        BoundaryDocente.scan = new Scanner(
            "WBC99\n" +              // code report
            LocalDate.now() + "\n" + // data
            "C123456\n" +            // real id teacher
            "30\n" +                 // vote
            "false\n" +              // honors
            "excellent\n" +       // notes
            "A1234\n" +             // real course code
            "flavio\n" +          // real username
            "no\n"                   // no other exams
        );

        gestore.OpeningReport("WBC99", Date.valueOf(LocalDate.now()), "C123456");

        // Verify that report exist in mysql
        assertDoesNotThrow(() -> gestore.checkReport("WBC99"));
    }

    @Test
    void testOpeningReport_noUsername() throws Exception {
        BoundaryDocente.scan = new Scanner(
            "WFF98\n" +             
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
    void testClosingReport() throws Exception {
        // Let's assume that WWW99 already exists from previous OpeningReport
        BoundaryDocente.scan = new Scanner(
            "AAA99\n" +     
            "1234567\n"      // real PIN 
        );

        assertDoesNotThrow(() -> BoundaryDocente.ClosingReport());
    }
}
