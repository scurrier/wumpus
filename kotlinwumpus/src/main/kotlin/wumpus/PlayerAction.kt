package wumpus

internal interface PlayerAction {
    fun doAction(gameState: GameState, ui: UI, map: GameMap)
}

internal object Move : PlayerAction {
    override fun doAction(gameState: GameState, ui: UI, map: GameMap) {
        val newPlayerRoom = ui.askForValidDestinationRoom(gameState, map)
        gameState.movePlayerToRoom(newPlayerRoom, ui, map)
    }
}

internal object Shoot : PlayerAction {
    override fun doAction(gameState: GameState, ui: UI, map: GameMap) {
        val roomCount = ui.askForNumberOfRooms()
        val path = ui.askForArrowPath(roomCount)
        gameState.followArrowPath(path, ui, map)
    }
}