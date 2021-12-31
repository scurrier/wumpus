package wumpus

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class WumpusTest {
    private val testObj = Wumpus()
    private val chaos = mockk<Chaos>()
    private val gameResult = GameResult()
    private val gameState = GameState(gameResult, chaos, true)
    private val ui = mockk<UI>(relaxed = true)
    private val gameMap = gameState.map

    init {
        gameState.setNewLocations(listOf(2, 20, 20, 20, 20, 20))
        testObj.room = gameMap.room(1)
    }

    @Test
    fun noMove() {
        every { chaos.pickWumpusMovement() } returns 4
        testObj.wumpusMove(ui, gameState)
        assertTrue(testObj.room.isIndex(1))
        assertTrue(gameResult.stillPlaying())
    }

    @Test
    fun relocates() {
        every { chaos.pickWumpusMovement() } returns 2
        testObj.wumpusMove(ui, gameState)
        assertTrue(testObj.room.isIndex(5))
        assertTrue(gameResult.stillPlaying())
    }

    @Test
    fun catchesPlayer() {
        every { chaos.pickWumpusMovement() } returns 1
        testObj.wumpusMove(ui, gameState)
        assertTrue(testObj.room.isIndex(2))
        assertTrue(gameResult.hasLost())
    }
}