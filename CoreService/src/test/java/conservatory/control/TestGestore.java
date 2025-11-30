package conservatory.control;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Random;
import static org.mockito.Mockito.doNothing;
import static org.mockito.ArgumentMatchers.anyString;

/**
 * INTEGRATION TEST (E2E) for gestore.
 * Runs tests with the in-memory H2 database,
 * populated by schema.sql and data.sql.
*/
@SpringBootTest
public class TestGestore {

	//Ask Spring to inject the REAL gestore (already connected to the real DAOs, which are connected to H2)
    @Autowired
    private GestoreCorsiDiStudioConservatorio gestore;

    @MockBean
    private ExternalService externalServiceMock;
    
    //The Before/After methods are no longer needed: spring manages gestore's lifecycle.

    @Test
    void testOpeningReport_OK() {
    	
    	String reportCode = "" + (10000 + new Random().nextInt(90000));
    	
        doNothing().when(externalServiceMock).bookRoom(anyString(), anyString(), anyString(), anyString());

        //Method
        assertDoesNotThrow(() -> 
            gestore.OpeningReport(
                reportCode, 
                Date.valueOf(LocalDate.now()), 
                "C123456",
                "Aula 01",
                "Morning"  
            )
        );
        
        // Verify that it has been written in the H2 database
        assertDoesNotThrow(() -> gestore.checkReport(reportCode));
    }
    
    //Tests read from the data.sql file
    @Test
    void testCheckTeacher_ExistingTeacher() throws Exception {
        assertTrue(gestore.checkTeacher("C123456"));
    }

    @Test
    void testCheckTeacher_NotExistingTeacher() throws Exception {
        assertFalse(gestore.checkTeacher("FAKE001"));
    }

    @Test
    void testCheckCourse_ExistingCourse() throws Exception {
        assertTrue(gestore.checkCourse("A1234"));
    }

    @Test
    void testCheckCourse_NotExistingCourse() throws Exception {
        assertFalse(gestore.checkCourse("ZZZZZ"));
    }

    @Test
    void testCheckStudent_ExistingStudent() throws Exception {
        assertTrue(gestore.checkStudent("flavio"));
    }

    @Test
    void testCheckStudent_NotExistingStudent() throws Exception {
        assertFalse(gestore.checkStudent("fakeUser"));
    } 
}


    