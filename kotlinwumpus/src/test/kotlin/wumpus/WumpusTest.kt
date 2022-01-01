package wumpus

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class WumpusTest {
    private val gameResult = GameResult()
    private val chaos = mockk<Chaos>()
    private val ui = mockk<UI>(relaxed = true)
    private val gameMap = GameMap()
    private val testObj = Wumpus(chaos, gameMap, gameResult)
    private val player = Player(listOf(), gameMap, gameResult)

    init {
        testObj.room = gameMap.room(1)
        player.room = gameMap.room(2)
    }

    @Test
    fun noMove() {
        every { chaos.pickWumpusMovement() } returns 4
        testObj.wumpusMove(ui, player)
        assertTrue(testObj.room.isIndex(1))
        assertTrue(gameResult.stillPlaying())
    }

    @Test
    fun relocates() {
        every { chaos.pickWumpusMovement() } returns 2
        testObj.wumpusMove(ui, player)
        assertTrue(testObj.room.isIndex(5))
        assertTrue(gameResult.stillPlaying())
    }

    @Test
    fun catchesPlayer() {
        every { chaos.pickWumpusMovement() } returns 1
        testObj.wumpusMove(ui, player)
        assertTrue(testObj.room.isIndex(2))
        assertTrue(gameResult.hasLost())
    }
}