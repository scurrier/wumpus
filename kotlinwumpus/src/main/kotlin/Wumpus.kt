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
	var gameState = GameState(random)
	val map = GameMap()

	fun main() {
		try {
			currentLine = 5
			var ll = 0
			var f = 0

			var j = 0
			var k = 0
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
				270 -> when(getAction()) {1 -> nextLine = 280; 2 -> nextLine = 300}										// 270 on o goto 280,300
				275 -> {}																						// 275 rem *** SHOOT ***
				280 -> f = shootArrow()
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

	private fun shootArrow(): Int {
		val j9 = askForNumberOfRooms()
		val p = getArrowPath(j9)
		return followArrowPath(p, j9)
	}

	private fun followArrowPath(p: Array<Int>, j9: Int):Int {
		var ll1 = gameState.playerRoom
		var f1 = 0
		for (k in 1..j9) {
			ll1 = getNextRoomFromPath(ll1, k, p)
			if (ll1 == gameState.wumpusRoom) {
				console.println("AHA! YOU GOT THE WUMPUS!")
				f1 = 1
			}
			if (ll1 == gameState.playerRoom) {
				console.println("OUCH! ARROW GOT YOU!")
				f1 = -1
			}
		}
		if (f1 == 0) {
			console.println("MISSED")
			ll1 = gameState.playerRoom
			f1 = gameState.wumpusMove(f1, map, console)
			gameState.consumeArrow()
			if (!gameState.hasArrows()) f1 = -1
		}
		return f1
	}

	private fun getNextRoomFromPath(ll: Int, k: Int, p: Array<Int>) = if (map.roomHasPathTo(ll, p[k])) {
		p[k]
	} else {
		map.tunnelFrom(ll, gameState.fnB())
	}

	private fun getArrowPath(roomCount: Int): Array<Int> {
		val path = Array(roomCount + 1) {0}
		for (k in 1..roomCount) {
			path[k] = getNextRoom(path, k)
		}
		return path
	}

	private fun getNextRoom(path: Array<Int>, k: Int): Int {
		var nextRoom: Int
		do {
			console.print("ROOM # ")
			nextRoom = console.readInt()
			val invalidPath = (k > 2) && path[k] == nextRoom
			if (invalidPath) console.println("ARROWS AREN'T THAT CROOKED - TRY ANOTHER ROOM")
		} while (invalidPath)
		return nextRoom
	}

	private fun askForNumberOfRooms(): Int {
		var a: Int
		do {
			console.print("NO. OF ROOMS (1-5) ")
			a = console.readInt()
		} while (a < 1 || a > 5)
		return a
	}

	private fun getAction(): Int {
		var result: Int
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

