package wumpus

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ArrowTest {
    private val gameMap = GameMap()
    private val chaos = mockk<Chaos>()
    private val gameResult = GameResult()
    private val player = Player(listOf(), gameMap, gameResult)
    private val wumpus = Wumpus(chaos, gameMap, gameResult)
    private val testObj = Arrow(chaos, player, wumpus, gameMap, gameResult)
    @Test
    fun nextArrowRoom() {
        assertEquals(2, testObj.nextArrowRoom(gameMap.room(3), 2))
        every { chaos.pickTunnel() } returns 1
        assertEquals(2, testObj.nextArrowRoom(gameMap.room(3), 5))
    }

}