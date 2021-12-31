package wumpus

import java.util.Random

internal class GameState(
    var gameResult: GameResult,
    val chaos: Chaos = Chaos(Random()),
    private val exitOnWin: Boolean
) {
    val map: GameMap = GameMap()
    private var arrows: Arrows = Arrows()
    val player: Player = Player()
    val playerRoom: Room get() = player.room
    private val wumpus: Wumpus = Wumpus()
    val wumpusRoom: Room get() = wumpus.room
    private val hazards = arrayOf(wumpus, Pit(), Pit(), Bat(), Bat())
    private var locations = arrayOf(player, *hazards)
    private var initialLocations = locations.map { it.room }.toMutableList()
    fun hazardIterator(): Iterator<Hazard> = hazards.iterator()

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

    fun playAgain() = !(exitOnWin && gameResult.hasWon())

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
            return gameResult.lose()
        return wumpus.wumpusMove(ui, this)
    }

    private fun playerHitByArrow(ui: UI) {
        ui.reportShotSelf()
        gameResult.lose()
    }

    private fun wumpusHitByArrow(ui: UI) {
        ui.reportShotWumpus()
        gameResult.win()
    }

    fun nextArrowRoom(start: Room, destination: Int) = if (destination in start) {
        destination
    } else {
        start[chaos.pickTunnel()]
    }

    fun hazardsNearby(): List<Hazard> {
        return hazards.filter { playerRoom.hasPathTo(it.room) }
    }
}