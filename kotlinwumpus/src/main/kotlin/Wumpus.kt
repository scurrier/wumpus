import java.util.ArrayDeque
import java.util.Deque
import java.util.Random

class Wumpus {
	private var currentLine: Int = 0
    private val returnLine: Deque<Int> = ArrayDeque()
	private var nextLine: Int = 0
    var random = Random()
	var console = Console()
	var earlyExitHack: Int = 1150
	val p = Array(6) {0}
	var gameState = GameState(random)
	val map = GameMap()

	fun main() {
		try {
			currentLine = 5
			var ll = 0
			var o = 1
			var f = 0

			var j = 0
			var k = 0
			var k1 = 0
			var j9 = 0
			while (currentLine <= 1150 && currentLine != earlyExitHack) {
				nextLine = currentLine + 1
				when (currentLine) {
				15 -> if (needInstruction()) {giveInstructions()}
				170 -> gameState.intializeLocations()
				230 -> {
					gameState.resetArrows()
					console.println("HUNT THE WUMPUS")
				}
				255 -> printRoomDescription()
				265 -> o = getAction()
				270 -> when(o) {1 -> nextLine = 280; 2 -> nextLine = 300}										// 270 on o goto 280,300
				275 -> {}																						// 275 rem *** SHOOT ***
				280 -> gosub(715, 285)													// 280 gosub 715
				285 -> if (f == 0) nextLine = 255																// 285 if f = 0 then 255
				290 -> nextLine = 310																			// 290 goto 310
				295 -> {}																						// 295 rem *** MOVE ***
				300 -> gosub(975, 305)													// 300 gosub 975
				305 -> if (f == 0) nextLine = 255																// 305 if f = 0 then 255
				310 -> if (f > 0) nextLine = 335																// 310 if f > 0 then 335
				315 -> {}																						// 315 rem *** LOSE ***
				320 -> console.println("HA HA HA - YOU LOSE!")															// 320 print "HA HA HA - YOU LOSE!"
				325 -> nextLine = 340																			// 325 goto 340
				330 -> {}																						// 330 rem *** WIN ***
				335 -> console.println("HEE HEE HEE - THE WUMPUS'LL GET YOU NEXT TIME!!")								// 335 print "HEE HEE HEE - THE WUMPUS'LL GET YOU NEXT TIME!!"
				340 -> j = 1																					// 340 for j = 1 to 6
				345 -> gameState.locations[j] = gameState.initialLocations[j]																				// 345 l(j) = m(j)
				350 -> { ++j; if (j <= 6) nextLine = 345 }														// 350 next j
				355 -> console.print("SAME SETUP (Y-N)")														// 355 print "SAME SETUP (Y-N)";
				360 -> {
					val iS = console.readln()                                                                    // 360 input i$
					if (iS != 'Y' && iS != 'y') nextLine = 170                                                // 365 if (i$ <> "Y") and (i$ <> "y") then 170
					else nextLine = 230                                                                            // 370 goto 230
				}
				665 -> returnFromGosub()																		// 665 return
				715 -> {}																						// 715 rem *** ARROW ROUTINE ***
				720 -> f = 0																					// 720 f = 0
				725 -> {}																						// 725 rem *** PATH OF ARROW ***
				735 -> console.print("NO. OF ROOMS (1-5) ")																// 735 print "NO. OF ROOMS (1-5)";
				740 -> j9 = console.readInt()																			// 740 input j9
				745 -> if (j9 < 1) nextLine = 735																// 745 if j9 < 1 then 735
				750 -> if (j9 > 5) nextLine = 735																// 750 if j9 > 5 then 735
				755 -> k = 1																					// 755 for k = 1 to j9
				760 -> console.print("ROOM # ")																			// 760 print "ROOM #";
				765 -> p[k] = console.readInt()																			// 765 input p(k)
				770 -> if (k <= 2) nextLine = 790																// 770 if k <= 2 then 790
				775 -> if (p[k] != p[k-2]) nextLine = 790														// 775 if p(k) <> p(k-2) then 790
				780 -> console.println("ARROWS AREN'T THAT CROOKED - TRY ANOTHER ROOM")									// 780 print "ARROWS AREN'T THAT CROOKED - TRY ANOTHER ROOM"
				785 -> nextLine = 760																			// 785 goto 760
				790 -> { ++k; if (k <= j9) nextLine = 760 }														// 790 next k
				795 -> {}																						// 795 rem *** SHOOT ARROW ***
				800 -> ll = gameState.playerRoom																				// 800 l = l(1)
				805 -> k = 1																					// 805 for k = 1 to j9
				810 -> k1 = 1																					// 810 for k1 = 1 to 3
				815 -> if (map.nearByRoomHas(ll, k1, p[k])) nextLine = 895													// 815 if s(l,k1) = p(k) then 895
				820 -> { ++k1; if (k1 <= 3) nextLine = 815 }													// 820 next k1
				825 -> {}																						// 825 rem *** NO TUNNEL FOR ARROW ***
				830 -> ll = map.tunnelFrom(ll, gameState.fnB())																		// 830 l = s(l,fnb(1))
				835 -> nextLine = 900																			// 835 goto 900
				840 -> { ++k; if (k <= j9) nextLine = 810 }														// 840 next k
				845 -> console.println("MISSED")																		// 845 print "MISSED"
				850 -> ll = gameState.playerRoom																				// 850 l = l(1)
				855 -> {}																						// 855 rem *** MOVE WUMPUS ***
				860 -> f = gameState.wumpusMove(f, map, console)
				865 -> {}																						// 865 rem *** AMMO CHECK ***
				870 -> gameState.consumeArrow()																					// 870 a = a-1
				875 -> if (gameState.hasArrows()) nextLine = 885																// 875 if a > 0 then 885
				880 -> f = -1																					// 880 f = -1
				885 -> returnFromGosub()																		// 885 return
				890 -> {}																						// 890 rem *** SEE IF ARROW IS AT l(1) OR AT l(2)
				895 -> ll = p[k]																				// 895 l = p(k)
				900 -> if (ll != gameState.wumpusRoom) nextLine = 920															// 900 if l <> l(2) then 920
				905 -> console.println("AHA! YOU GOT THE WUMPUS!")														// 905 print "AHA! YOU GOT THE WUMPUS!"
				910 -> f = 1																					// 910 f = 1
				915 -> returnFromGosub()																		// 915 return
				920 -> if (ll != gameState.playerRoom) nextLine = 840															// 920 if l <> l(1) then 840
				925 -> console.println ("OUCH! ARROW GOT YOU!")															// 925 print "OUCH! ARROW GOT YOU!"
				930 -> nextLine = 880																			// 930 goto 880
				975 -> {}																						// 975 rem *** MOVE ROUTINE ***
				980 -> f = 0																					// 980 f = 0
				985 -> console.print("WHERE TO ")																		// 985 print "WHERE TO";
				990 -> ll = console.readInt()																			// 990 input l
				995 -> if (ll < 1) nextLine = 985																// 995 if l < 1 then 985
				1000 -> if (ll > 20) nextLine = 985																// 1000 if l > 20 then 985
				1005 -> k = 1																					// 1005 for k = 1 to 3
				1010 -> {}																						// 1010 rem *** CHECK IF LEGAL MOVE ***
				1015 -> if (map.nearByRoomHas(gameState.playerRoom, k, ll)) nextLine = 1045													// 1015 if s(l(1),k) = l then 1045
				1020 -> { ++k; if (k <= 3) nextLine = 1010 }													// 1020 next k
				1025 -> if (ll == gameState.playerRoom) nextLine = 1045															// 1025 if l = l(1) then 1045
				1030 -> console.print("NOT POSSIBLE - ")																// 1030 print "NOT POSSIBLE -";
				1035 -> nextLine = 985																			// 1035 goto 985
				1040 -> {}																						// 1040 rem *** CHECK FOR HAZARDS ***
				1045 -> gameState.playerRoom = ll																				// 1045 l(1) = l
				1050 -> {}																						// 1050 rem *** WUMPUS ***
				1055 -> if (ll != gameState.wumpusRoom) nextLine = 1090															// 1055 if l <> l(2) then 1090
				1060 -> console.println("... OOPS! BUMPED A WUMPUS!")													// 1060 print "... OOPS! BUMPED A WUMPUS!"
				1065 -> {}																						// 1065 rem *** MOVE WUMPUS ***
				1070 -> f = gameState.wumpusMove(f, map, console)
				1075 -> if (f == 0) nextLine = 1090																// 1075 if f = 0 then 1090
				1080 -> returnFromGosub()																		// 1080 return
				1085 -> {}																						// 1085 rem *** PIT ***
				1090 -> if (ll == gameState.pit1) nextLine = 1100															// 1090 if l = l(3) then 1100
				1095 -> if (ll != gameState.pit2) nextLine = 1120															// 1095 if l <> l(4) then 1120
				1100 -> console.println("YYYYIIIIEEEE . . . FELL IN PIT")												// 1100 print "YYYYIIIIEEEE . . . FELL IN PIT"
				1105 -> f = -1																					// 1105 f = -1
				1110 -> returnFromGosub()																		// 1110 return
				1115 -> {}																						// 1115 rem *** BATS ***
				1120 -> if (ll == gameState.bat1) nextLine = 1130															// 1120 if l = l(5) then 1130
				1125 -> if (ll != gameState.bat2) nextLine = 1145															// 1125 if l <> l(6) then 1145
				1130 -> console.println("ZAP--SUPER BAT SNATCH! ELSEWHEREVILLE FOR YOU!")								// 1130 print "ZAP--SUPER BAT SNATCH! ELSEWHEREVILLE FOR YOU!"
				1135 -> ll = gameState.fnA()																				// 1135 l = fna(1)
				1140 -> nextLine = 1045																			// 1140 goto 1045
				1145 -> returnFromGosub()																		// 1145 return
				1150 -> {}																						// 1150 end
				}
				currentLine = nextLine
			}
		} catch (e: Throwable) {
			e.printStackTrace()
		}
	}

