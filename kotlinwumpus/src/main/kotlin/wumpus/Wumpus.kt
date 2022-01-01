package wumpus

internal class Wumpus(private val chaos: Chaos, private val map: GameMap, private val gameResult: GameResult) : Hazard(), Shootable {
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

    override fun checkShot(arrowRoom: Room, ui: UI) {
        if (arrowRoom == room)
            shot(ui)
    }

    private fun shot(ui: UI) {
        ui.reportShotWumpus()
        gameResult.win()
    }
}