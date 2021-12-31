package wumpus

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class GameStateTest {
    private val chaos = mockk<Chaos>()
    private val testObj = GameState(chaos)
    private val gameMap = testObj.map
    private val ui = mockk<UI>(relaxed = true)

    @Test
    fun initializeLocations() {
        // first six return values that cross over, second values get used (plus 1)
        every { chaos.pickRoom() } returnsMany listOf(2, 2, 2, 2, 2, 2, 3, 4, 5, 6, 7, 8)
        testObj.initializeLocations()
        assertTrue(testObj.playerRoom.isIndex(3))
        assertTrue(testObj.wumpusRoom.isIndex(4))
    }

    @Test
    fun resetWithSameSetup() {
        testObj.setNewLocations(listOf(1, 2, 3, 4, 5, 6))
        assertTrue(testObj.playerRoom.isIndex(1))
        testObj.resetGame(false)
        assertTrue(testObj.playerRoom.isIndex(1))
        testObj.playerRoom = gameMap.room(10)
        testObj.resetGame(false)
        assertTrue(testObj.playerRoom.isIndex(1), "expected 1 but was ${testObj.playerRoom}")
    }

    @Test
    fun hasCrossover() {
        assertFalse(testObj.hasCrossovers(listOf(1, 2, 3, 4, 5, 6)))
        assertTrue(testObj.hasCrossovers(listOf(1, 2, 3, 4, 5, 1)))
        assertTrue(testObj.hasCrossovers(listOf(1, 2, 3, 4, 2, 6)))
        assertTrue(testObj.hasCrossovers(listOf(1, 2, 3, 3, 5, 6)))
    }

    @Test
    fun generateLocations() {
        every { chaos.pickRoom() }.returnsMany(3, 1, 4, 1, 5, 9)
        val result = testObj.generateLocations()
        assertEquals(listOf(3, 1, 4, 1, 5, 9), result)
    }

    @Test
    fun setNewLocations() {
        testObj.setNewLocations(listOf(3, 4, 5, 6, 7, 8))
        assertTrue(testObj.playerRoom.isIndex(3))
        assertTrue(testObj.wumpusRoom.isIndex(4))
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

    @Nested
    inner class followArrowPathTest {
        init {
            testObj.playerRoom = gameMap.room(1)
            testObj.wumpusRoom = gameMap.room(10)
        }

        @Test
        fun miss() {
            // need to train random move of wumpus after miss
            every { chaos.pickWumpusMovement() } returns 4
            testObj.followArrowPath(arrayOf(2, 3, 4), ui)
            verify { ui.reportMissedShot() }
            assertTrue(testObj.stillPlaying())
        }

        @Test
        fun outOfAmmo() {
            // need to train random move of wumpus after miss
            every { chaos.pickWumpusMovement() } returns 4

            consume4Arrows()
            testObj.followArrowPath(arrayOf(2, 3, 4), ui)
            verify { ui.reportMissedShot() }
            assertTrue(testObj.hasLost())
        }

        private fun consume4Arrows() {
            repeat(4) { testObj.followArrowPath(arrayOf(), ui) }
        }

        @Test
        fun wumpusEatsPlayerAfterMiss() {
            // need to train random move of wumpus after miss
            every { chaos.pickWumpusMovement() } returns 1

            testObj.wumpusRoom = gameMap.room(5)
            testObj.followArrowPath(arrayOf(2), ui)
            verify { ui.reportMissedShot() }
            verify { ui.reportWumpusAtePlayer() }
            assertTrue(testObj.hasLost())
        }

        @Test
        fun shootSelf() {
            testObj.followArrowPath(arrayOf(5, 6, 7, 8, 1), ui)
            verify { ui.reportShotSelf() }
            assertTrue(testObj.hasLost())
        }

        @Test
        fun shootWumpus() {
            testObj.wumpusRoom = gameMap.room(3)
            testObj.followArrowPath(arrayOf(2, 3), ui)
            verify { ui.reportShotWumpus() }
            assertTrue(testObj.hasWon())
        }
    }

    @Test
    fun nextArrowRoom() {
        assertEquals(2, testObj.nextArrowRoom(gameMap.room(3), 2))
        every { chaos.pickTunnel() } returns 1
        assertEquals(2, testObj.nextArrowRoom(gameMap.room(3), 5))
    }
}