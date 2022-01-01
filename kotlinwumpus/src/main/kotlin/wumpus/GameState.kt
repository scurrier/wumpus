package wumpus

import java.util.Random

internal class GameState(
    var gameResult: GameResult,
    val chaos: Chaos = Chaos(Random()),
    private val exitOnWin: Boolean
) {
    val map: GameMap = GameMap()
    private var arrows: Arrows = Arrows()
    private val wumpus: Wumpus = Wumpus(chaos, map, gameResult)
    private val hazards = listOf(wumpus, Pit(gameResult), Pit(gameResult), Bat(chaos), Bat(chaos))
    val player: Player = Player(hazards, map, gameResult)
    val playerRoom: Room get() = player.room
    val wumpusRoom: Room get() = wumpus.room
    private var locations = listOf(player, *hazards.toTypedArray())
    private var initialLocations = locations.map { it.room }.toMutableList()
    private val shootables = listOf<Shootable>(player, wumpus)
    private val arrow = Arrow()
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
        val pathIterator = path.iterator()
        while (pathIterator.hasNext() && gameResult.stillPlaying()) {
            val pathRoom = pathIterator.next()
            arrowRoom = map.room(nextArrowRoom(arrowRoom, pathRoom))
            shootables.forEach { it.checkShot(arrowRoom, ui) }
            if (!gameResult.stillPlaying())
                return
        }
        arrowMissed(ui)
    }

    private fun arrowMissed(ui: UI) {
        ui.reportMissedShot()
        arrows.consumeArrow()
        if (!arrows.hasArrows())
            return gameResult.lose()
        return wumpus.wumpusMove(ui, player)
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