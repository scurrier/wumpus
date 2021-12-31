package wumpus

import Console

class Game {
	internal var ui = UI(Console())
	internal var gameState = GameState(exitOnWin = true)

	fun main() {
		try {
			ui.provideInstructions()
			gameState.initializeLocations()
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
		gameState.startPlaying()
		do {
			ui.printRoomDescription(gameState.playerRoom, gameState.hazardsNearby())
			ui.askForAction().doAction(gameState, ui)
		} while (gameState.stillPlaying())
		if (gameState.hasLost()) {
			ui.reportLoss()
		} else {
			ui.reportWin()
		}
	}
}

