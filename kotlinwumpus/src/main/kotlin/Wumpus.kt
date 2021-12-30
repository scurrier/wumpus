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
			ui.askForAction().doAction(gameState, ui, map)
		} while (gameState.stillPlaying())
		if (gameState.hasLost()) {
			ui.reportLoss()
		} else {
			ui.reportWin()
		}
	}
}

