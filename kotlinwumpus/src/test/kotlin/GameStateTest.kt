import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import wumpus.Chaos
import wumpus.UI
import java.util.Random

internal class GameStateTest {
    private val testObj = GameState(Chaos(Random(0)))
    private val gameMap = GameMap()
    private val ui = mockk<UI>(relaxed = true)

    @Test
    fun fnA() {
        assertEquals(1, testObj.fnA())
        assertEquals(9, testObj.fnA())
        assertEquals(10, testObj.fnA())
        assertEquals(8, testObj.fnA())
        assertEquals(16, testObj.fnA())
        assertEquals(14, testObj.fnA())
        assertEquals(12, testObj.fnA())
        assertEquals(2, testObj.fnA())
        assertEquals(20, testObj.fnA())
        assertEquals(15, testObj.fnA())
    }

    @Test
    fun fnB() {
        assertEquals(1, testObj.fnB())
        assertEquals(2, testObj.fnB())
        assertEquals(2, testObj.fnB())
        assertEquals(3, testObj.fnB())
        assertEquals(3, testObj.fnB())
        assertEquals(3, testObj.fnB())
        assertEquals(3, testObj.fnB())
        assertEquals(1, testObj.fnB())
        assertEquals(1, testObj.fnB())
        assertEquals(3, testObj.fnB())
    }

    @Test
    fun fnC() {
        assertEquals(3, testObj.fnC())
        assertEquals(4, testObj.fnC())
        assertEquals(1, testObj.fnC())
        assertEquals(3, testObj.fnC())
        assertEquals(3, testObj.fnC())
        assertEquals(2, testObj.fnC())
        assertEquals(3, testObj.fnC())
        assertEquals(1, testObj.fnC())
        assertEquals(3, testObj.fnC())
        assertEquals(4, testObj.fnC())
    }

    @Test
    fun initalizeLocations() {
        val random = mockk<Random>()
        // first six return values that cross over, second values get used (plus 1)
        every { random.nextInt(20) } returnsMany listOf(1, 1, 1, 1, 1, 1, 2, 3, 4, 5, 6, 7)
        val testObj = GameState(Chaos(random))
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
        val random = mockk<Random>()
        every { random.nextInt(20) }.returnsMany(3, 1, 4, 1, 5, 9)
        val testObj = GameState(Chaos(random))
        val result = testObj.generateLocations()
        assertArrayEquals(arrayOf(0, 4, 2, 5, 2, 6, 10), result)
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
        testObj.setNewLocations(arrayOf(0, 1, 10, 11, 12, 13, 14))
        assertEquals(0, testObj.followArrowPath(arrayOf(0, 2, 3, 4), 3, ui, gameMap), "should miss")
        verify { ui.reportMissedShot() }
    }

    @Test
    fun followArrowPath_outOfAmmo() {
        repeat(4) { testObj.consumeArrow() }
        testObj.setNewLocations(arrayOf(0, 1, 10, 11, 12, 13, 14))
        assertEquals(-1, testObj.followArrowPath(arrayOf(0, 2, 3, 4), 3, ui, gameMap), "should miss and run out of ammo")
        verify { ui.reportMissedShot() }
    }

    @Test
    fun followArrowPath_wumpusEatsPlayerAfterMiss() {
        val random = mockk<Random>()
        // need to train random move of wumpus after miss
        every { random.nextInt(4) } returns 0
        val testObj = GameState(Chaos(random))

        testObj.setNewLocations(arrayOf(0, 1, 5, 11, 12, 13, 14))
        assertEquals(-1, testObj.followArrowPath(arrayOf(0, 2), 1, ui, gameMap), "should miss and be eaten")
        verify { ui.reportMissedShot() }
        verify { ui.reportWumpusAtePlayer() }
    }

    @Test
    fun followArrowPath_shootSelf() {
        testObj.setNewLocations(arrayOf(0, 1, 10, 11, 12, 13, 14))
        assertEquals(-1, testObj.followArrowPath(arrayOf(0, 5, 6, 7, 8, 1), 5, ui, gameMap), "should miss and be eaten")
        verify { ui.reportShotSelf() }
    }

    @Test
    fun followArrowPath_shootWumpus() {
        testObj.setNewLocations(arrayOf(0, 1, 3, 11, 12, 13, 14))
        assertEquals(1, testObj.followArrowPath(arrayOf(0, 2, 3), 2, ui, gameMap), "should hit wumpus")
        verify { ui.reportShotWumpus() }
    }

    @Test
    fun nextArrowRoom() {
        assertEquals(2, testObj.nextArrowRoom(3, 1, arrayOf(0, 2), gameMap))
        assertEquals(2, testObj.nextArrowRoom(3, 1, arrayOf(0, 5), gameMap))
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