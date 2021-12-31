package wumpus

import java.util.Random

internal class GameState(
    val chaos: Chaos = Chaos(Random()),
    private val exitOnWin: Boolean = false
) {

    val map: GameMap = GameMap()
    private var arrows: Arrows = Arrows()

    private var gameResult: Int = 0
    val player: Player = Player()
    var playerRoom: Room
        get() = player.room
        set(v) {
            player.room = v
        }
    private val wumpus: Wumpus = Wumpus()
    var wumpusRoom: Room
        get() = wumpus.room
        set(v) {
            wumpus.room = v
        }

    private var locations = arrayOf(player, wumpus, Pit(), Pit(), Bat(), Bat())
    private val hazards = locations.slice(1..5)
    private var initialLocations = locations.map { it.room }.toMutableList()
    fun hazardIterator(): Iterator<Piece> = hazards.iterator()

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
    fun lose() = updateGameResult(-1)
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
        return wumpus.wumpusMove(ui, this)
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

    fun hazardsNearby(): List<Piece> {
        return hazards.filter { playerRoom.hasPathTo(it.room) }
    }
}