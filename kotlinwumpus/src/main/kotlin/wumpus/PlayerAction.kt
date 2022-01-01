package wumpus

internal interface PlayerAction {
    fun doAction(gameState: GameState, ui: UI)
}

internal object Move : PlayerAction {
    override fun doAction(gameState: GameState, ui: UI) {
        val newPlayerRoom = ui.askForValidDestinationRoom(gameState.playerRoom)
        gameState.player.movePlayerToRoom(newPlayerRoom, ui)
    }
}

internal object Shoot : PlayerAction {
    override fun doAction(gameState: GameState, ui: UI) {
        val roomCount = ui.askForNumberOfRooms()
        val path = ui.askForArrowPath(roomCount)
        gameState.arrow.followArrowPath(path, ui)
    }
}