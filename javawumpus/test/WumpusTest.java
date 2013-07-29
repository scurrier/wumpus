import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;


public class WumpusTest {

	private Wumpus testObj;

	@Before
	public void setUp() {
		testObj = new Wumpus();
		testObj.selector = new Selector(new Random(0)); //seed random for expected results
	}
	
	@Test
	public void fnA() throws Exception {
		assertEquals(1, testObj.fnA());
		assertEquals(9, testObj.fnA());
		assertEquals(10, testObj.fnA());
		assertEquals(8, testObj.fnA());
		assertEquals(16, testObj.fnA());
		assertEquals(14, testObj.fnA());
		assertEquals(12, testObj.fnA());
		assertEquals(2, testObj.fnA());
		assertEquals(20, testObj.fnA());
		assertEquals(15, testObj.fnA());
	}

	@Test
	public void fnB() throws Exception {
		assertEquals(1, testObj.fnB());
		assertEquals(2, testObj.fnB());
		assertEquals(2, testObj.fnB());
		assertEquals(3, testObj.fnB());
		assertEquals(3, testObj.fnB());
		assertEquals(3, testObj.fnB());
		assertEquals(3, testObj.fnB());
		assertEquals(1, testObj.fnB());
		assertEquals(1, testObj.fnB());
		assertEquals(3, testObj.fnB());
	}
	
	@Test
	public void fnC() throws Exception {
		assertEquals(3, testObj.fnC());
		assertEquals(4, testObj.fnC());
		assertEquals(1, testObj.fnC());
		assertEquals(3, testObj.fnC());
		assertEquals(3, testObj.fnC());
		assertEquals(2, testObj.fnC());
		assertEquals(3, testObj.fnC());
		assertEquals(1, testObj.fnC());
		assertEquals(3, testObj.fnC());
		assertEquals(4, testObj.fnC());
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
