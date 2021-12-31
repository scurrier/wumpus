package wumpus

internal open class Piece (var room: Room = Room(0, 0, 0, 0)) {
}

internal class Player() : Piece()
internal class Wumpus() : Piece()
internal class Pit() : Piece()
internal class Bat() : Piece()