	private fun getAction(): Int {
		var result = 0
		do {
			console.print("SHOOT OR MOVE (S-M) ")                                                            // 675 print "SHOOT OR MOVE (S-M)";
			result = when (console.readln()) {
				'S', 's' -> 1
				'M', 'm' -> 2
				else -> 0
			}
			} while (result == 0)
		return result
	}

	private fun printRoomDescription() {
		val ll = gameState.playerRoom
		console.println("")
		(2..6).forEach { j ->
			(1..3).forEach { k ->
				if (!map.nearByRoomHas(gameState.playerRoom, k, gameState.locations[j])) return@forEach
				when (j - 1) {
					1 -> console.println("I SMELL A WUMPUS!")
					2, 3 -> console.println("I FEEL A DRAFT")
					4, 5 -> console.println("BATS NEARBY!")
				}
			}
		}
		console.print("YOUR ARE IN ROOM ")
		console.println(gameState.playerRoom)
		console.print("TUNNELS LEAD TO ")
		console.print(map.tunnelFrom(ll, 1))
		console.print(" ")
		console.print(map.tunnelFrom(ll, 2))
		console.print(" ")
		console.println(map.tunnelFrom(ll, 3))
		console.println("")
	}

	private fun gosub(gosubLine: Int, lineToReturnTo: Int) {
		nextLine = gosubLine
		returnLine.addLast(lineToReturnTo)
	}
	private fun returnFromGosub() {
		nextLine = if (returnLine.isEmpty())
			1151
		else
			returnLine.pollLast()
	}

