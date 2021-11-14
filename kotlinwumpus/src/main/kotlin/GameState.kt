class GameState {
    fun resetArrows() {
		arrowCount = 5
	}

    fun consumeArrow() {
		arrowCount -= 1
	}

    fun hasArrows(): Boolean {
		return arrowCount > 0
	}

	private var arrowCount: Int = 5
}