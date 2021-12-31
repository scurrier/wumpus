package wumpus

import Console

class Game {
	internal var ui = UI(Console())
	internal val gameResult = GameResult()
	internal var gameState = GameState(gameResult, exitOnWin = true)

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
		gameResult.startPlaying()
		do {
			ui.printRoomDescription(gameState.playerRoom, gameState.hazardsNearby())
			ui.askForAction().doAction(gameState, ui)
		} while (gameResult.stillPlaying())
		if (gameResult.hasLost()) {
			ui.reportLoss()
		} else {
			ui.reportWin()
		}
	}
}

