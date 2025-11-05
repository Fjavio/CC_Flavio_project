package conservatory.boundary;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import conservatory.boundary.BoundaryDocente;
import conservatory.database.EsameDAO;
import conservatory.database.VerbaleDAO;
import conservatory.entity.EntityEsame;
import conservatory.entity.EntityVerbale;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.*;

/**
 * INTEGRATION TEST WITH REAL DATABASE
 * Make sure that:
 *  - The database is running
 *  - The Teacher table contains a teacher with ID "C123456"
 */

@TestMethodOrder(OrderAnnotation.class)
public class TestBoundaryDocenteSQL {

    static String lastReportCode;
    
    @BeforeAll
    static void setupAll() {
        System.out.println("START TEST SUITE");
    }

    @BeforeEach
    void setup() {
    	System.out.println("START TEST");
    	BoundaryDocente.setGestore(new conservatory.control.GestoreCorsiDiStudioConservatorio());
    }

    @AfterEach
    void tearDown() {
    	System.out.println("END TEST");
    	//Restore scanner
        BoundaryDocente.setScanner(new Scanner(System.in));
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("END TEST SUITE");
    }
    
    @Test
    @Order(1)
    void testOpeningReport_OK() throws Exception {
    	
    	//PHASE 1 - ACTION
    	lastReportCode = "" + (10000 + new Random().nextInt(90000));
    	
        //Input
    	String simulatedInput =
        	lastReportCode + "\n" +               // code report
            LocalDate.now() + "\n" + // data
            "C123456\n" +            // real id teacher
            "30\n" +                 // vote
            "false\n" +              // honors
            "excellent\n" +       // notes
            "A1234\n" +             // real course code
            "flavio\n" +          // real username
            "no\n";                   // no other exams

    	BoundaryDocente.setScanner(new Scanner(simulatedInput));
        BoundaryDocente.OpeningReport();
        
        //PHASE 2 - VERIFICATION
        //Read directly from the database to make sure the data is correct
        EntityVerbale reportCreated = VerbaleDAO.readReport(lastReportCode);
        List<EntityEsame> inseredExams = EsameDAO.readExam(lastReportCode);

        assertNotNull(reportCreated, "The report should have been created in the DB");
        assertEquals("C123456", reportCreated.getteacherID(), "The teacher ID in the report is incorrect");
        
        assertFalse(inseredExams.isEmpty(), "At least one exam should have been included");
        assertEquals("flavio", inseredExams.get(0).getUsername(), "The student's username in the exam is incorrect");
        assertEquals(30, inseredExams.get(0).getvote(), "The exam vote is incorrect");
    }

    @Test
    @Order(3)
    void testOpeningReport_noUsername() throws Exception {
       
        lastReportCode = "" + (10000 + new Random().nextInt(90000));

        String simulatedInput =
        	lastReportCode + "\n" +       
            LocalDate.now() + "\n" + 
            "C123456\n" +            
            "25\n" +                
            "false\n" +            
            "some errors\n" +            
            "A1234\n" +              
            "fakeUser\n" +         
            "no\n";                   
     
        BoundaryDocente.setScanner(new Scanner(simulatedInput));
        BoundaryDocente.OpeningReport();
        EntityVerbale reportCreated = VerbaleDAO.readReport(lastReportCode);
        List<EntityEsame> inseredExams = EsameDAO.readExam(lastReportCode);

        assertNotNull(reportCreated, "The report should have been created in the DB");
        assertTrue(inseredExams.isEmpty(), "Anyone exam should have been included (username not exist)");
    }

    
    @Test
    @Order(2)
    void testClosingReport() throws Exception {
        // Let's assume that a report already exists from previous OpeningReport
    	String simulatedInput =
                lastReportCode + "\n" +
                "1234567\n";  // real PIN
           
    	BoundaryDocente.setScanner(new Scanner(simulatedInput));
        assertDoesNotThrow(() -> BoundaryDocente.ClosingReport());
    }
}
