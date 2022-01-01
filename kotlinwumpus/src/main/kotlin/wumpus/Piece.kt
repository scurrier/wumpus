package wumpus

internal abstract class Piece(var room: Room = Room(0, 0, 0, 0)) {
}

internal abstract class Hazard() : Piece() {
    abstract fun nearby(ui: UI)
    fun checkForEncounter(ui: UI, player: Player) {
        if (player.room == room)
            encountered(ui, player)
    }
    protected abstract fun encountered(ui: UI, player: Player)
}

internal class Player(private val hazards: List<Hazard>, private val map: GameMap, private val gameResult: GameResult) : Piece() {
    fun movePlayerToRoom(newPlayerRoom: Int, ui: UI) {
        room = map.room(newPlayerRoom)
        val hazardIterator = hazards.iterator()
        while (hazardIterator.hasNext() && gameResult.stillPlaying()) {
            hazardIterator.next().checkForEncounter(ui, this)
        }
    }
}

internal class Wumpus(private val chaos: Chaos, private val map: GameMap, private val gameResult: GameResult) : Hazard() {
    override fun nearby(ui: UI) = ui.reportWumpusNearby()

    override fun encountered(ui: UI, player: Player) {
        ui.reportWumpusBump()
        wumpusMove(ui, player)
    }

    fun wumpusMove(ui: UI, player: Player) {
        val k2 = chaos.pickWumpusMovement()
        if (k2 != 4)
            room = map.room(room[k2])
        if (room == player.room)
            wumpusWin(ui)
    }

    private fun wumpusWin(ui: UI) {
        ui.reportWumpusAtePlayer()
        gameResult.lose()
    }
}

internal class Pit(private val gameResult: GameResult) : Hazard() {
    override fun nearby(ui: UI) = ui.reportPitNearby()

    override fun encountered(ui: UI, player: Player) {
        ui.reportFall()
        gameResult.lose()
    }
}

internal class Bat(private val chaos: Chaos) : Hazard() {
    override fun nearby(ui: UI) = ui.reportBatNearby()

    override fun encountered(ui: UI, player: Player) {
        ui.reportBatEncounter()
        player.movePlayerToRoom(chaos.pickRoom(), ui)
    }
}