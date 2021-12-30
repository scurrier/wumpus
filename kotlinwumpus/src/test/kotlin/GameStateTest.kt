import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import wumpus.Chaos
import wumpus.UI

internal class GameStateTest {
    private val chaos = mockk<Chaos>()
    private val testObj = GameState(chaos)
    private val gameMap = GameMap()
    private val ui = mockk<UI>(relaxed = true)

    @Test
    fun initializeLocations() {
        // first six return values that cross over, second values get used (plus 1)
        every { chaos.pickRoom() } returnsMany listOf(2, 2, 2, 2, 2, 2, 3, 4, 5, 6, 7, 8)
        testObj.intializeLocations()
        assertEquals(3, testObj.playerRoom)
        assertEquals(4, testObj.wumpusRoom)
    }

    @Test
    fun hasCrossover() {
        testObj.setNewLocations(arrayOf(0, 1, 2, 3, 4, 5, 6))
        assertFalse(testObj.hasCrossovers())
        testObj.setNewLocations(arrayOf(0, 1, 2, 3, 4, 5, 1))
        assertTrue(testObj.hasCrossovers())
        testObj.setNewLocations(arrayOf(0, 6, 2, 3, 4, 5, 6))
        assertTrue(testObj.hasCrossovers())
        testObj.setNewLocations(arrayOf(0, 6, 2, 3, 3, 5, 6))
        assertTrue(testObj.hasCrossovers())
    }

    @Test
    fun generateLocations() {
        every { chaos.pickRoom() }.returnsMany(3, 1, 4, 1, 5, 9)
        val result = testObj.generateLocations()
        assertArrayEquals(arrayOf(0, 3, 1, 4, 1, 5, 9), result)
    }

    @Test
    fun setNewLocations() {
        testObj.setNewLocations(arrayOf(0, 3, 4, 5, 6, 7, 8))
        assertEquals(3, testObj.playerRoom)
        assertEquals(4, testObj.wumpusRoom)
        assertEquals(5, testObj.pit1)
        assertEquals(6, testObj.pit2)
        assertEquals(7, testObj.bat1)
        assertEquals(8, testObj.bat2)
    }

    @Test
    fun startPlaying() {
        testObj.startPlaying()
        assertTrue(testObj.stillPlaying())
        assertFalse(testObj.hasWon())
        assertFalse(testObj.hasLost())
    }

    @Test
    fun updateGameResult() {
        testObj.updateGameResult(0)
        assertTrue(testObj.stillPlaying())
        assertFalse(testObj.hasWon())
        assertFalse(testObj.hasLost())
        testObj.updateGameResult(1)
        assertFalse(testObj.stillPlaying())
        assertTrue(testObj.hasWon())
        assertFalse(testObj.hasLost())
        testObj.updateGameResult(-1)
        assertFalse(testObj.stillPlaying())
        assertFalse(testObj.hasWon())
        assertTrue(testObj.hasLost())
    }

    @Test
    fun followArrowPath_miss() {
        // need to train random move of wumpus after miss
        every { chaos.pickWumpusMovement() } returns 4
        testObj.setNewLocations(arrayOf(0, 1, 10, 11, 12, 13, 14))
        testObj.followArrowPath(arrayOf(2, 3, 4), ui, gameMap)
        verify { ui.reportMissedShot() }
        assertTrue(testObj.stillPlaying())
    }

    @Test
    fun followArrowPath_outOfAmmo() {
        // need to train random move of wumpus after miss
        every { chaos.pickWumpusMovement() } returns 4

        repeat(4) { testObj.consumeArrow() }
        testObj.setNewLocations(arrayOf(0, 1, 10, 11, 12, 13, 14))
        testObj.followArrowPath(arrayOf(2, 3, 4), ui, gameMap)
        verify { ui.reportMissedShot() }
        assertTrue(testObj.hasLost())
    }

    @Test
    fun followArrowPath_wumpusEatsPlayerAfterMiss() {
        // need to train random move of wumpus after miss
        every { chaos.pickWumpusMovement() } returns 1

        testObj.setNewLocations(arrayOf(0, 1, 5, 11, 12, 13, 14))
        testObj.followArrowPath(arrayOf(2), ui, gameMap)
        verify { ui.reportMissedShot() }
        verify { ui.reportWumpusAtePlayer() }
        assertTrue(testObj.hasLost())
    }

    @Test
    fun followArrowPath_shootSelf() {
        testObj.setNewLocations(arrayOf(0, 1, 10, 11, 12, 13, 14))
        testObj.followArrowPath(arrayOf(5, 6, 7, 8, 1), ui, gameMap)
        verify { ui.reportShotSelf() }
        assertTrue(testObj.hasLost())
    }

    @Test
    fun followArrowPath_shootWumpus() {
        testObj.setNewLocations(arrayOf(0, 1, 3, 11, 12, 13, 14))
        testObj.followArrowPath(arrayOf(2, 3), ui, gameMap)
        verify { ui.reportShotWumpus() }
        assertTrue(testObj.hasWon())
    }

    @Test
    fun nextArrowRoom() {
        assertEquals(2, testObj.nextArrowRoom(3, 2, gameMap))
        every { chaos.pickTunnel() } returns 1
        assertEquals(2, testObj.nextArrowRoom(3, 5, gameMap))
    }

    @Test
    fun arrows() {
        val arrowsConsumed = consumeAllArrows()
        assertEquals(5, arrowsConsumed)
        testObj.resetArrows()
        val resetArrows = consumeAllArrows()
        assertEquals(5, resetArrows)
        assertEquals(0, consumeAllArrows(), "Just proving that there are no arrows after consumeAllArrows()")
    }

    private fun consumeAllArrows(): Int {
        var c = 0
        while (testObj.hasArrows()) {
            c++
            testObj.consumeArrow()
        }
        return c
    }
}