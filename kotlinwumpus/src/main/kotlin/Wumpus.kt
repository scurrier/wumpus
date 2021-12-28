import wumpus.UI
import java.util.Random

class Wumpus {
	private var won: Boolean = false
	var random = Random()
	var ui = UI(Console())
	var exitOnWin = false
	var gameState = GameState(random)
	val map = GameMap()

	fun main() {
		try {
			if (ui.askIfInstructionsNeeded()) {
				ui.giveInstructions()
			}
			gameState.intializeLocations()
			while (!(exitOnWin && won)) {
				playGame()
				val useNewSetup = askIfNewSetup()
				gameState.resetGame(useNewSetup)
			}
		} catch (e: Throwable) {
			e.printStackTrace()
		}
	}

	private fun playGame() {
		gameState.resetArrows()
		ui.console.println("HUNT THE WUMPUS")
		var f = 0
		do {
			printRoomDescription()
			when (getAction()) {
				1 -> f = shootArrow()
				2 -> f = movePlayerToRoom(askForValidDestinationRoom())
			}
		} while (f == 0)
		if (f < 0) {
			ui.console.println("HA HA HA - YOU LOSE!")
		} else {
			ui.console.println("HEE HEE HEE - THE WUMPUS'LL GET YOU NEXT TIME!!")
			won = true
		}
	}

	private fun askIfNewSetup(): Boolean {
		ui.console.print("SAME SETUP (Y-N)")                                                        // 355 print "SAME SETUP (Y-N)";
		val iS = ui.console.readln()                                                                    // 360 input i$
		val useNewSetup = iS != 'Y' && iS != 'y'
		return useNewSetup
	}

	private fun askForValidDestinationRoom(): Int {
		var temp: Int
		do {
			temp = askForDestinationRoom()
			val isValidRoom = map.roomHasPathTo(gameState.playerRoom, temp) || temp == gameState.playerRoom
			if (!isValidRoom) {
				ui.console.print("NOT POSSIBLE - ")
			}
		} while (!isValidRoom)
		return temp
	}

	private fun movePlayerToRoom(newPlayerRoom: Int): Int {
		gameState.playerRoom = newPlayerRoom
		if (newPlayerRoom == gameState.wumpusRoom) {
			ui.console.println("... OOPS! BUMPED A WUMPUS!")
			val f = gameState.wumpusMove(map, ui.console)
			if (f != 0) return f
		}
		if ((newPlayerRoom == gameState.pit1 || newPlayerRoom == gameState.pit2)) {
			ui.console.println("YYYYIIIIEEEE . . . FELL IN PIT")
			return -1
		}
		if ((newPlayerRoom == gameState.bat1 || newPlayerRoom == gameState.bat2)) {
			ui.console.println("ZAP--SUPER BAT SNATCH! ELSEWHEREVILLE FOR YOU!")
			return movePlayerToRoom(gameState.fnA())
		}
		return 0
	}

	private fun askForDestinationRoom(): Int {
		var ll1: Int
		do {
			ui.console.print("WHERE TO ")
			ll1 = ui.console.readInt()
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
				ui.console.println("AHA! YOU GOT THE WUMPUS!")
				f1 = 1
			}
			if (ll1 == gameState.playerRoom) {
				ui.console.println("OUCH! ARROW GOT YOU!")
				f1 = -1
			}
		}
		if (f1 == 0) {
			ui.console.println("MISSED")
			f1 = gameState.wumpusMove(map, ui.console)
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
			ui.console.print("ROOM # ")
			nextRoom = ui.console.readInt()
			val invalidPath = (k > 2) && path[k] == nextRoom
			if (invalidPath) ui.console.println("ARROWS AREN'T THAT CROOKED - TRY ANOTHER ROOM")
		} while (invalidPath)
		return nextRoom
	}

	private fun askForNumberOfRooms(): Int {
		var a: Int
		do {
			ui.console.print("NO. OF ROOMS (1-5) ")
			a = ui.console.readInt()
		} while (a < 1 || a > 5)
		return a
	}

	private fun getAction(): Int {
		var result: Int
		do {
			ui.console.print("SHOOT OR MOVE (S-M) ")                                                            // 675 print "SHOOT OR MOVE (S-M)";
			result = when (ui.console.readln()) {
				'S', 's' -> 1
				'M', 'm' -> 2
				else -> 0
			}
			} while (result == 0)
		return result
	}

	private fun printRoomDescription() {
		val ll = gameState.playerRoom
		ui.console.println("")
		(2..6).forEach { j ->
			(1..3).forEach { k ->
				if (!map.nearByRoomHas(gameState.playerRoom, k, gameState.locations[j])) return@forEach
				when (j - 1) {
					1 -> ui.console.println("I SMELL A WUMPUS!")
					2, 3 -> ui.console.println("I FEEL A DRAFT")
					4, 5 -> ui.console.println("BATS NEARBY!")
				}
			}
		}
		ui.console.print("YOUR ARE IN ROOM ")
		ui.console.println(gameState.playerRoom)
		ui.console.print("TUNNELS LEAD TO ")
		ui.console.print(map.tunnelFrom(ll, 1))
		ui.console.print(" ")
		ui.console.print(map.tunnelFrom(ll, 2))
		ui.console.print(" ")
		ui.console.println(map.tunnelFrom(ll, 3))
		ui.console.println("")
	}
}

