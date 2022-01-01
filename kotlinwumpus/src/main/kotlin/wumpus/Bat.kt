package wumpus

internal class Bat(private val chaos: Chaos) : Hazard() {
    override fun nearby(ui: UI) = ui.reportBatNearby()

    override fun encountered(ui: UI, player: Player) {
        ui.reportBatEncounter()
        player.movePlayerToRoom(chaos.pickRoom(), ui)
    }
}