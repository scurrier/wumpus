import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Test;


public class SelectorTest {

	Selector testObj = new Selector(new Random(0));
	
	@Test
	public void fnA() throws Exception {
		assertEquals(1, testObj.pickRoom(null));
		assertEquals(9, testObj.pickRoom(null));
		assertEquals(10, testObj.pickRoom(null));
		assertEquals(8, testObj.pickRoom(null));
		assertEquals(16, testObj.pickRoom(null));
		assertEquals(14, testObj.pickRoom(null));
		assertEquals(12, testObj.pickRoom(null));
		assertEquals(2, testObj.pickRoom(null));
		assertEquals(20, testObj.pickRoom(null));
		assertEquals(15, testObj.pickRoom(null));
	}

	@Test
	public void fnB() throws Exception {
		assertEquals(1, testObj.pickPath(null));
		assertEquals(2, testObj.pickPath(null));
		assertEquals(2, testObj.pickPath(null));
		assertEquals(3, testObj.pickPath(null));
		assertEquals(3, testObj.pickPath(null));
		assertEquals(3, testObj.pickPath(null));
		assertEquals(3, testObj.pickPath(null));
		assertEquals(1, testObj.pickPath(null));
		assertEquals(1, testObj.pickPath(null));
		assertEquals(3, testObj.pickPath(null));
	}
	
	@Test
	public void fnC() throws Exception {
		assertEquals(3, testObj.pickMove(null));
		assertEquals(4, testObj.pickMove(null));
		assertEquals(1, testObj.pickMove(null));
		assertEquals(3, testObj.pickMove(null));
		assertEquals(3, testObj.pickMove(null));
		assertEquals(2, testObj.pickMove(null));
		assertEquals(3, testObj.pickMove(null));
		assertEquals(1, testObj.pickMove(null));
		assertEquals(3, testObj.pickMove(null));
		assertEquals(4, testObj.pickMove(null));
	}
}
