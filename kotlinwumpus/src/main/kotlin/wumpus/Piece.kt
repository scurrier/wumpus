package wumpus

internal open class Piece(var room: Room = Room(0, 0, 0, 0)) {
    open fun nearby(ui: UI) {}
}

internal class Player() : Piece()

internal class Wumpus() : Piece() {
    override fun nearby(ui: UI) = ui.reportWumpusNearby()
}

internal class Pit() : Piece() {
    override fun nearby(ui: UI) = ui.reportPitNearby()
}

internal class Bat() : Piece() {
    override fun nearby(ui: UI) = ui.reportBatNearby()
}