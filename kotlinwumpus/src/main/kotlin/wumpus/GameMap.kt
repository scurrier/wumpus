package wumpus

internal class GameMap {

	fun tunnelFrom(room: Int, tunnel: Int): Int {
		return room(room)[tunnel]
	}

	fun roomHasPathTo(room: Int, target: Int): Boolean {
		return target in room(room)
	}

	fun room(i: Int): Room = data[i-1]

	private val data = arrayOf(
	Room(1,2,5,8), Room(2,1,3,10), Room(3,2,4,12), Room(4,3,5,14), Room(5,1,4,6),
	Room(6,5,7,15), Room(7,6,8,17), Room(8,1,7,9), Room(9,8,10,18), Room(10,2,9,11),
	Room(11,10,12,19), Room(12,3,11,13), Room(13,12,14,20), Room(14,4,13,15), Room(15,6,14,16),
	Room(16,15,17,20), Room(17,7,16,18), Room(18,9,17,19), Room(19,11,18,20), Room(20,13,16,19))
}