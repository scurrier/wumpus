import static org.junit.Assert.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;


public class WumpusAcceptanceTest {
	class TestableWumpus extends Wumpus {
		@Override
		public void print(int data) {
			output.append(data);
		}
		@Override
		public void println(int data) {
			output.append(data);
			output.append('\n');
		}
		@Override
		public void print(String data) {
			output.append(data);
		}
		@Override
		public void println(String data) {
			output.append(data);
			output.append('\n');
		}
		@Override
		public int readChar() throws IOException {
			output.append('\n');
			if (charInputIndex < charInput.length)
				return (charInput[charInputIndex++]);
			throw new IOException();
		}
		@Override
		public int readInt()  {
			output.append('\n');
			if (intInputIndex < intInput.length)
				return (intInput[intInputIndex++]);
			return 0;
		}
		
		TestableWumpus(String charInput) {
			this(charInput, new int[0]);
		}

		TestableWumpus(String charInput, int[]intInput) {
			try {
				this.charInput = charInput.getBytes("UTF8");
			} catch (UnsupportedEncodingException e) {
			}
			this.intInput= intInput; 
		}
		StringBuilder output = new StringBuilder();
		int charInputIndex = 0;
		byte[] charInput;
		int intInputIndex = 0;
		int[] intInput;
	}
	
	@Before
	public void setUp() {
		Wumpus.random = new Random(0); //seed random for expected results		
	}
	
	public void runGame(TestableWumpus testObj) {
		try {
			testObj.run();
		} catch (IOException e) { //ThrowingAnIOException is how the app is currently terminated
		}
	}
	
	@Test
	public void testGameWithInstructions() {
		TestableWumpus testObj = new TestableWumpus("Y\r\r\r");
		runGame(testObj);
		assertEquals("INSTRUCTIONS (Y-N) \n\n"
				+ "WELCOME TO 'HUNT THE WUMPUS'\n"
				+ "  THE WUMPUS LIVES IN A CAVE OF 20 ROOMS. EACH ROOM\n"
				+ "HAS 3 TUNNELS LEADING TO OTHER ROOMS. (LOOK AT A\n"
				+ "DODECAHEDRON TO SEE HOW THIS WORKS-IF YOU DON'T KNOW\n"
				+ "WHAT A DODECAHEDRON IS, ASK SOMEONE)\n"
                + "\n"
				+ "     HAZARDS:\n"
				+ " BOTTOMLESS PITS - TWO ROOMS HAVE BOTTOMLESS PITS IN THEM\n"
				+ "     IF YOU GO THERE, YOU FALL INTO THE PIT (& LOSE!)\n"
				+ " SUPER BATS - TWO OTHER ROOMS HAVE SUPER BATS. IF YOU\n"
				+ "     GO THERE, A BAT GRABS YOU AND TAKES YOU TO SOME OTHER\n"
				+ "     ROOM AT RANDOM. (WHICH MAY BE TROUBLESOME)\n"
				+ "HIT RETURN TO CONTINUE\n\n"
			    + "     WUMPUS:\n"
			   	+ " THE WUMPUS IS NOT BOTHERED BY HAZARDS (HE HAS SUCKER\n"
			   	+ " FEET AND IS TOO BIG FOR A BAT TO LIFT).  USUALLY\n"
			   	+ " HE IS ASLEEP.  TWO THINGS WAKE HIM UP: YOU SHOOTING AN\n"
			   	+ "ARROW OR YOU ENTERING HIS ROOM.\n"
			   	+ "     IF THE WUMPUS WAKES HE MOVES (P=.75) ONE ROOM\n"
			   	+ " OR STAYS STILL (P=.25).  AFTER THAT, IF HE IS WHERE YOU\n"
			   	+ " ARE, HE EATS YOU UP AND YOU LOSE!\n"
                + "\n"
			   	+ "     YOU:\n"
			   	+ " EACH TURN YOU MAY MOVE OR SHOOT A CROOKED ARROW\n"
			   	+ "   MOVING:  YOU CAN MOVE ONE ROOM (THRU ONE TUNNEL)\n"
			   	+ "   ARROWS:  YOU HAVE 5 ARROWS.  YOU LOSE WHEN YOU RUN OUT\n"
			   	+ "   EACH ARROW CAN GO FROM 1 TO 5 ROOMS. YOU AIM BY TELLING\n"
			   	+ "   THE COMPUTER THE ROOM#S YOU WANT THE ARROW TO GO TO.\n"
			   	+ "   IF THE ARROW CAN'T GO THAT WAY (IF NO TUNNEL) IT MOVES\n"
			   	+ "   AT RANDOM TO THE NEXT ROOM.\n"
			   	+ "     IF THE ARROW HITS THE WUMPUS, YOU WIN.\n"
			   	+ "     IF THE ARROW HITS YOU, YOU LOSE.\n"
			   	+ "HIT RETURN TO CONTINUE\n\n"
			    + "    WARNINGS:\n"
			    + "     WHEN YOU ARE ONE ROOM AWAY FROM A WUMPUS OR HAZARD,\n"
			    + "     THE COMPUTER SAYS:\n"
			    + " WUMPUS:  'I SMELL A WUMPUS'\n"
			    + " BAT   :  'BATS NEARBY'\n"
			    + " PIT   :  'I FEEL A DRAFT'\n"
                + "\n"
			    + "HUNT THE WUMPUS\n"
                + "\n"
			    + "I FEEL A DRAFT\n"
			    + "YOUR ARE IN ROOM 1\n"
                + "TUNNELS LEAD TO 2 5 8\n"
			    + "\n"
			    + "SHOOT OR MOVE (S-M) \n" 

				, testObj.output.toString());
	}

