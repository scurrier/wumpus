package wumpus

import java.util.Random

internal class GameState(
    private val chaos: Chaos = Chaos(Random()),
    private val exitOnWin: Boolean = false
) {

    fun resetArrows() {
        arrowCount = 5
    }

    fun consumeArrow() {
        arrowCount -= 1
    }

    fun hasArrows(): Boolean {
        return arrowCount > 0
    }

    private var gameResult: Int = 0
    var playerRoom: Int
        get() = locations[0]
        set(v) {
            locations[0] = v
        }
    var wumpusRoom: Int
        get() = locations[1]
        set(v) {
            locations[1] = v
        }
    val pit1: Int
        get() = locations[2]
    val pit2: Int
        get() = locations[3]
    val bat1: Int
        get() = locations[4]
    val bat2: Int
        get() = locations[5]

    private var arrowCount: Int = 5
    var locations = Array(6) { 0 }
    private var initialLocations = Array(6) { 0 }

    fun wumpusMove(map: GameMap, ui: UI) {
        val k2 = chaos.pickWumpusMovement()
        if (k2 != 4) wumpusRoom = map.tunnelFrom(wumpusRoom, k2)
        if (wumpusRoom == playerRoom) {
            ui.reportWumpusAtePlayer()
            lose()
        }
    }

    fun intializeLocations() {
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
        locations = newLocations.clone()
        initialLocations = newLocations.clone()
    }

    fun hasCrossovers(): Boolean {
        for (j in 0..5) {
            for (k in 0..5) {
                if (j != k && locations[j] == locations[k]) {
                    return true
                }
            }
        }
        return false
    }

    private fun restoreInitialLocations() {
        locations = initialLocations.clone()
    }

    fun resetGame(useNewSetup: Boolean) {
        if (useNewSetup) intializeLocations()
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

    fun followArrowPath(path: Array<Int>, ui: UI, map: GameMap) {
        var arrowRoom = playerRoom
        for (pathRoom in path) {
            arrowRoom = nextArrowRoom(arrowRoom, pathRoom, map)
            if (arrowRoom == wumpusRoom) {
                ui.reportShotWumpus()
                return win()
            }
            if (arrowRoom == playerRoom) {
                ui.reportShotSelf()
                return lose()
            }
        }

        ui.reportMissedShot()
        consumeArrow()
        if (!hasArrows()) {
            return lose()
        }
        return wumpusMove(map, ui)
    }

    fun nextArrowRoom(start: Int, destination: Int, map: GameMap) = if (map.roomHasPathTo(start, destination)) {
        destination
    } else {
        map.tunnelFrom(start, chaos.pickTunnel())
    }

    fun movePlayerToRoom(newPlayerRoom: Int, ui: UI, map: GameMap) {
        playerRoom = newPlayerRoom
        if (newPlayerRoom == wumpusRoom) {
            ui.reportWumpusBump()
            wumpusMove(map, ui)
            if (!stillPlaying()) return
        }
        if ((newPlayerRoom == pit1 || newPlayerRoom == pit2)) {
            ui.reportFall()
            lose()
            return
        }
        if ((newPlayerRoom == bat1 || newPlayerRoom == bat2)) {
            ui.reportBatEncounter()
            return movePlayerToRoom(chaos.pickRoom(), ui, map)
        }
        return
    }
}