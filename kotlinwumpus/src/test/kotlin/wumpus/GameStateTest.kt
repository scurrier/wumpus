package wumpus

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class GameStateTest {
    private val chaos = mockk<Chaos>()
    private val gameResult = GameResult()
    private val testObj = GameState(gameResult, chaos, true)
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
        testObj.player.room = gameMap.room(10)
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

    @Nested
    inner class FollowArrowPathTest {
        init {
            testObj.setNewLocations(listOf(1, 10, 20, 20, 20, 20))
        }

        @Test
        fun miss() {
            // need to train random move of wumpus after miss
            every { chaos.pickWumpusMovement() } returns 4
            testObj.followArrowPath(arrayOf(2, 3, 4), ui)
            verify { ui.reportMissedShot() }
            assertTrue(gameResult.stillPlaying())
        }

        @Test
        fun outOfAmmo() {
            // need to train random move of wumpus after miss
            every { chaos.pickWumpusMovement() } returns 4

            consume4Arrows()
            testObj.followArrowPath(arrayOf(2, 3, 4), ui)
            verify { ui.reportMissedShot() }
            assertTrue(gameResult.hasLost())
        }

        private fun consume4Arrows() {
            repeat(4) { testObj.followArrowPath(arrayOf(), ui) }
        }

        @Test
        fun wumpusEatsPlayerAfterMiss() {
            // need to train random move of wumpus after miss
            every { chaos.pickWumpusMovement() } returns 1
            testObj.setNewLocations(listOf(1, 5, 20, 20, 20, 20))

            testObj.followArrowPath(arrayOf(2), ui)
            verify { ui.reportMissedShot() }
            verify { ui.reportWumpusAtePlayer() }
            assertTrue(gameResult.hasLost())
        }

        @Test
        fun shootSelf() {
            testObj.followArrowPath(arrayOf(5, 6, 7, 8, 1), ui)
            verify { ui.reportShotSelf() }
            assertTrue(gameResult.hasLost())
        }

        @Test
        fun shootWumpus() {
            testObj.setNewLocations(listOf(1, 3, 20, 20, 20, 20))
            testObj.followArrowPath(arrayOf(2, 3), ui)
            verify { ui.reportShotWumpus() }
            assertTrue(gameResult.hasWon())
        }
    }

    @Test
    fun nextArrowRoom() {
        assertEquals(2, testObj.nextArrowRoom(gameMap.room(3), 2))
        every { chaos.pickTunnel() } returns 1
        assertEquals(2, testObj.nextArrowRoom(gameMap.room(3), 5))
    }
}