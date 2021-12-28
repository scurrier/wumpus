import java.util.Random

class GameState(private val random: Random) {
    fun resetArrows() {
		arrowCount = 5
	}

    fun consumeArrow() {
		arrowCount -= 1
	}

    fun hasArrows(): Boolean {
		return arrowCount > 0
	}

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
	var initialLocations = Array(7) {0}

	fun wumpusMove(map: GameMap, console: Console): Int {
		var k2 = fnC()
		if (k2 != 4) wumpusRoom = map.tunnelFrom(wumpusRoom, k2)
		if (wumpusRoom == playerRoom) {
			console.println("TSK TSK TSK - WUMPUS GOT YOU!")
			return -1
		}
		return 0
	}

	fun fnA(): Int {
		return random.nextInt(20) + 1
	}
	fun fnB(): Int {
		return random.nextInt(3) + 1
	}
	fun fnC(): Int {
		return random.nextInt(4) + 1
	}

	fun intializeLocations() {
		do {
			setNewLocations(generateLocations())
		} while (hasCrossovers())
	}

	fun generateLocations(): Array<Int> {
		val newLocations = Array(7) { 0 }
		for (j in 1..6) {
			newLocations[j] = fnA()
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

	fun restoreInitialLocations() {
		locations = initialLocations.clone()
	}
}