package wumpus

class Arrows {
    var count: Int = 5

    fun resetArrows() {
        count = 5
    }

    fun consumeArrow() {
        count -= 1
    }

    fun hasArrows(): Boolean {
        return count > 0
    }
}
