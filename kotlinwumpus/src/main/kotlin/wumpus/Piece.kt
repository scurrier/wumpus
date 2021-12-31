package wumpus

internal abstract class Piece(var room: Room = Room(0, 0, 0, 0)) {
    abstract fun nearby(ui: UI)
    fun checkForEncounter(ui: UI, gameState: GameState) {
        if (gameState.playerRoom == room)
            encountered(ui, gameState)
    }
    protected abstract fun encountered(ui: UI, gameState: GameState)
}

internal class Player() : Piece() {
    override fun nearby(ui: UI) {}
    override fun encountered(ui: UI, gameState: GameState) {}
    fun movePlayerToRoom(newPlayerRoom: Int, ui: UI, gameState: GameState) {
        room = gameState.map.room(newPlayerRoom)
        val hazardIterator = gameState.hazardIterator()
        while (hazardIterator.hasNext() && gameState.stillPlaying()) {
            hazardIterator.next().checkForEncounter(ui, gameState)
        }
    }
}

internal class Wumpus() : Piece() {
    override fun nearby(ui: UI) = ui.reportWumpusNearby()

    override fun encountered(ui: UI, gameState: GameState) {
        ui.reportWumpusBump()
        wumpusMove(ui, gameState)
    }

    fun wumpusMove(ui: UI, gameState: GameState) {
        val k2 = gameState.chaos.pickWumpusMovement()
        if (k2 != 4)
            room = gameState.map.room(room[k2])
        if (room == gameState.playerRoom)
            wumpusWin(ui, gameState)
    }

    fun wumpusWin(ui: UI, gameState: GameState) {
        ui.reportWumpusAtePlayer()
        gameState.lose()
    }
}

internal class Pit() : Piece() {
    override fun nearby(ui: UI) = ui.reportPitNearby()

    override fun encountered(ui: UI, gameState: GameState) {
        ui.reportFall()
        gameState.lose()
    }
}

internal class Bat() : Piece() {
    override fun nearby(ui: UI) = ui.reportBatNearby()

    override fun encountered(ui: UI, gameState: GameState) {
        ui.reportBatEncounter()
        gameState.player.movePlayerToRoom(gameState.chaos.pickRoom(), ui, gameState)
    }
}