	@Test
	public void testWithoutInstructions() throws Exception {
		TestableWumpus testObj = new TestableWumpus("N\r");
		runGame(testObj);
		assertEquals("INSTRUCTIONS (Y-N) \n" 
					+ "\n"
					+ "HUNT THE WUMPUS\n"
					+ "\n"
					+ "I FEEL A DRAFT\n"
					+ "YOUR ARE IN ROOM 1\n"
					+ "TUNNELS LEAD TO 2 5 8\n"
					+ "\n"
					+ "SHOOT OR MOVE (S-M) \n"
		, testObj.output.toString());
	}

	@Test
	public void testKillWumpus() throws Exception {
		int[] intInput = {5, 6, 7, 17, 18, 1, 9};
		TestableWumpus testObj = new TestableWumpus("N\rm\rm\rm\rm\rm\rs\r", intInput);
		runGame(testObj);
		assertEquals("INSTRUCTIONS (Y-N) \n" 
					+ "\n"
					+ "HUNT THE WUMPUS\n"
					+ "\n"
					+ "I FEEL A DRAFT\n"
					+ "YOUR ARE IN ROOM 1\n"
					+ "TUNNELS LEAD TO 2 5 8\n"
					+ "\n"
					+ "SHOOT OR MOVE (S-M) \n"
					+ "\n"
                    + "WHERE TO \n" 
                    + "\n"
		            + "YOUR ARE IN ROOM 5\n"
		            + "TUNNELS LEAD TO 1 4 6\n"
		            + "\n"
		            + "SHOOT OR MOVE (S-M) \n" 
		            + "\n"
		            + "WHERE TO \n" 
		            + "\n"
		            + "YOUR ARE IN ROOM 6\n"
		            + "TUNNELS LEAD TO 5 7 15\n"
		            + "\n"
		            + "SHOOT OR MOVE (S-M) \n" 
		            + "\n"
		            + "WHERE TO \n" 
		            + "\n"
		            + "I FEEL A DRAFT\n"
		            + "YOUR ARE IN ROOM 7\n"
		            + "TUNNELS LEAD TO 6 8 17\n"
		            + "\n"
		            + "SHOOT OR MOVE (S-M) \n" 
                    + "\n"
		            + "WHERE TO \n" 
		            + "\n"
		            + "BATS NEARBY!\n"
		            + "YOUR ARE IN ROOM 17\n"
		            + "TUNNELS LEAD TO 7 16 18\n"
		            + "\n"
		            + "SHOOT OR MOVE (S-M) \n" 
		            + "\n"
		            + "WHERE TO \n" 
		            + "\n"
		            + "I SMELL A WUMPUS!\n"
		            + "YOUR ARE IN ROOM 18\n"
		            + "TUNNELS LEAD TO 9 17 19\n"
		            + "\n"
		            + "SHOOT OR MOVE (S-M) \n" 
		            + "\n"
		            + "NO. OF ROOMS (1-5) \n" 
		            + "ROOM # \n"
		            + "AHA! YOU GOT THE WUMPUS!\n"
		            + "HEE HEE HEE - THE WUMPUS'LL GET YOU NEXT TIME!!\n"
		            + "SAME SETUP (Y-N)\n"		
		, testObj.output.toString());
	}

	@Test
	public void testFallInPit() throws Exception {
		int[] intInput = {8};
		TestableWumpus testObj = new TestableWumpus("N\rm\r", intInput);
		runGame(testObj);
		assertEquals("INSTRUCTIONS (Y-N) \n" 
					+ "\n"
					+ "HUNT THE WUMPUS\n"
                    + "\n"
					+ "I FEEL A DRAFT\n"
					+ "YOUR ARE IN ROOM 1\n"
					+ "TUNNELS LEAD TO 2 5 8\n"
                    + "\n"
					+ "SHOOT OR MOVE (S-M) \n" 
                    + "\n"
					+ "WHERE TO \n" 
					+ "YYYYIIIIEEEE . . . FELL IN PIT\n"
					+ "HA HA HA - YOU LOSE!\n"
					+ "SAME SETUP (Y-N)\n"
		, testObj.output.toString());
	}

	// pit 8 and 10, bat in 14, wumpus in 9
	@Test
	public void testCrookedArrow() throws Exception {
		int[] intInput = {5, 5, 6, 7, 6, 8, 9};
		TestableWumpus testObj = new TestableWumpus("N\rm\rs\r", intInput);
		runGame(testObj);
		assertEquals("INSTRUCTIONS (Y-N) \n" 
					+ "\n"
					+ "HUNT THE WUMPUS\n"
                    + "\n"
					+ "I FEEL A DRAFT\n"
					+ "YOUR ARE IN ROOM 1\n"
					+ "TUNNELS LEAD TO 2 5 8\n"
                    + "\n"
					+ "SHOOT OR MOVE (S-M) \n" 
                    + "\n"
                    + "WHERE TO \n" 
                    + "\n"
                    + "YOUR ARE IN ROOM 5\n"
                    + "TUNNELS LEAD TO 1 4 6\n"
                    + "\n"
                    + "SHOOT OR MOVE (S-M) \n" 
                    + "\n"
                    + "NO. OF ROOMS (1-5) \n"
					+ "ROOM # \n"
					+ "ROOM # \n"
					+ "ROOM # \n"
                    + "ARROWS AREN'T THAT CROOKED - TRY ANOTHER ROOM\n"
					+ "ROOM # \n"
					+ "ROOM # \n"
					+ "ROOM # \n"
					+ "AHA! YOU GOT THE WUMPUS!\n"
					+ "HEE HEE HEE - THE WUMPUS'LL GET YOU NEXT TIME!!\n"
					+ "SAME SETUP (Y-N)\n"
		, testObj.output.toString());
	}
}