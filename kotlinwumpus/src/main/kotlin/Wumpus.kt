import wumpus.Move
import wumpus.Shoot
import wumpus.UI
import java.util.Random

class Wumpus {
	var ui = UI(Console())
	var gameState = GameState(Random())
	val map = GameMap()

	fun main() {
		try {
			ui.provideInstructions()
			gameState.intializeLocations()
			do {
				playGame()
				val useNewSetup = ui.askIfNewSetup()
				gameState.resetGame(useNewSetup)
			} while (gameState.playAgain())
		} catch (e: Throwable) {
			e.printStackTrace()
		}
	}

	private fun playGame() {
		ui.showTitle()
		gameState.resetArrows()
		gameState.startPlaying()
		do {
			ui.printRoomDescription(gameState, map)
			when (ui.askForAction()) {
				Shoot -> gameState.updateGameResult(shootArrow())
				Move -> gameState.updateGameResult(movePlayerToRoom(askForValidDestinationRoom()))
			}
		} while (gameState.stillPlaying())
		if (gameState.hasLost()) {
			ui.console.println("HA HA HA - YOU LOSE!")
		} else {
			ui.console.println("HEE HEE HEE - THE WUMPUS'LL GET YOU NEXT TIME!!")
		}
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
}

