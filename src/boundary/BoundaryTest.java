package boundary;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BoundaryTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("START TEST");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("END TEST");
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("START TEST");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("END TEST");
	}

	@Test
    public void testAssociationTeacherCourse() {
        BoundarySegreteriaStudenti.AssociationTeacherCourse();
		System.out.println("Teacher association test completed without any unexpected exceptions.");
    }
	
	@Test
    public void testClosingReport() {
        BoundaryDocente.ClosingReport();
		System.out.println("Closing report test completed without any unexpected exceptions.");
    }

}
