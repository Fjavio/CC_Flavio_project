package control;

import org.junit.jupiter.api.*;

import conservatory.control.GestoreCorsiDiStudioConservatorio;

import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Random;

/** INTEGRATION TEST **/

public class TestGestore {

    private GestoreCorsiDiStudioConservatorio gestore;

    @BeforeAll
    static void setupAll() {
        System.out.println("START UNIT TEST");
    }

    @BeforeEach
    void setup() {
    	gestore = new GestoreCorsiDiStudioConservatorio();
    }
    
    @AfterEach
	void tearDown() {
		gestore = null;
		assumeTrue(gestore == null);
	}
    
    @AfterAll
    static void tearDownAll() {
        System.out.println("END UNIT TEST");
    }

    @Test
    void testOpeningReport_OK() {
    	
    	String reportCode = ""+(10000 + new Random().nextInt(90000));
    	
        assertDoesNotThrow(() -> 
            gestore.OpeningReport(reportCode, Date.valueOf(LocalDate.now()), "C123456")
        );

        //Now verify that the report actually exists
        assertDoesNotThrow(() -> gestore.checkReport(reportCode));
    }
    
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



    