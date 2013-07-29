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
		assertEquals(1, testObj.selector.fnA(testObj));
		assertEquals(9, testObj.selector.fnA(testObj));
		assertEquals(10, testObj.selector.fnA(testObj));
		assertEquals(8, testObj.selector.fnA(testObj));
		assertEquals(16, testObj.selector.fnA(testObj));
		assertEquals(14, testObj.selector.fnA(testObj));
		assertEquals(12, testObj.selector.fnA(testObj));
		assertEquals(2, testObj.selector.fnA(testObj));
		assertEquals(20, testObj.selector.fnA(testObj));
		assertEquals(15, testObj.selector.fnA(testObj));
	}

	@Test
	public void fnB() throws Exception {
		assertEquals(1, testObj.selector.fnB(testObj));
		assertEquals(2, testObj.selector.fnB(testObj));
		assertEquals(2, testObj.selector.fnB(testObj));
		assertEquals(3, testObj.selector.fnB(testObj));
		assertEquals(3, testObj.selector.fnB(testObj));
		assertEquals(3, testObj.selector.fnB(testObj));
		assertEquals(3, testObj.selector.fnB(testObj));
		assertEquals(1, testObj.selector.fnB(testObj));
		assertEquals(1, testObj.selector.fnB(testObj));
		assertEquals(3, testObj.selector.fnB(testObj));
	}
	
	@Test
	public void fnC() throws Exception {
		assertEquals(3, testObj.selector.fnC(testObj));
		assertEquals(4, testObj.selector.fnC(testObj));
		assertEquals(1, testObj.selector.fnC(testObj));
		assertEquals(3, testObj.selector.fnC(testObj));
		assertEquals(3, testObj.selector.fnC(testObj));
		assertEquals(2, testObj.selector.fnC(testObj));
		assertEquals(3, testObj.selector.fnC(testObj));
		assertEquals(1, testObj.selector.fnC(testObj));
		assertEquals(3, testObj.selector.fnC(testObj));
		assertEquals(4, testObj.selector.fnC(testObj));
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
