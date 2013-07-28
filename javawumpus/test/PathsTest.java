import static org.junit.Assert.*;

import org.junit.Test;


public class PathsTest {

	@Test
	public void testCanGetToRoom() {
		Paths testObj = new Paths(new int[]{1,2,3});
		assertTrue(testObj.canGetToRoom(1));
		assertTrue(testObj.canGetToRoom(2));
		assertTrue(testObj.canGetToRoom(3));
		assertFalse(testObj.canGetToRoom(4));
	}

}