	private fun needInstruction(): Boolean {
		console.print("INSTRUCTIONS (Y-N) ")                                                        // 15 print "INSTRUCTIONS (Y-N)";
		val iS = console.readln()                                                                        // 20 input i$
		return (iS != 'N' && iS != 'n')
	}

	private fun giveInstructions() {
		console.println("WELCOME TO 'HUNT THE WUMPUS'")
		console.println("  THE WUMPUS LIVES IN A CAVE OF 20 ROOMS. EACH ROOM")
		console.println("HAS 3 TUNNELS LEADING TO OTHER ROOMS. (LOOK AT A")
		console.println("DODECAHEDRON TO SEE HOW THIS WORKS-IF YOU DON'T KNOW")
		console.println("WHAT A DODECAHEDRON IS, ASK SOMEONE)")
		console.println("")
		console.println("     HAZARDS:")
		console.println(" BOTTOMLESS PITS - TWO ROOMS HAVE BOTTOMLESS PITS IN THEM")
		console.println("     IF YOU GO THERE, YOU FALL INTO THE PIT (& LOSE!)")
		console.println(" SUPER BATS - TWO OTHER ROOMS HAVE SUPER BATS. IF YOU")
		console.println("     GO THERE, A BAT GRABS YOU AND TAKES YOU TO SOME OTHER")
		console.println("     ROOM AT RANDOM. (WHICH MAY BE TROUBLESOME)")
		console.input("HIT RETURN TO CONTINUE")
		console.println("     WUMPUS:")
		console.println(" THE WUMPUS IS NOT BOTHERED BY HAZARDS (HE HAS SUCKER")
		console.println(" FEET AND IS TOO BIG FOR A BAT TO LIFT).  USUALLY")
		console.println(" HE IS ASLEEP.  TWO THINGS WAKE HIM UP: YOU SHOOTING AN")
		console.println("ARROW OR YOU ENTERING HIS ROOM.")
		console.println("     IF THE WUMPUS WAKES HE MOVES (P=.75) ONE ROOM")
		console.println(" OR STAYS STILL (P=.25).  AFTER THAT, IF HE IS WHERE YOU")
		console.println(" ARE, HE EATS YOU UP AND YOU LOSE!")
		console.println("")
		console.println("     YOU:")
		console.println(" EACH TURN YOU MAY MOVE OR SHOOT A CROOKED ARROW")
		console.println("   MOVING:  YOU CAN MOVE ONE ROOM (THRU ONE TUNNEL)")
		console.println("   ARROWS:  YOU HAVE 5 ARROWS.  YOU LOSE WHEN YOU RUN OUT")
		console.println("   EACH ARROW CAN GO FROM 1 TO 5 ROOMS. YOU AIM BY TELLING")
		console.println("   THE COMPUTER THE ROOM#S YOU WANT THE ARROW TO GO TO.")
		console.println("   IF THE ARROW CAN'T GO THAT WAY (IF NO TUNNEL) IT MOVES")
		console.println("   AT RANDOM TO THE NEXT ROOM.")
		console.println("     IF THE ARROW HITS THE WUMPUS, YOU WIN.")
		console.println("     IF THE ARROW HITS YOU, YOU LOSE.")
		console.input("HIT RETURN TO CONTINUE")
		console.println("    WARNINGS:")
		console.println("     WHEN YOU ARE ONE ROOM AWAY FROM A WUMPUS OR HAZARD,")
		console.println("     THE COMPUTER SAYS:")
		console.println(" WUMPUS:  'I SMELL A WUMPUS'")
		console.println(" BAT   :  'BATS NEARBY'")
		console.println(" PIT   :  'I FEEL A DRAFT'")
		console.println("")
	}
}

