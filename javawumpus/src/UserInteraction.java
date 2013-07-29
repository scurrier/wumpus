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

	public int getMoveShootChoiceFromPlayer(Wumpus wumpus) throws IOException {
		while (true) {
			print("SHOOT OR MOVE (S-M) ");
			wumpus.i$ = (char) readChar();
			readChar(); 
			if (wumpus.i$ == 'S' || wumpus.i$ == 's')
				return 1;
			else if (wumpus.i$ == 'M' || wumpus.i$ == 'm')
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

}
