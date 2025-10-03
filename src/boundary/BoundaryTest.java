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
		System.out.println("INIZIO TEST");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("FINE TEST");
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("INIZIO TEST");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("FINE TEST");
	}

	@Test
    public void testAssociazioneDocenteCorso() {
        BoundarySegreteriaStudenti.AssociazioneDocenteCorso();
		System.out.println("Test associazione docente corso completato senza eccezioni inattese.");
    }
	
	@Test
    public void testChiusuraVerbale() {
        BoundaryDocente.ChiusuraVerbale();
		System.out.println("Test chiusura verbale completato senza eccezioni inattese.");
    }

}
