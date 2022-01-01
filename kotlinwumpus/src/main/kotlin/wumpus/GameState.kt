package wumpus

import java.util.Random

internal class GameState(
    var gameResult: GameResult,
    val chaos: Chaos = Chaos(Random()),
    private val exitOnWin: Boolean
) {
    val map: GameMap = GameMap()
    private val wumpus: Wumpus = Wumpus(chaos, map, gameResult)
    private val hazards = listOf(wumpus, Pit(gameResult), Pit(gameResult), Bat(chaos), Bat(chaos))
    val player: Player = Player(hazards, map, gameResult)
    val arrow = Arrow(chaos, player, wumpus, map, gameResult)
    val playerRoom: Room get() = player.room
    val wumpusRoom: Room get() = wumpus.room
    private var locations = listOf(player, *hazards.toTypedArray())
    private var initialLocations = locations.map { it.room }.toMutableList()
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
        arrow.resetArrows()
        if (useNewSetup)
            initializeLocations()
        else restoreInitialLocations()
    }

    fun playAgain() = !(exitOnWin && gameResult.hasWon())

    fun hazardsNearby(): List<Hazard> {
        return hazards.filter { playerRoom.hasPathTo(it.room) }
    }
}