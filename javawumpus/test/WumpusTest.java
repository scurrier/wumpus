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
		
	class WumpusWithSetMapItems extends Wumpus {
		public WumpusWithSetMapItems(int[] mapItems) {
			this.items = new MapItemLocations(mapItems);
		}
	}
	
	@Test
	public void testCrossover() throws Exception {
		// remember zeroth index is not used
		int[] withDups = {0,1,1,2,3,4,5};
		Wumpus testObj = new WumpusWithSetMapItems(withDups);
		assertTrue(testObj.items.crossover());
		int[] withDupsAtEnds = {0,1,2,3,4,5,1};
		testObj = new WumpusWithSetMapItems(withDupsAtEnds);
		assertTrue(testObj.items.crossover());
		int[] unique = {0,1,2,3,4,5,6};
		testObj = new WumpusWithSetMapItems(unique);
		assertFalse(testObj.items.crossover());
	}

}
