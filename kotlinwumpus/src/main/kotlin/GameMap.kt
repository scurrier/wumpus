class GameMap {

	fun nearByRoomHas(room: Int, tunnel: Int, target: Int): Boolean {
		return tunnelFrom(room, tunnel) == target
	}

    fun tunnelFrom(room: Int, tunnel: Int): Int {
		return data[room-1][tunnel-1]
	}

	fun roomHasPathTo(room: Int, target: Int): Boolean {
		for (k in 1..3) {
			if (nearByRoomHas(room, k, target)) return true
		}
		return false
	}

	private val data = arrayOf(
	arrayOf(2,5,8), arrayOf(1,3,10), arrayOf(2,4,12), arrayOf(3,5,14), arrayOf(1,4,6),
	arrayOf(5,7,15), arrayOf(6,8,17), arrayOf(1,7,9), arrayOf(8,10,18), arrayOf(2,9,11),
	arrayOf(10,12,19), arrayOf(3,11,13), arrayOf(12,14,20), arrayOf(4,13,15), arrayOf(6,14,16),
	arrayOf(15,17,20), arrayOf(7,16,18), arrayOf(9,17,19), arrayOf(11,18,20), arrayOf(13,16,19))
}