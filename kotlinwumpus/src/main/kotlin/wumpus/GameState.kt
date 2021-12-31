package wumpus

import java.util.Random

internal class GameState(
    private val chaos: Chaos = Chaos(Random()),
    private val exitOnWin: Boolean = false
) {

    val map: GameMap = GameMap()
    private var arrows: Arrows = Arrows()

    private var gameResult: Int = 0
    var playerRoom: Room
        get() = locations[0].room
        set(v) {
            locations[0].room = v
        }
    var wumpusRoom: Room
        get() = locations[1].room
        set(v) {
            locations[1].room = v
        }
    val pit1: Room
        get() = locations[2].room
    val pit2: Room
        get() = locations[3].room
    val bat1: Room
        get() = locations[4].room
    val bat2: Room
        get() = locations[5].room

    var locations = Array(6) { Piece(Room(0, 0, 0, 0)) }
    private var initialLocations = Array(6) { Piece(Room(0, 0, 0, 0)) }

    fun wumpusMove(ui: UI) {
        val k2 = chaos.pickWumpusMovement()
        if (k2 != 4)
            wumpusRoom = map.room(wumpusRoom[k2])
        if (wumpusRoom == playerRoom)
            wumpusWin(ui)
    }

    private fun wumpusWin(ui: UI) {
        ui.reportWumpusAtePlayer()
        lose()
    }

    fun initializeLocations() {
        do {
            setNewLocations(generateLocations())
        } while (hasCrossovers())
    }

    fun generateLocations(): Array<Int> {
        val newLocations = Array(6) { 0 }
        for (j in 0..5) {
            newLocations[j] = chaos.pickRoom()
        }
        return newLocations
    }

    fun setNewLocations(newLocations: Array<Int>) {
        for ((index, newRoom) in newLocations.withIndex()) {
            locations[index] = Piece(map.room(newRoom))
            initialLocations[index] = Piece(map.room(newRoom))
        }
    }

    fun hasCrossovers(): Boolean {
        for (j in 0..5) {
            for (k in 0..5) {
                if (j != k && locations[j].room == locations[k].room)
                    return true
            }
        }
        return false
    }

    private fun restoreInitialLocations() {
        for ((i, location) in initialLocations.withIndex())
            locations[i].room = location.room
    }

    fun resetGame(useNewSetup: Boolean) {
        arrows.resetArrows()
        if (useNewSetup)
            initializeLocations()
        else restoreInitialLocations()
    }

    fun updateGameResult(newGameResult: Int) {
        gameResult = newGameResult
    }

    fun startPlaying() = updateGameResult(0)
    private fun win() = updateGameResult(1)
    private fun lose() = updateGameResult(-1)
    fun stillPlaying(): Boolean = gameResult == 0
    fun hasLost(): Boolean = gameResult < 0
    fun hasWon(): Boolean = gameResult > 0
    fun playAgain() = !(exitOnWin && hasWon())

    fun followArrowPath(path: Array<Int>, ui: UI) {
        var arrowRoom = playerRoom
        for (pathRoom in path) {
            arrowRoom = map.room(nextArrowRoom(arrowRoom, pathRoom))
            when (arrowRoom) {
                wumpusRoom -> return wumpusHitByArrow(ui)
                playerRoom -> return playerHitByArrow(ui)
            }
        }
        return arrowMissed(ui)
    }

    private fun arrowMissed(ui: UI) {
        ui.reportMissedShot()
        arrows.consumeArrow()
        if (!arrows.hasArrows())
            return lose()
        return wumpusMove(ui)
    }

    private fun playerHitByArrow(ui: UI) {
        ui.reportShotSelf()
        lose()
    }

    private fun wumpusHitByArrow(ui: UI) {
        ui.reportShotWumpus()
        win()
    }

    fun nextArrowRoom(start: Room, destination: Int) = if (destination in start) {
        destination
    } else {
        start[chaos.pickTunnel()]
    }

    fun movePlayerToRoom(newPlayerRoom: Int, ui: UI) {
        playerRoom = map.room(newPlayerRoom)
        if (playerRoom == wumpusRoom) {
            ui.reportWumpusBump()
            wumpusMove(ui)
            if (!stillPlaying()) return
        }
        if ((playerRoom == pit1 || playerRoom == pit2)) {
            ui.reportFall()
            lose()
            return
        }
        if ((playerRoom == bat1 || playerRoom == bat2)) {
            ui.reportBatEncounter()
            return movePlayerToRoom(chaos.pickRoom(), ui)
        }
        return
    }
}