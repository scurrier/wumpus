import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class UserInteraction {

	public void print(int data) {
		System.out.print(data);
	}

	public void println(int data) {
		System.out.println(data);
	}

	public void println(String data) {
		System.out.println(data);
	}

	public void print(String data) {
		System.out.print(data);
	}

	public int readChar() throws IOException {
		return System.in.read();
	}

	public int readInt() {
		String line = "";
		BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
		try {
			line = is.readLine();
		} catch (IOException e) {
			return 0;
		}
		return Integer.parseInt(line);
	}

	public void giveInstructionsIfNeeded() throws IOException {
		if (needInstructions())
			giveInstructions();
	}
	
	private void giveInstructions() throws IOException {
		println("WELCOME TO 'HUNT THE WUMPUS'");
		println("  THE WUMPUS LIVES IN A CAVE OF 20 ROOMS. EACH ROOM");
		println("HAS 3 TUNNELS LEADING TO OTHER ROOMS. (LOOK AT A");
		println("DODECAHEDRON TO SEE HOW THIS WORKS-IF YOU DON'T KNOW");
		println("WHAT A DODECAHEDRON IS, ASK SOMEONE)");
		println("");
		println("     HAZARDS:");
		println(" BOTTOMLESS PITS - TWO ROOMS HAVE BOTTOMLESS PITS IN THEM");
		println("     IF YOU GO THERE, YOU FALL INTO THE PIT (& LOSE!)");
		println(" SUPER BATS - TWO OTHER ROOMS HAVE SUPER BATS. IF YOU");
		println("     GO THERE, A BAT GRABS YOU AND TAKES YOU TO SOME OTHER");
		println("     ROOM AT RANDOM. (WHICH MAY BE TROUBLESOME)");
		println("HIT RETURN TO CONTINUE"); readChar();
		println("     WUMPUS:");
		println(" THE WUMPUS IS NOT BOTHERED BY HAZARDS (HE HAS SUCKER");
		println(" FEET AND IS TOO BIG FOR A BAT TO LIFT).  USUALLY");
		println(" HE IS ASLEEP.  TWO THINGS WAKE HIM UP: YOU SHOOTING AN");
		println("ARROW OR YOU ENTERING HIS ROOM.");
		println("     IF THE WUMPUS WAKES HE MOVES (P=.75) ONE ROOM");
		println(" OR STAYS STILL (P=.25).  AFTER THAT, IF HE IS WHERE YOU");
		println(" ARE, HE EATS YOU UP AND YOU LOSE!");
		println("");
		println("     YOU:");
		println(" EACH TURN YOU MAY MOVE OR SHOOT A CROOKED ARROW");
		println("   MOVING:  YOU CAN MOVE ONE ROOM (THRU ONE TUNNEL)");
		println("   ARROWS:  YOU HAVE 5 ARROWS.  YOU LOSE WHEN YOU RUN OUT");
		println("   EACH ARROW CAN GO FROM 1 TO 5 ROOMS. YOU AIM BY TELLING");
		println("   THE COMPUTER THE ROOM#S YOU WANT THE ARROW TO GO TO.");
		println("   IF THE ARROW CAN'T GO THAT WAY (IF NO TUNNEL) IT MOVES");
		println("   AT RANDOM TO THE NEXT ROOM.");
		println("     IF THE ARROW HITS THE WUMPUS, YOU WIN.");
		println("     IF THE ARROW HITS YOU, YOU LOSE.");
		println("HIT RETURN TO CONTINUE"); readChar();
		println("    WARNINGS:");
		println("     WHEN YOU ARE ONE ROOM AWAY FROM A WUMPUS OR HAZARD,");
		println("     THE COMPUTER SAYS:");
		println(" WUMPUS:  'I SMELL A WUMPUS'");
		println(" BAT   :  'BATS NEARBY'");
		println(" PIT   :  'I FEEL A DRAFT'");
		println("");
	}

	private boolean needInstructions() throws IOException {
		print("INSTRUCTIONS (Y-N) "); 
		char answer = (char) readChar();
		readChar();
		return answer != 'N' && answer != 'n';
	}

	public int getMoveShootChoiceFromPlayer() throws IOException {
		while (true) {
			print("SHOOT OR MOVE (S-M) ");
			char i$ = (char) readChar();
			readChar(); 
			if (i$ == 'S' || i$ == 's')
				return 1;
			else if (i$ == 'M' || i$ == 'm')
				return 2;
		}
	}

	public int getMoveDirection() {
		int move;
		do {
			print("WHERE TO ");													// 985 print "WHERE TO";
			move = readInt();																// 990 input l
		} while (move < 1 || move > 20);
		return move;
	}

	public void gameStartHeader() {
		println("HUNT THE WUMPUS");
	}

	public void youLose() {
		println("HA HA HA - YOU LOSE!");
	}

	public void youWin() {
		println("HEE HEE HEE - THE WUMPUS'LL GET YOU NEXT TIME!!");
	}

	public boolean useSameSetup() throws IOException {
		print("SAME SETUP (Y-N)");
		char i$ = (char) readChar(); readChar(); 
		return i$ != 'Y' && i$ != 'y';
	}

	void printLocation(int player) {
		print("YOU ARE IN ROOM ");
		println(player);
	}

	void printTunnels(Paths room) {
		print("TUNNELS LEAD TO "); print(room.room(1));
					print(" "); print(room.room(2)); 
					print(" "); println(room.room(3));
	}

	public void printPlayerStatus(Map map, MapItemLocations items) {
		println("");
		items.printNearbyItemHints(this, map.getRoomExits(items.getPlayer()));
		printLocation(items.getPlayer());
		printTunnels(map.getRoomExits(items.getPlayer()));
		println("");
	}

	public int getShotDistance() {
		int numberOfRoomsToShoot;
		do {
			print("NO. OF ROOMS (1-5) ");										// 735 print "NO. OF ROOMS (1-5)";
			numberOfRoomsToShoot = readInt();																// 740 input j9
		} while (outOfArrowRange(numberOfRoomsToShoot));
		return numberOfRoomsToShoot;
	}

	private boolean outOfArrowRange(int numberOfRoomsToShoot) {
		return numberOfRoomsToShoot < 1 || numberOfRoomsToShoot > 5;
	}
	
	public int[] getIntendedFlightPath(int numberOfRooms) {
		int[] p = new int[6];
		for (int k = 1; k <= numberOfRooms; ++k) {
			do {
				print("ROOM # ");													// 760 print "ROOM #";
				p[k] = readInt();																// 765 input p(k)
				if (did180(p, k))
					println("ARROWS AREN'T THAT CROOKED - TRY ANOTHER ROOM");			// 780 print "ARROWS AREN'T THAT CROOKED - TRY ANOTHER ROOM"
			} while(did180(p, k));
		}
		return p;
	}

	private boolean did180(int[] path, int roomCount) {
		return roomCount > 2 && path[roomCount] == path[roomCount-2];
	}

	public void shotSelf() {
		println("OUCH! ARROW GOT YOU!");
	}

	public void shotTheWumpus() {
		println("AHA! YOU GOT THE WUMPUS!");
	}

	public void wumpusWins() {
		println("TSK TSK TSK - WUMPUS GOT YOU!");
	}

	public void arrowMissed() {
		println("MISSED");
	}

	public void invalidMove() {
		print("NOT POSSIBLE - ");
	}

	public void encounteredWumpus() {
		println("... OOPS! BUMPED A WUMPUS!");
	}
	
}
