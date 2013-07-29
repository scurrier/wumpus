import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Test;


public class SelectorTest {

	Selector testObj = new Selector(new Random(0));
	
	@Test
	public void fnA() throws Exception {
		assertEquals(1, testObj.fnA(null));
		assertEquals(9, testObj.fnA(null));
		assertEquals(10, testObj.fnA(null));
		assertEquals(8, testObj.fnA(null));
		assertEquals(16, testObj.fnA(null));
		assertEquals(14, testObj.fnA(null));
		assertEquals(12, testObj.fnA(null));
		assertEquals(2, testObj.fnA(null));
		assertEquals(20, testObj.fnA(null));
		assertEquals(15, testObj.fnA(null));
	}

	@Test
	public void fnB() throws Exception {
		assertEquals(1, testObj.fnB(null));
		assertEquals(2, testObj.fnB(null));
		assertEquals(2, testObj.fnB(null));
		assertEquals(3, testObj.fnB(null));
		assertEquals(3, testObj.fnB(null));
		assertEquals(3, testObj.fnB(null));
		assertEquals(3, testObj.fnB(null));
		assertEquals(1, testObj.fnB(null));
		assertEquals(1, testObj.fnB(null));
		assertEquals(3, testObj.fnB(null));
	}
	
	@Test
	public void fnC() throws Exception {
		assertEquals(3, testObj.fnC(null));
		assertEquals(4, testObj.fnC(null));
		assertEquals(1, testObj.fnC(null));
		assertEquals(3, testObj.fnC(null));
		assertEquals(3, testObj.fnC(null));
		assertEquals(2, testObj.fnC(null));
		assertEquals(3, testObj.fnC(null));
		assertEquals(1, testObj.fnC(null));
		assertEquals(3, testObj.fnC(null));
		assertEquals(4, testObj.fnC(null));
	}
}
