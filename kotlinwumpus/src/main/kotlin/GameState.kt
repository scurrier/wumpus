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
	val locations = Array(7) {0}
	val initialLocations = Array(7) {0}

	fun wumpusMove(f: Int, map: GameMap, console: Console): Int {
		var k2 = 0
		var f1 = f
		k2 = fnC()
		if (k2 != 4) wumpusRoom = map.tunnelFrom(wumpusRoom, k2)
		if (wumpusRoom == playerRoom) {
			console.println("TSK TSK TSK - WUMPUS GOT YOU!")
			f1 = -1
		}
		return f1
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
}