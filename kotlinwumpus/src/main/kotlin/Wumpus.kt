import java.util.ArrayDeque
import java.util.Deque
import java.util.Random

class Wumpus {
	private var won: Boolean = false
	private var currentLine: Int = 0
	private var nextLine: Int = 0
    var random = Random()
	var console = Console()
	var exitOnWin = false
	var gameState = GameState(random)
	val map = GameMap()

	fun main() {
		try {
			currentLine = 5
			var f = 0

			while (currentLine <= 1150 && !(exitOnWin && won)) {
				nextLine = currentLine + 1
				when (currentLine) {
				15 -> if (needInstruction()) {giveInstructions()}
				170 -> gameState.intializeLocations()
				230 -> {
					gameState.resetArrows()
					console.println("HUNT THE WUMPUS")
				}
				255 -> {
					do {
						printRoomDescription()
						when (getAction()) {
							1 -> f = shootArrow()
							2 -> f = movePlayerToRoom(askForValidDestinationRoom())
						}
					} while (f == 0)
					if (f < 0) {
						console.println("HA HA HA - YOU LOSE!")
					} else {
						console.println("HEE HEE HEE - THE WUMPUS'LL GET YOU NEXT TIME!!")
						won = true
					}
				}
				340 -> gameState.restoreInitialLocations()
				355 -> {
					val useNewSetup = askIfNewSetup()
					if (useNewSetup) nextLine = 170                                                // 365 if (i$ <> "Y") and (i$ <> "y") then 170
					else nextLine = 230                                                                            // 370 goto 230
				}
				}
				currentLine = nextLine
			}
		} catch (e: Throwable) {
			e.printStackTrace()
		}
	}

	private fun askIfNewSetup(): Boolean {
		console.print("SAME SETUP (Y-N)")                                                        // 355 print "SAME SETUP (Y-N)";
		val iS = console.readln()                                                                    // 360 input i$
		val useNewSetup = iS != 'Y' && iS != 'y'
		return useNewSetup
	}

	private fun askForValidDestinationRoom(): Int {
		var temp: Int
		do {
			temp = askForDestinationRoom()
			val isValidRoom = map.roomHasPathTo(gameState.playerRoom, temp) || temp == gameState.playerRoom
			if (!isValidRoom) {
				console.print("NOT POSSIBLE - ")
			}
		} while (!isValidRoom)
		return temp
	}

	private fun movePlayerToRoom(newPlayerRoom: Int): Int {
		gameState.playerRoom = newPlayerRoom
		if (newPlayerRoom == gameState.wumpusRoom) {
			console.println("... OOPS! BUMPED A WUMPUS!")
			val f = gameState.wumpusMove(map, console)
			if (f != 0) return f
		}
		if ((newPlayerRoom == gameState.pit1 || newPlayerRoom == gameState.pit2)) {
			console.println("YYYYIIIIEEEE . . . FELL IN PIT")
			return -1
		}
		if ((newPlayerRoom == gameState.bat1 || newPlayerRoom == gameState.bat2)) {
			console.println("ZAP--SUPER BAT SNATCH! ELSEWHEREVILLE FOR YOU!")
			return movePlayerToRoom(gameState.fnA())
		}
		return 0
	}

	private fun askForDestinationRoom(): Int {
		var ll1: Int
		do {
			console.print("WHERE TO ")
			ll1 = console.readInt()
		} while (ll1 < 1 || ll1 > 20)
		return ll1
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
			f1 = gameState.wumpusMove(map, console)
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

