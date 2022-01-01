package wumpus

internal abstract class Piece(var room: Room = Room(0, 0, 0, 0))

internal abstract class Hazard : Piece() {
    abstract fun nearby(ui: UI)
    fun checkForEncounter(ui: UI, player: Player) {
        if (player.room == room)
            encountered(ui, player)
    }
    protected abstract fun encountered(ui: UI, player: Player)
}

internal interface Shootable {
    fun checkShot(arrowRoom: Room, ui: UI)
}

