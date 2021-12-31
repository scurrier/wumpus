package wumpus

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class PlayerTest {
    private val chaos = mockk<Chaos>()
    private val gameState = GameState(chaos)
    private val testObj = gameState.player
    private val ui = mockk<UI>(relaxed = true)
    private val gameMap = gameState.map

    init {
        testObj.room = gameMap.room(1)
    }

    @Test
    fun noEncounters() {
        testObj.movePlayerToRoom(2, ui, gameState)
        assertTrue(testObj.room.isIndex(2))
        assertTrue(gameState.stillPlaying())
    }

    @ParameterizedTest
    @CsvSource("20, 2", "2, 20")
    fun bats(bat1: Int, bat2: Int) {
        gameState.setNewLocations(listOf(1, 20, 20, 20, bat1, bat2))
        every { chaos.pickRoom() } returns 10
        testObj.movePlayerToRoom(2, ui, gameState)
        assertTrue(gameState.stillPlaying())
        assertTrue(testObj.room.isIndex(10), "expected 10 but was ${testObj}")
        verify { ui.reportBatEncounter() }
    }

    @ParameterizedTest
    @CsvSource("20, 2", "2, 20")
    fun pits(pit1: Int, pit2: Int) {
        gameState.setNewLocations(listOf(1, 20, pit1, pit2, 20, 20))
        testObj.movePlayerToRoom(2, ui, gameState)
        assertTrue(gameState.hasLost())
        verify { ui.reportFall() }
    }

    @Test
    fun wumpusThatStays() {
        every { chaos.pickWumpusMovement() } returns 4
        gameState.wumpusRoom = gameMap.room(2)
        testObj.movePlayerToRoom(2, ui, gameState)
        assertTrue(gameState.hasLost())
        verify { ui.reportWumpusBump() }
    }

    @Test
    fun wumpusThatMovesThenPit() {
        every { chaos.pickWumpusMovement() } returns 1
        gameState.setNewLocations(listOf(1, 2, 2, 20, 20, 20))
        testObj.movePlayerToRoom(2, ui, gameState)
        assertTrue(gameState.hasLost())
        verify { ui.reportWumpusBump() }
        verify { ui.reportFall() }
    }
}