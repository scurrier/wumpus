package wumpus

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class GameResultTest {
    private val testObj = GameResult()
    @Test
    fun startPlaying() {
        testObj.startPlaying()
        assertTrue(testObj.stillPlaying())
        assertFalse(testObj.hasWon())
        assertFalse(testObj.hasLost())
    }

    @Test
    fun updateGameResult() {
        assertTrue(testObj.stillPlaying())
        assertFalse(testObj.hasWon())
        assertFalse(testObj.hasLost())
        testObj.win()
        assertFalse(testObj.stillPlaying())
        assertTrue(testObj.hasWon())
        assertFalse(testObj.hasLost())
        testObj.lose()
        assertFalse(testObj.stillPlaying())
        assertFalse(testObj.hasWon())
        assertTrue(testObj.hasLost())
    }


}