package wumpus

internal class Arrow(
    private val chaos: Chaos,
    private val player: Player,
    private val wumpus: Wumpus,
    private val map: GameMap,
    private val gameResult: GameResult
) : Piece() {
    val shootables = listOf<Shootable>(player, wumpus)
    var arrows: Arrows = Arrows()

    fun nextArrowRoom(start: Room, destination: Int) = if (destination in start) {
        destination
    } else {
        start[chaos.pickTunnel()]
    }

    fun resetArrows() {
        arrows.resetArrows()
    }

    fun followArrowPath(path: Array<Int>, ui: UI) {
        var arrowRoom = player.room
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

    fun arrowMissed(ui: UI) {
        ui.reportMissedShot()
        arrows.consumeArrow()
        if (!arrows.hasArrows())
            return gameResult.lose()
        return wumpus.wumpusMove(ui, player)
    }
}
