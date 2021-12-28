class GameMap {

	fun nearByRoomHas(room: Int, tunnel: Int, target: Int): Boolean {
		return tunnelFrom(room, tunnel) == target
	}

    fun tunnelFrom(room: Int, tunnel: Int): Int {
		return data[room][tunnel]
	}

	fun roomHasPathTo(room: Int, target: Int): Boolean {
		for (k in 1..3) {
			if (nearByRoomHas(room, k, target)) return true
		}
		return false
	}

	private val data = arrayOf(arrayOf(0,0,0,0),
	arrayOf(0,2,5,8), arrayOf(0,1,3,10), arrayOf(0,2,4,12), arrayOf(0,3,5,14), arrayOf(0,1,4,6),
	arrayOf(0,5,7,15), arrayOf(0,6,8,17), arrayOf(0,1,7,9), arrayOf(0,8,10,18), arrayOf(0,2,9,11),
	arrayOf(0,10,12,19), arrayOf(0,3,11,13), arrayOf(0,12,14,20), arrayOf(0,4,13,15), arrayOf(0,6,14,16),
	arrayOf(0,15,17,20), arrayOf(0,7,16,18), arrayOf(0,9,17,19), arrayOf(0,11,18,20), arrayOf(0,13,16,19))
}