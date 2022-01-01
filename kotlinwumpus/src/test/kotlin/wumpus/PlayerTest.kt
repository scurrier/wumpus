package wumpus

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class PlayerTest {
    private val chaos = mockk<Chaos>()
    private val gameResult = GameResult()
    private val gameMap = GameMap()
    private val destinationRoom = gameMap.room(2)
    private val hazards = mutableListOf<Hazard>()
    private val wumpus = Wumpus(chaos, gameMap, gameResult).also { it.room = destinationRoom }
    private val pit = Pit(gameResult).also { it.room = destinationRoom }
    private val bat = Bat(chaos).also { it.room = destinationRoom }
    private val testObj = Player(hazards, gameMap, gameResult)
    private val ui = mockk<UI>(relaxed = true)

    init {
        testObj.room = gameMap.room(1)
    }

    @Test
    fun noEncounters() {
        testObj.movePlayerToRoom(2, ui)
        assertTrue(testObj.room.isIndex(2))
        assertTrue(gameResult.stillPlaying())
    }

    @Test
    fun bat() {
        hazards.add(bat)
        every { chaos.pickRoom() } returns 10
        testObj.movePlayerToRoom(2, ui)
        assertTrue(gameResult.stillPlaying())
        assertTrue(testObj.room.isIndex(10), "expected 10 but was ${testObj.room}")
        verify { ui.reportBatEncounter() }
    }

    @Test
    fun pit() {
        hazards.add(pit)
        testObj.movePlayerToRoom(2, ui)
        assertTrue(gameResult.hasLost())
        verify { ui.reportFall() }
    }

    @Test
    fun wumpusThatStays() {
        every { chaos.pickWumpusMovement() } returns 4
        hazards.addAll(listOf(wumpus, pit))
        testObj.movePlayerToRoom(2, ui)
        assertTrue(gameResult.hasLost())
        verify { ui.reportWumpusBump() }
    }

    @Test
    fun wumpusThatMovesThenPit() {
        every { chaos.pickWumpusMovement() } returns 1
        hazards.addAll(listOf(wumpus, pit))
        testObj.movePlayerToRoom(2, ui)
        assertTrue(gameResult.hasLost())
        verify { ui.reportWumpusBump() }
        verify { ui.reportFall() }
    }
}