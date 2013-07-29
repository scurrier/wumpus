import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Test;


public class SelectorTest {

	Selector testObj = new Selector(new Random(0));
	
	@Test
	public void fnA() throws Exception {
		assertEquals(1, testObj.pickRoom());
		assertEquals(9, testObj.pickRoom());
		assertEquals(10, testObj.pickRoom());
		assertEquals(8, testObj.pickRoom());
		assertEquals(16, testObj.pickRoom());
		assertEquals(14, testObj.pickRoom());
		assertEquals(12, testObj.pickRoom());
		assertEquals(2, testObj.pickRoom());
		assertEquals(20, testObj.pickRoom());
		assertEquals(15, testObj.pickRoom());
	}

	@Test
	public void fnB() throws Exception {
		assertEquals(1, testObj.pickPath());
		assertEquals(2, testObj.pickPath());
		assertEquals(2, testObj.pickPath());
		assertEquals(3, testObj.pickPath());
		assertEquals(3, testObj.pickPath());
		assertEquals(3, testObj.pickPath());
		assertEquals(3, testObj.pickPath());
		assertEquals(1, testObj.pickPath());
		assertEquals(1, testObj.pickPath());
		assertEquals(3, testObj.pickPath());
	}
	
	@Test
	public void fnC() throws Exception {
		assertEquals(3, testObj.pickMove());
		assertEquals(4, testObj.pickMove());
		assertEquals(1, testObj.pickMove());
		assertEquals(3, testObj.pickMove());
		assertEquals(3, testObj.pickMove());
		assertEquals(2, testObj.pickMove());
		assertEquals(3, testObj.pickMove());
		assertEquals(1, testObj.pickMove());
		assertEquals(3, testObj.pickMove());
		assertEquals(4, testObj.pickMove());
	}
}
