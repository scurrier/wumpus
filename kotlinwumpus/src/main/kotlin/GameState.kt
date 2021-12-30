import wumpus.Chaos
import wumpus.UI
import java.util.Random

class GameState(
	private val chaos: Chaos = Chaos(Random()),
	private val exitOnWin: Boolean = false) {
	fun resetArrows() {
		arrowCount = 5
	}

    fun consumeArrow() {
		arrowCount -= 1
	}

    fun hasArrows(): Boolean {
		return arrowCount > 0
	}

	private var gameResult: Int = 0
	var playerRoom: Int
		get() = locations[1]
		set(v) {locations[1] = v}
	var wumpusRoom: Int
		get() = locations[2]
		set(v) {locations[2] = v}
	val pit1: Int
		get() = locations[3]
	val pit2: Int
		get() = locations[4]
	val bat1: Int
		get() = locations[5]
	val bat2: Int
		get() = locations[6]

	private var arrowCount: Int = 5
	var locations = Array(7) {0}
	private var initialLocations = Array(7) {0}

	fun wumpusMove(map: GameMap, ui: UI): Int {
		val k2 = chaos.pickWumpusMovement()
		if (k2 != 4) wumpusRoom = map.tunnelFrom(wumpusRoom, k2)
		if (wumpusRoom == playerRoom) {
			ui.reportWumpusAtePlayer()
			return -1
		}
		return 0
	}

	fun intializeLocations() {
		do {
			setNewLocations(generateLocations())
		} while (hasCrossovers())
	}

	fun generateLocations(): Array<Int> {
		val newLocations = Array(7) { 0 }
		for (j in 1..6) {
			newLocations[j] = chaos.pickRoom()
		}
		return newLocations
	}

	fun setNewLocations(newLocations: Array<Int>) {
		locations = newLocations.clone()
		initialLocations = newLocations.clone()

	}

	fun hasCrossovers(): Boolean {
		for (j in 1..6) {
			for (k in 1..6) {
				if (j != k && locations[j] == locations[k]) {
					return true
				}
			}
		}
		return false
	}

	private fun restoreInitialLocations() {
		locations = initialLocations.clone()
	}

	fun resetGame(useNewSetup: Boolean) {
		if (useNewSetup) intializeLocations()
		else restoreInitialLocations()

	}

	fun updateGameResult(newGameResult: Int) {
		gameResult = newGameResult
	}
	fun startPlaying() = updateGameResult(0)
	fun stillPlaying(): Boolean = gameResult == 0
	fun hasLost(): Boolean = gameResult < 0
	fun hasWon(): Boolean = gameResult > 0
	fun playAgain() = !(exitOnWin && hasWon())

	fun followArrowPath(path: Array<Int>, ui: UI, map: GameMap) {
		var arrowRoom = playerRoom
		for (pathRoom in path) {
			arrowRoom = nextArrowRoom(arrowRoom, pathRoom, map)
			if (arrowRoom == wumpusRoom) {
				ui.reportShotWumpus()
				return updateGameResult(1)
			}
			if (arrowRoom == playerRoom) {
				ui.reportShotSelf()
				return updateGameResult(-1)
			}
		}

		ui.reportMissedShot()
		consumeArrow()
		if (!hasArrows()) {
			return updateGameResult(-1)
		}
		return updateGameResult(wumpusMove(map, ui))
	}

	fun nextArrowRoom(start: Int, destination: Int, map: GameMap) = if (map.roomHasPathTo(start, destination)) {
		destination
	} else {
		map.tunnelFrom(start, chaos.pickTunnel())
	}

	fun pickRoom(): Int = chaos.pickRoom()
}