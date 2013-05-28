import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;


public class WumpusTest {

	@Before
	public void setUp() {
		Wumpus.random = new Random(0); //seed random for expected results
	}
	
	@Test
	public void fnA() throws Exception {
		assertEquals(1, Wumpus.fnA());
		assertEquals(9, Wumpus.fnA());
		assertEquals(10, Wumpus.fnA());
		assertEquals(8, Wumpus.fnA());
		assertEquals(16, Wumpus.fnA());
		assertEquals(14, Wumpus.fnA());
		assertEquals(12, Wumpus.fnA());
		assertEquals(2, Wumpus.fnA());
		assertEquals(20, Wumpus.fnA());
		assertEquals(15, Wumpus.fnA());
	}

	@Test
	public void fnB() throws Exception {
		assertEquals(1, Wumpus.fnB());
		assertEquals(2, Wumpus.fnB());
		assertEquals(2, Wumpus.fnB());
		assertEquals(3, Wumpus.fnB());
		assertEquals(3, Wumpus.fnB());
		assertEquals(3, Wumpus.fnB());
		assertEquals(3, Wumpus.fnB());
		assertEquals(1, Wumpus.fnB());
		assertEquals(1, Wumpus.fnB());
		assertEquals(3, Wumpus.fnB());
	}
	
	@Test
	public void fnC() throws Exception {
		assertEquals(3, Wumpus.fnC());
		assertEquals(4, Wumpus.fnC());
		assertEquals(1, Wumpus.fnC());
		assertEquals(3, Wumpus.fnC());
		assertEquals(3, Wumpus.fnC());
		assertEquals(2, Wumpus.fnC());
		assertEquals(3, Wumpus.fnC());
		assertEquals(1, Wumpus.fnC());
		assertEquals(3, Wumpus.fnC());
		assertEquals(4, Wumpus.fnC());
	}

}
