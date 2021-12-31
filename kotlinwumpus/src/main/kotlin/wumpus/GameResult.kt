package wumpus

class GameResult {
    private var gameResult: Int = 0
    private fun updateGameResult(newGameResult: Int) {
        gameResult = newGameResult
    }

    fun startPlaying() = updateGameResult(0)
    fun win() = updateGameResult(1)
    fun lose() = updateGameResult(-1)
    fun stillPlaying(): Boolean = gameResult == 0
    fun hasLost(): Boolean = gameResult < 0
    fun hasWon(): Boolean = gameResult > 0
}