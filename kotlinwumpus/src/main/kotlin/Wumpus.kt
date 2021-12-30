import wumpus.Move
import wumpus.Shoot
import wumpus.UI

class Wumpus {
	var ui = UI(Console())
	var gameState = GameState(exitOnWin = true)
	val map = GameMap()

	fun main() {
		try {
			ui.provideInstructions()
			gameState.intializeLocations()
			playGame()
			while (gameState.playAgain()) {
				val useNewSetup = ui.askIfNewSetup()
				gameState.resetGame(useNewSetup)
				playGame()
			}
		} catch (e: Throwable) {
			e.printStackTrace()
		}
	}

	private fun playGame() {
		ui.showTitle()
		gameState.resetArrows()
		gameState.startPlaying()
		do {
			ui.printRoomDescription(gameState, map)
			when (val action = ui.askForAction()) {
				Shoot -> action.doAction(gameState, ui, map)
				Move -> gameState.updateGameResult(movePlayerToRoom(ui.askForValidDestinationRoom(gameState, map)))
			}
		} while (gameState.stillPlaying())
		if (gameState.hasLost()) {
			ui.console.println("HA HA HA - YOU LOSE!")
		} else {
			ui.console.println("HEE HEE HEE - THE WUMPUS'LL GET YOU NEXT TIME!!")
		}
	}

	private fun movePlayerToRoom(newPlayerRoom: Int): Int {
		gameState.playerRoom = newPlayerRoom
		if (newPlayerRoom == gameState.wumpusRoom) {
			ui.reportWumpusBump()
			val f = gameState.wumpusMove(map, ui)
			if (f != 0) return f
		}
		if ((newPlayerRoom == gameState.pit1 || newPlayerRoom == gameState.pit2)) {
			ui.reportFall()
			return -1
		}
		if ((newPlayerRoom == gameState.bat1 || newPlayerRoom == gameState.bat2)) {
			ui.reportBatEncounter()
			return movePlayerToRoom(gameState.pickRoom())
		}
		return 0
	}
}

