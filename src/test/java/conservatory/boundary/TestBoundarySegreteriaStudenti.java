package conservatory.boundary;

import static org.mockito.Mockito.*;
import java.util.Scanner;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import conservatory.boundary.BoundarySegreteriaStudenti;
import conservatory.control.GestoreCorsiDiStudioConservatorio;
import conservatory.exception.OperationException;

@ExtendWith(MockitoExtension.class)
class TestBoundarySegreteriaStudenti {

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
        BoundarySegreteriaStudenti.setGestore(gestoreMock);
    }
    
    @AfterEach
    void tearDown() {
    	System.out.println("END TEST");
    	//Restore scanner
        BoundarySegreteriaStudenti.setScanner(new Scanner(System.in));
    }

    @Test
    void testAssociationTeacherCourse_Success() throws Exception {
    	
        String simulatedInput = "A1234\nC123456\n"; //All valid
        BoundarySegreteriaStudenti.setScanner(new Scanner(simulatedInput));

        //Boundary calls checkCourse/checkTeacher to validate input
        when(gestoreMock.checkCourse("A1234")).thenReturn(true);
        when(gestoreMock.checkTeacher("C123456")).thenReturn(true);
        //Boundary calls final method to execute operation
        doNothing().when(gestoreMock).AssociationTeacherCourse("A1234", "C123456");

        BoundarySegreteriaStudenti.AssociationTeacherCourse(); //Execute method under test

        verify(gestoreMock).checkCourse("A1234");
        verify(gestoreMock).checkTeacher("C123456");
        verify(gestoreMock).AssociationTeacherCourse("A1234", "C123456");
    }

    @Test
    void testAssociationTeacherCourse_FailsBecauseCourseAlreadyAssigned() throws Exception {
        
        String simulatedInput = "A1234\nC123456\n";
        BoundarySegreteriaStudenti.setScanner(new Scanner(simulatedInput));

        when(gestoreMock.checkCourse("A1234")).thenReturn(true);
        when(gestoreMock.checkTeacher("C123456")).thenReturn(true);
        //Let's simulate a failure: the handler throws an exception
        doThrow(new OperationException("Course already assigned"))
            .when(gestoreMock).AssociationTeacherCourse("A1234", "C123456");

        BoundarySegreteriaStudenti.AssociationTeacherCourse();

        //Check that the association attempt has been made anyway
        verify(gestoreMock).AssociationTeacherCourse("A1234", "C123456");
    }
}