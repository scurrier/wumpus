package wumpus

internal class Player(private val hazards: List<Hazard>, private val map: GameMap, private val gameResult: GameResult) : Piece(), Shootable {
    fun movePlayerToRoom(newPlayerRoom: Int, ui: UI) {
        room = map.room(newPlayerRoom)
        val hazardIterator = hazards.iterator()
        while (hazardIterator.hasNext() && gameResult.stillPlaying()) {
            hazardIterator.next().checkForEncounter(ui, this)
        }
    }

    override fun checkShot(arrowRoom: Room, ui: UI) {
        if (arrowRoom == room)
            shot(ui)
    }

    private fun shot(ui: UI) {
        ui.reportShotSelf()
        gameResult.lose()
    }

}