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

    private var locations = arrayOf(Player(), Wumpus(), Pit(), Pit(), Bat(), Bat())
    private val hazards = locations.slice(1..5)
    private var initialLocations = locations.map { it.room }.toMutableList()

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
        var newLocations: List<Int>
        do {
            newLocations = generateLocations()
        } while (hasCrossovers(newLocations))
        setNewLocations(newLocations)
    }

    fun generateLocations(): List<Int> {
        val newLocations = mutableListOf<Int>()
        repeat(6) { newLocations.add(chaos.pickRoom()) }
        return newLocations
    }

    fun setNewLocations(newLocations: List<Int>) {
        for ((index, newRoom) in newLocations.withIndex()) {
            locations[index].room = map.room(newRoom)
            initialLocations[index] = map.room(newRoom)
        }
    }

    fun hasCrossovers(newLocations: List<Int>): Boolean {
        for ((j, room) in newLocations.withIndex())
            for ((k, otherRoom) in newLocations.withIndex())
                if (j != k && room == otherRoom)
                    return true
        return false
    }

    private fun restoreInitialLocations() {
        for ((i, location) in initialLocations.withIndex())
            locations[i].room = location
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

    fun hazardsNearby(): List<Piece> {
        return hazards.filter { playerRoom.hasPathTo(it.room) }
    }
}