import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;


public class Wumpus {

	private int currentLine = 5;
	private Deque<Integer> returnLine = new ArrayDeque<Integer>();
	private int nextLine;
	char i$ = '\0';
	private int[][] s = {{0,0,0,0},
	{0,2,5,8},		{0,1,3,10},		{0,2,4,12},		{0,3,5,14},		{0,1,4,6},
	{0,5,7,15},		{0,6,8,17},		{0,1,7,9},		{0,8,10,18},	{0,2,9,11},
	{0,10,12,19},	{0,3,11,13},	{0,12,14,20},	{0,4,13,15},	{0,6,14,16},
	{0,15,17,20},	{0,7,16,18},	{0,9,17,19},	{0,11,18,20},	{0,13,16,19}};
	protected int[] mapItemLocations = new int[7];
	private int[] copyOfMapItemlocations = new int[7];
	private int[] p = new int[6];
	private int availableArrows = 5;
	private int ll = availableArrows;
	private int o = 1;
	private int f = 0;
	
	private int j = 0;
	private int k = 0;
	private int k1 = 0;
	private int j9 = 0;
	public static Random random = new Random();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Wumpus game = new Wumpus();
		try {
			game.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void run() throws IOException {
		while (currentLine <= 1150) {
			nextLine = currentLine + 1;
			switch (currentLine) {
			case 15: 
				if (needInstructions()) 										// 25 if (i$ = "N") or (i$ = "n") then 35
						giveInstructions();																	// 30 gosub 375
				break;																	// 35 goto 80
			case 170: placeItemsOnMap();
					break;												
			case 225: break;																				// 225 rem *** SET NO. OF ARROWS ***
			case 230: availableArrows = 5; break;																		// 230 a = 5
			case 235: ll = playerLocation(); break;																		// 235 l = l(1)
			case 240: break;																				// 240 rem *** RUN THE GAME ***
			case 245: println("HUNT THE WUMPUS"); break;											// 245 print "HUNT THE WUMPUS"
			case 250: break;																				// 250 rem *** HAZARD WARNING AND LOCATION ***
			case 255: printPlayerStatus(); break;																// 255 gosub 585
			case 260: break;																				// 260 rem *** MOVE OR SHOOT ***
			case 265: gosub(670, 270); break;																// 265 gosub 670
			case 270: switch(o) {case 1: nextLine = 280; break; case 2: nextLine = 300; break;} break;		// 270 on o goto 280,300
			case 275: break;																				// 275 rem *** SHOOT ***
			case 280: gosub(715, 285); break;																// 280 gosub 715
			case 285: if (f == 0) nextLine = 255; break;													// 285 if f = 0 then 255
			case 290: nextLine = 310; break;																// 290 goto 310
			case 295: break;																				// 295 rem *** MOVE ***
			case 300: gosub(975, 305); break;																// 300 gosub 975
			case 305: if (f == 0) nextLine = 255; break;													// 305 if f = 0 then 255
			case 310: if (f > 0) nextLine = 335; break;														// 310 if f > 0 then 335
			case 315: break;																				// 315 rem *** LOSE ***
			case 320: println("HA HA HA - YOU LOSE!"); break;										// 320 print "HA HA HA - YOU LOSE!"
			case 325: nextLine = 340; break;																// 325 goto 340
			case 330: break;																				// 330 rem *** WIN ***
			case 335: println("HEE HEE HEE - THE WUMPUS'LL GET YOU NEXT TIME!!"); break;			// 335 print "HEE HEE HEE - THE WUMPUS'LL GET YOU NEXT TIME!!"
			case 340: j = 1; break;																			// 340 for j = 1 to 6
			case 345: mapItemLocations[j] = copyOfMapItemlocations[j]; break;																	// 345 l(j) = m(j)
			case 350: ++j; if (j <= 6) nextLine = 345; break;												// 350 next j
			case 355: print("SAME SETUP (Y-N)"); break;											// 355 print "SAME SETUP (Y-N)";
			case 360: i$ = (char) readChar(); readChar(); break;								// 360 input i$
			case 365: if (i$ != 'Y' && i$ != 'y') nextLine = 170; break;									// 365 if (i$ <> "Y") and (i$ <> "y") then 170
			case 370: nextLine = 230; break;																// 370 goto 230
			case 670: break;																				// 670 rem *** CHOOSE OPTION ***
			case 675: print("SHOOT OR MOVE (S-M) "); break;										// 675 print "SHOOT OR MOVE (S-M)";
			case 680: i$ = (char) readChar(); readChar(); break;								// 680 input i$
			case 685: if (i$ != 'S' && i$ != 's') nextLine = 700; break;									// 685 if (i$ <> "S") and (i$ <> "s") then 700
			case 690: o = 1; break;																			// 690 o = 1
			case 695: returnFromGosub(); break;																// 695 return
			case 700: if (i$ != 'M' && i$ != 'm') nextLine = 675; break;									// 700 if (i$ <> "M") and (i$ <> "m") then 675
			case 705: o = 2; break;																			// 705 o = 2
			case 710: returnFromGosub(); break;																// 710 return
			case 715: break;																				// 715 rem *** ARROW ROUTINE ***
			case 720: f = 0; break;																			// 720 f = 0
			case 725: break;																				// 725 rem *** PATH OF ARROW ***
			case 735: print("NO. OF ROOMS (1-5) "); break;										// 735 print "NO. OF ROOMS (1-5)";
			case 740: j9 = readInt(); break;																// 740 input j9
			case 745: if (j9 < 1) nextLine = 735; break;													// 745 if j9 < 1 then 735
			case 750: if (j9 > 5) nextLine = 735; break;													// 750 if j9 > 5 then 735
			case 755: k = 1; break;																			// 755 for k = 1 to j9
			case 760: print("ROOM # "); break;													// 760 print "ROOM #";
			case 765: p[k] = readInt(); break;																// 765 input p(k)
			case 770: if (k <= 2) nextLine = 790; break;													// 770 if k <= 2 then 790
			case 775: if (p[k] != p[k-2]) nextLine = 790; break;											// 775 if p(k) <> p(k-2) then 790
			case 780: println("ARROWS AREN'T THAT CROOKED - TRY ANOTHER ROOM"); break;			// 780 print "ARROWS AREN'T THAT CROOKED - TRY ANOTHER ROOM"
			case 785: nextLine = 760; break;																// 785 goto 760
			case 790: ++k; if (k <= j9) nextLine = 760; break;												// 790 next k
			case 795: break;																				// 795 rem *** SHOOT ARROW ***
			case 800: ll = playerLocation(); break;																		// 800 l = l(1)
			case 805: k = 1; break;																			// 805 for k = 1 to j9
			case 810: k1 = 1; break;																		// 810 for k1 = 1 to 3
			case 815: if (s[ll][k1] == p[k]) nextLine = 895; break;											// 815 if s(l,k1) = p(k) then 895
			case 820: ++k1; if (k1 <= 3) nextLine = 815; break;												// 820 next k1
			case 825: break;																				// 825 rem *** NO TUNNEL FOR ARROW ***
			case 830: ll = s[ll][fnB()]; break;																// 830 l = s(l,fnb(1))
			case 835: nextLine = 900; break;																// 835 goto 900
			case 840: ++k; if (k <= j9) nextLine = 810; break;												// 840 next k
			case 845: println("MISSED"); break;													// 845 print "MISSED"
			case 850: ll = playerLocation(); break;																		// 850 l = l(1)
			case 855: break;																				// 855 rem *** MOVE WUMPUS ***
			case 860: gosub(935, 865); break;																// 860 gosub 935
			case 865: break;																				// 865 rem *** AMMO CHECK ***
			case 870: availableArrows = availableArrows - 1; break;																	// 870 a = a-1
			case 875: if (availableArrows > 0) nextLine = 885; break;													// 875 if a > 0 then 885
			case 880: f = -1; break;																		// 880 f = -1
			case 885: returnFromGosub(); break;																// 885 return
			case 890: break;																				// 890 rem *** SEE IF ARROW IS AT l(1) OR AT l(2)
			case 895: ll = p[k]; break;																		// 895 l = p(k)
			case 900: if (ll != mapItemLocations[2]) nextLine = 920; break;												// 900 if l <> l(2) then 920
			case 905: println("AHA! YOU GOT THE WUMPUS!"); break;								// 905 print "AHA! YOU GOT THE WUMPUS!"
			case 910: f = 1; break;																			// 910 f = 1
			case 915: returnFromGosub(); break;																// 915 return
			case 920: if (ll != playerLocation()) nextLine = 840; break;												// 920 if l <> l(1) then 840
			case 925: println("OUCH! ARROW GOT YOU!"); break;									// 925 print "OUCH! ARROW GOT YOU!"
			case 930: nextLine = 880; break;																// 930 goto 880
			case 935: break;																				// 935 rem *** MOVE WUMPUS ROUTINE ***
			case 940: k = fnC(); break;																		// 940 k = fnc(0)
			case 945: if (k == 4) nextLine = 955; break;													// 945 if k = 4 then 955
			case 950: mapItemLocations[2] = s[mapItemLocations[2]][k]; break;																// 950 l(2) = s(l(2),k)
			case 955: if (mapItemLocations[2] != ll) nextLine = 970; break;												// 955 if l(2) <> l then 970
			case 960: println("TSK TSK TSK - WUMPUS GOT YOU!"); break;							// 960 print "TSK TSK TSK - WUMPUS GOT YOU!"
			case 965: f = -1; break;																		// 965 f = -1
			case 970: returnFromGosub(); break;																// 970 return
			case 975: break;																				// 975 rem *** MOVE ROUTINE ***
			case 980: f = 0; break;																			// 980 f = 0
			case 985: print("WHERE TO "); break;													// 985 print "WHERE TO";
			case 990: ll = readInt(); break;																// 990 input l
			case 995: if (ll < 1) nextLine = 985; break;													// 995 if l < 1 then 985
			case 1000: if (ll > 20) nextLine = 985; break;													// 1000 if l > 20 then 985
			case 1005: k = 1; break;																		// 1005 for k = 1 to 3
			case 1010: break;																				// 1010 rem *** CHECK IF LEGAL MOVE ***
			case 1015: if (s[playerLocation()][k] == ll) nextLine = 1045; break;										// 1015 if s(l(1),k) = l then 1045
			case 1020: ++k; if (k <= 3) nextLine = 1010; break;												// 1020 next k
			case 1025: if (ll == playerLocation()) nextLine = 1045; break;												// 1025 if l = l(1) then 1045
			case 1030: print("NOT POSSIBLE - "); break;											// 1030 print "NOT POSSIBLE -";
			case 1035: nextLine = 985; break;																// 1035 goto 985
			case 1040: break;																				// 1040 rem *** CHECK FOR HAZARDS ***
			case 1045: mapItemLocations[1] = ll; break;																	// 1045 l(1) = l
			case 1050: break;																				// 1050 rem *** WUMPUS ***
			case 1055: if (ll != mapItemLocations[2]) nextLine = 1090; break;												// 1055 if l <> l(2) then 1090
			case 1060: println("... OOPS! BUMPED A WUMPUS!"); break;								// 1060 print "... OOPS! BUMPED A WUMPUS!"
			case 1065: break;																				// 1065 rem *** MOVE WUMPUS ***
			case 1070: gosub(940, 1075); break;																// 1070 gosub 940
			case 1075: if (f == 0) nextLine = 1090; break;													// 1075 if f = 0 then 1090
			case 1080: returnFromGosub(); break;															// 1080 return
			case 1085: break;																				// 1085 rem *** PIT ***
			case 1090: if (ll == mapItemLocations[3]) nextLine = 1100; break;												// 1090 if l = l(3) then 1100
			case 1095: if (ll != mapItemLocations[4]) nextLine = 1120; break;												// 1095 if l <> l(4) then 1120
			case 1100: println("YYYYIIIIEEEE . . . FELL IN PIT"); break;							// 1100 print "YYYYIIIIEEEE . . . FELL IN PIT"
			case 1105: f = -1; break;																		// 1105 f = -1
			case 1110: returnFromGosub(); break;															// 1110 return
			case 1115: break;																				// 1115 rem *** BATS ***
			case 1120: if (ll == mapItemLocations[5]) nextLine = 1130; break;												// 1120 if l = l(5) then 1130
			case 1125: if (ll != mapItemLocations[6]) nextLine = 1145; break;												// 1125 if l <> l(6) then 1145
			case 1130: println("ZAP--SUPER BAT SNATCH! ELSEWHEREVILLE FOR YOU!"); break;			// 1130 print "ZAP--SUPER BAT SNATCH! ELSEWHEREVILLE FOR YOU!"
			case 1135: ll = fnA(); break;																	// 1135 l = fna(1)
			case 1140: nextLine = 1045; break;																// 1140 goto 1045
			case 1145: returnFromGosub(); break;															// 1145 return
			case 1150: break;																				// 1150 end
			}
			currentLine = nextLine;
		}
	}
	public void placeItemsOnMap() {
		do {
					randomizeMapItemLocations();
			}  while (crossover());
	}
	public void printPlayerStatus() {
		println("");														// 590 print
		printNearbyItemHints();
		printPlayerLocation();
		printTunnelOptions();
		println("");														// 660 print
	}
	public void printTunnelOptions() {
		print("TUNNELS LEAD TO "); print(s[ll][1]);						// 655 print "TUNNELS LEAD TO ";s(l,1);" ";s(l,2);" ";s(l,3)
					print(" "); print(s[ll][2]); 
					print(" "); println(s[ll][3]);
	}
	public void printPlayerLocation() {
		print("YOUR ARE IN ROOM "); println(playerLocation());				// 650 print "YOU ARE IN ROOM ";l(1)
	}
	public void printNearbyItemHints() {
		for (j = 2; j <= 6; ++j) {
					for (k = 1; k <= 3; ++k) {
						if (s[playerLocation()][k] == mapItemLocations[j]) 
							printItemNearbyPlayerHint(j-1);
					}
			
		}
	}
	public void printItemNearbyPlayerHint(int itemType) {
		switch(itemType) {																			// 610 on j-1 goto 615,625,x,635,635
				case 1: println("I SMELL A WUMPUS!"); break;
				case 2:
				case 3: println("I FEEL A DRAFT"); break;
				case 4:
				case 5: println("BATS NEARBY!"); break;
				};
	}
	public boolean crossover() {
		for (int j = 1; j <= 6; ++j) {
			for (int k = 1; k <= 6; ++k) {
				if (j == k) continue;
				if (mapItemLocations[j] == mapItemLocations[k])
					return true;
			}
		}
		return false;
	}
	
	private void randomizeMapItemLocations() {
		for (j = 1; j <= 6; ++j) {
			mapItemLocations[j] = fnA();																	// 175 l(j) = fna(0)
			copyOfMapItemlocations[j] = mapItemLocations[j];																	// 180 m(j) = l(j)
		}
	}
	private int playerLocation() {
		return mapItemLocations[1];
	}
	private boolean needInstructions() throws IOException {
		print("INSTRUCTIONS (Y-N) "); 
		char answer = (char) readChar();
		readChar();
		return answer != 'N' && answer != 'n';
	}
	
	private void giveInstructions() throws IOException {
		println("WELCOME TO 'HUNT THE WUMPUS'");					 		// 380 print "WELCOME TO 'HUNT THE WUMPUS'"
		println(																	// 385 print
		"  THE WUMPUS LIVES IN A CAVE OF 20 ROOMS. EACH ROOM");							// "  THE WUMPUS LIVES IN A CAVE OF 20 ROOMS. EACH ROOM"
		println("HAS 3 TUNNELS LEADING TO OTHER ROOMS. (LOOK AT A");		// 390 print "HAS 3 TUNNELS LEADING TO OTHER ROOMS. (LOOK AT A"
		println(																	// 395 print
		"DODECAHEDRON TO SEE HOW THIS WORKS-IF YOU DON'T KNOW");							// "DODECAHEDRON TO SEE HOW THIS WORKS-IF YOU DON'T KNOW"
		println("WHAT A DODECAHEDRON IS, ASK SOMEONE)");					// 400 print "WHAT A DODECAHEDRON IS, ASK SOMEONE)"
		println("");														// 405 print
		println("     HAZARDS:");											// 410 print "     HAZARDS:"
		println(																	// 415 print
		" BOTTOMLESS PITS - TWO ROOMS HAVE BOTTOMLESS PITS IN THEM");					// " BOTTOMLESS PITS - TWO ROOMS HAVE BOTTOMLESS PITS IN THEM"
		println(																	// 420 print
		"     IF YOU GO THERE, YOU FALL INTO THE PIT (& LOSE!)");						// "     IF YOU GO THERE, YOU FALL INTO THE PIT (& LOSE!)"
		println(																	// 425 print
		" SUPER BATS - TWO OTHER ROOMS HAVE SUPER BATS. IF YOU");						// " SUPER BATS - TWO OTHER ROOMS HAVE SUPER BATS. IF YOU"
		println(																	// 430 print
		"     GO THERE, A BAT GRABS YOU AND TAKES YOU TO SOME OTHER");					// "     GO THERE, A BAT GRABS YOU AND TAKES YOU TO SOME OTHER"
		println("     ROOM AT RANDOM. (WHICH MAY BE TROUBLESOME)");			// 435 print "     ROOM AT RANDOM. (WHICH MAY BE TROUBLESOME)"
		println("HIT RETURN TO CONTINUE"); readChar();				// 440 input "HIT RETURN TO CONTINUE";a$
		println("     WUMPUS:");											// 445 print "     WUMPUS:"
		println(																	// 450 print
		" THE WUMPUS IS NOT BOTHERED BY HAZARDS (HE HAS SUCKER");						// " THE WUMPUS IS NOT BOTHERED BY HAZARDS (HE HAS SUCKER"
		println(" FEET AND IS TOO BIG FOR A BAT TO LIFT).  USUALLY"); 		// 455 print " FEET AND IS TOO BIG FOR A BAT TO LIFT).  USUALLY"
		println(																	// 460 print
		" HE IS ASLEEP.  TWO THINGS WAKE HIM UP: YOU SHOOTING AN");						// " HE IS ASLEEP.  TWO THINGS WAKE HIM UP: YOU SHOOTING AN"
		println("ARROW OR YOU ENTERING HIS ROOM.");							// 465 print "ARROW OR YOU ENTERING HIS ROOM."
		println(																	// 470 print
		"     IF THE WUMPUS WAKES HE MOVES (P=.75) ONE ROOM");							// "     IF THE WUMPUS WAKES HE MOVES (P=.75) ONE ROOM"
		println(																	// 475 print
		" OR STAYS STILL (P=.25).  AFTER THAT, IF HE IS WHERE YOU");						// " OR STAYS STILL (P=.25).  AFTER THAT, IF HE IS WHERE YOU"
		println(" ARE, HE EATS YOU UP AND YOU LOSE!");						// 480 print " ARE, HE EATS YOU UP AND YOU LOSE!"
		println("");														// 485 print
		println("     YOU:");												// 490 print "     YOU:"
		println(" EACH TURN YOU MAY MOVE OR SHOOT A CROOKED ARROW"); 		// 495 print " EACH TURN YOU MAY MOVE OR SHOOT A CROOKED ARROW"
		println(																	// 500 print
		"   MOVING:  YOU CAN MOVE ONE ROOM (THRU ONE TUNNEL)");							// "   MOVING:  YOU CAN MOVE ONE ROOM (THRU ONE TUNNEL)"
		println(																	// 505 print
		"   ARROWS:  YOU HAVE 5 ARROWS.  YOU LOSE WHEN YOU RUN OUT");					// "   ARROWS:  YOU HAVE 5 ARROWS.  YOU LOSE WHEN YOU RUN OUT"
		println(																	// 510 print
		"   EACH ARROW CAN GO FROM 1 TO 5 ROOMS. YOU AIM BY TELLING");					// "   EACH ARROW CAN GO FROM 1 TO 5 ROOMS. YOU AIM BY TELLING"
		println(																	// 515 print
		"   THE COMPUTER THE ROOM#S YOU WANT THE ARROW TO GO TO.");						// "   THE COMPUTER THE ROOM#S YOU WANT THE ARROW TO GO TO."
		println(																	// 520 print
		"   IF THE ARROW CAN'T GO THAT WAY (IF NO TUNNEL) IT MOVES"); 					// "   IF THE ARROW CAN'T GO THAT WAY (IF NO TUNNEL) IT MOVES"
		println("   AT RANDOM TO THE NEXT ROOM.");							// 525 print "   AT RANDOM TO THE NEXT ROOM."
		println("     IF THE ARROW HITS THE WUMPUS, YOU WIN.");				// 530 print "     IF THE ARROW HITS THE WUMPUS, YOU WIN."
		println("     IF THE ARROW HITS YOU, YOU LOSE.");					// 535 print "     IF THE ARROW HITS YOU, YOU LOSE."
		println("HIT RETURN TO CONTINUE"); readChar();				// 540 input "HIT RETURN TO CONTINUE";a$
		println("    WARNINGS:");											// 545 print "    WARNINGS:"
		println(																	// 550 print
		"     WHEN YOU ARE ONE ROOM AWAY FROM A WUMPUS OR HAZARD,");						// "     WHEN YOU ARE ONE ROOM AWAY FROM A WUMPUS OR HAZARD,"
		println("     THE COMPUTER SAYS:");									// 555 print "     THE COMPUTER SAYS:"
		println(" WUMPUS:  'I SMELL A WUMPUS'");							// 560 print " WUMPUS:  'I SMELL A WUMPUS'"
		println(" BAT   :  'BATS NEARBY'");									// 565 print " BAT   :  'BATS NEARBY'"
		println(" PIT   :  'I FEEL A DRAFT'");								// 570 print " PIT   :  'I FEEL A DRAFT'"
		println("");														// 575 print
	}
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
	private void gosub(int gosubLine, int lineToReturnTo) {
		nextLine = gosubLine;
		returnLine.addLast(lineToReturnTo);
	}
	private void returnFromGosub() {
		if (returnLine.isEmpty())
			nextLine = 1151;
		else
			nextLine = returnLine.pollLast();
	}
	public static int fnA() {
		return random.nextInt(20) + 1;
	}
	public static int fnB() {
		return random.nextInt(3) + 1;
	}
	public static int fnC() {
		return random.nextInt(4) + 1;
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
}
