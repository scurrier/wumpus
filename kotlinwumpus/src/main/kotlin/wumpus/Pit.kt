package wumpus

internal class Pit(private val gameResult: GameResult) : Hazard() {
    override fun nearby(ui: UI) = ui.reportPitNearby()

    override fun encountered(ui: UI, player: Player) {
        ui.reportFall()
        gameResult.lose()
    }
}