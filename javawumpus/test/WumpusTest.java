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
	
	class WumpusWithSetMapItems extends Wumpus {
		public WumpusWithSetMapItems(int[] mapItems) {
			this.items.mapItemLocations = mapItems;
		}
	}
	
	@Test
	public void testCrossover() throws Exception {
		// remember zeroth index is not used
		int[] withDups = {0,1,1,2,3,4,5};
		Wumpus testObj = new WumpusWithSetMapItems(withDups);
		assertTrue(testObj.crossover());
		int[] withDupsAtEnds = {0,1,2,3,4,5,1};
		testObj = new WumpusWithSetMapItems(withDupsAtEnds);
		assertTrue(testObj.crossover());
		int[] unique = {0,1,2,3,4,5,6};
		testObj = new WumpusWithSetMapItems(unique);
		assertFalse(testObj.crossover());
	}

}
