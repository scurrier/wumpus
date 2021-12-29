package wumpus

import GameMap
import GameState

interface PlayerAction {
    fun doAction(gameState: GameState, ui: UI, map: GameMap)
}

object Move : PlayerAction {
    override fun doAction(gameState: GameState, ui: UI, map: GameMap) {
        TODO("Not yet implemented")
    }
}

object Shoot : PlayerAction {
    override fun doAction(gameState: GameState, ui: UI, map: GameMap) {
        val roomCount = ui.askForNumberOfRooms()
        val path = ui.askForArrowPath(roomCount)
        val newGameResult = gameState.followArrowPath(path, ui, map)
        gameState.updateGameResult(newGameResult)
    }
}