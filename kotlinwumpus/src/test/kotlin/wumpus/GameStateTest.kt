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
        testObj.intializeLocations()
        assertEquals(gameMap.room(3), testObj.playerRoom)
        assertEquals(gameMap.room(4), testObj.wumpusRoom)
    }

    @Test
    fun hasCrossover() {
        testObj.setNewLocations(arrayOf(1, 2, 3, 4, 5, 6))
        assertFalse(testObj.hasCrossovers())
        testObj.setNewLocations(arrayOf(1, 2, 3, 4, 5, 1))
        assertTrue(testObj.hasCrossovers())
        testObj.setNewLocations(arrayOf(6, 2, 3, 4, 5, 6))
        assertTrue(testObj.hasCrossovers())
        testObj.setNewLocations(arrayOf(6, 2, 3, 3, 5, 6))
        assertTrue(testObj.hasCrossovers())
    }

    @Test
    fun generateLocations() {
        every { chaos.pickRoom() }.returnsMany(3, 1, 4, 1, 5, 9)
        val result = testObj.generateLocations()
        assertArrayEquals(arrayOf(3, 1, 4, 1, 5, 9), result)
    }

    @Test
    fun setNewLocations() {
        testObj.setNewLocations(arrayOf(3, 4, 5, 6, 7, 8))
        assertEquals(gameMap.room(3), testObj.playerRoom)
        assertEquals(gameMap.room(4), testObj.wumpusRoom)
        assertEquals(gameMap.room(5), testObj.pit1)
        assertEquals(gameMap.room(6), testObj.pit2)
        assertEquals(gameMap.room(7), testObj.bat1)
        assertEquals(gameMap.room(8), testObj.bat2)
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
            testObj.followArrowPath(arrayOf(2, 3, 4), ui, gameMap)
            verify { ui.reportMissedShot() }
            assertTrue(testObj.stillPlaying())
        }

        @Test
        fun outOfAmmo() {
            // need to train random move of wumpus after miss
            every { chaos.pickWumpusMovement() } returns 4

            repeat(4) { testObj.consumeArrow() }
            testObj.followArrowPath(arrayOf(2, 3, 4), ui, gameMap)
            verify { ui.reportMissedShot() }
            assertTrue(testObj.hasLost())
        }

        @Test
        fun wumpusEatsPlayerAfterMiss() {
            // need to train random move of wumpus after miss
            every { chaos.pickWumpusMovement() } returns 1

            testObj.wumpusRoom = gameMap.room(5)
            testObj.followArrowPath(arrayOf(2), ui, gameMap)
            verify { ui.reportMissedShot() }
            verify { ui.reportWumpusAtePlayer() }
            assertTrue(testObj.hasLost())
        }

        @Test
        fun shootSelf() {
            testObj.followArrowPath(arrayOf(5, 6, 7, 8, 1), ui, gameMap)
            verify { ui.reportShotSelf() }
            assertTrue(testObj.hasLost())
        }

        @Test
        fun shootWumpus() {
            testObj.wumpusRoom = gameMap.room(3)
            testObj.followArrowPath(arrayOf(2, 3), ui, gameMap)
            verify { ui.reportShotWumpus() }
            assertTrue(testObj.hasWon())
        }
    }

    @Test
    fun nextArrowRoom() {
        assertEquals(2, testObj.nextArrowRoom(gameMap.room(3), 2, gameMap))
        every { chaos.pickTunnel() } returns 1
        assertEquals(2, testObj.nextArrowRoom(gameMap.room(3), 5, gameMap))
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

    @Nested
    inner class movePlayerToRoomTest {
        init {
            testObj.playerRoom = gameMap.room(1)
        }

        @Test
        fun noEncounters() {
            testObj.movePlayerToRoom(2, ui, gameMap)
            assertTrue(testObj.stillPlaying())
            assertEquals(gameMap.room(2), testObj.playerRoom)
        }

        @ParameterizedTest
        @CsvSource("20, 2", "2, 20")
        fun bats(bat1: Int, bat2: Int) {
            testObj.setNewLocations(arrayOf(1, 20, 20, 20, bat1, bat2))
            every { chaos.pickRoom() } returns 10
            testObj.movePlayerToRoom(2, ui, gameMap)
            assertTrue(testObj.stillPlaying())
            assertEquals(gameMap.room(10), testObj.playerRoom)
            verify { ui.reportBatEncounter() }
        }

        @ParameterizedTest
        @CsvSource("20, 2", "2, 20")
        fun pits(pit1: Int, pit2: Int) {
            testObj.setNewLocations(arrayOf(1, 20, pit1, pit2, 20, 20))
            testObj.movePlayerToRoom(2, ui, gameMap)
            assertTrue(testObj.hasLost())
            verify { ui.reportFall() }
        }

        @Test
        fun wumpusThatStays() {
            every { chaos.pickWumpusMovement() } returns 4
            testObj.wumpusRoom = gameMap.room(2)
            testObj.movePlayerToRoom(2, ui, gameMap)
            assertTrue(testObj.hasLost())
            verify { ui.reportWumpusBump() }
        }

        @Test
        fun wumpusThatMovesThenPit() {
            every { chaos.pickWumpusMovement() } returns 1
            testObj.setNewLocations(arrayOf(1, 2, 2, 20, 20, 20))
            testObj.movePlayerToRoom(2, ui, gameMap)
            assertTrue(testObj.hasLost())
            verify { ui.reportWumpusBump() }
            verify { ui.reportFall() }
        }
    }

    @Nested
    inner class wumpusMoveTest {
        init {
            testObj.playerRoom = gameMap.room(2)
            testObj.wumpusRoom = gameMap.room(1)
        }

        @Test
        fun noMove() {
            every {chaos.pickWumpusMovement()} returns 4
            testObj.wumpusMove(gameMap, ui)
            assertEquals(gameMap.room(1), testObj.wumpusRoom)
            assertTrue(testObj.stillPlaying())
        }

        @Test
        fun relocates() {
            every {chaos.pickWumpusMovement()} returns 2
            testObj.wumpusMove(gameMap, ui)
            assertEquals(gameMap.room(5), testObj.wumpusRoom)
            assertTrue(testObj.stillPlaying())
        }

        @Test
        fun catchesPlayer() {
            every {chaos.pickWumpusMovement()} returns 1
            testObj.wumpusMove(gameMap, ui)
            assertEquals(gameMap.room(2), testObj.wumpusRoom)
            assertTrue(testObj.hasLost())
        }
    }
}