/*package conservatory.boundary;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
import java.util.Scanner;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import conservatory.boundary.BoundarySegreteriaStudenti;
import conservatory.database.CorsoDAO;
import conservatory.database.DocenteDAO;
import conservatory.entity.EntityCorso;
import conservatory.entity.EntityDocente;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

// INTEGRATION TEST REAL DATABASE

@TestMethodOrder(OrderAnnotation.class)
public class TestBoundarySegreteriaStudentiSQL {

    static String lastTeacherID;
    static String lastCourseCode;

    @BeforeAll
    static void setupAll() {
        System.out.println("START TEST SUITE");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("END TEST SUITE");
    }
    
    @BeforeEach
    void setUp() {
    	System.out.println("START TEST");
    	BoundarySegreteriaStudenti.setGestore(new conservatory.control.GestoreCorsiDiStudioConservatorio());
    }
    
    @AfterEach
    void tearDown() {
    	System.out.println("END TEST");
    	//Restore scanner
    	BoundarySegreteriaStudenti.setScanner(new Scanner(System.in));
    }

    @Test
    @Order(1)
    void testAddTeacher_OK() throws Exception {
        
        lastTeacherID = "TCH" + (1000 + new Random().nextInt(9000));
        String name = "John";
        String surname = "Fish";

        String simulatedInput = name + "\n" + surname + "\n" + lastTeacherID + "\n";
        BoundarySegreteriaStudenti.setScanner(new Scanner(simulatedInput));
        
        BoundarySegreteriaStudenti.AddTeacher();

        //VERIFY
        EntityDocente createdTeacher = DocenteDAO.readTeacher(lastTeacherID);
        
        assertNotNull(createdTeacher, "The teacher should have been entered into the database");
        assertEquals(name, createdTeacher.getteacherName(), "The teacher's name does not match");
        assertEquals(surname, createdTeacher.getteacherSurname(), "The teacher's surname does not match");
    }

    @Test
    @Order(2)
    void testAddCourse_OK() throws Exception {
       
        lastCourseCode = "CR" + (100 + new Random().nextInt(900));
        String courseName = "Software Engineering";
        int cfu = 9;

        String simulatedInput = lastCourseCode + "\n" +  
                                courseName + "\n" +      
                                cfu + "\n" +          
                                "no\n" +   //"Do you want to add prerequisites of?"
                                "no\n";    //"Do you want to add prerequisites for?" 
        
        BoundarySegreteriaStudenti.setScanner(new Scanner(simulatedInput));

        BoundarySegreteriaStudenti.AddCourse();

        //VERIFY
        EntityCorso createdCourse = CorsoDAO.readCourse(lastCourseCode);

        assertNotNull(createdCourse, "The course should have been entered into the database");
        assertEquals(courseName, createdCourse.getcourseName(), "The course name does not match");
        assertEquals(cfu, createdCourse.getCFU(), "The course credits do not correspond");
        assertNull(createdCourse.getteacherID(), "The newly created course should not have an associated teacher");
    }
    
    @Test
    @Order(3)
    void testAssociationTeacherCourse_OK() throws Exception {
    
        assertNotNull(lastTeacherID, "Teacher ID not available from previous test");
        assertNotNull(lastCourseCode, "CourseCode not available from previous test");

        String simulatedInput = lastCourseCode + "\n" + lastTeacherID + "\n";
        BoundarySegreteriaStudenti.setScanner(new Scanner(simulatedInput));

        BoundarySegreteriaStudenti.AssociationTeacherCourse();

        EntityCorso updatedCourse = CorsoDAO.readCourse(lastCourseCode);

        assertNotNull(updatedCourse, "The course should still exist in the database");
        assertEquals(lastTeacherID, updatedCourse.getteacherID(), "The teacher was not correctly associated with the course");
    }
    
    @Test
    @Order(4)
    void testAssociationTeacherCourse_FailsIfAlreadyAssigned() throws Exception {
        //Try to associate ANOTHER teacher to the SAME course already associated in the test @Order(3)
        assertNotNull(lastCourseCode, "Course code not available from previous test");

        String otherTeacherID = "C123456"; //Teacher who exists in the DB
        
        String simulatedInput = lastCourseCode + "\n" + otherTeacherID + "\n";
        BoundarySegreteriaStudenti.setScanner(new Scanner(simulatedInput));

        BoundarySegreteriaStudenti.AssociationTeacherCourse();

        //Let's verify that the data on the DB has NOT been modified.
        EntityCorso course = CorsoDAO.readCourse(lastCourseCode);

        assertNotNull(course);
        assertEquals(lastTeacherID, course.getteacherID(), "The course association was not to be changed");
        assertNotEquals(otherTeacherID, course.getteacherID(), "The new teacher was not supposed to be associated");
    }
}*/