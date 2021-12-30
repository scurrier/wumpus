package wumpus

internal class Room(private val index: Int, private val connectedRooms: Array<Int>) {
    constructor(index: Int, vararg connectedRooms: Int) : this(index, connectedRooms.toTypedArray())
    operator fun get(i: Int): Int = connectedRooms[i-1]
    operator fun contains(target: Int): Boolean = target in connectedRooms
    fun isIndex(otherIndex: Int): Boolean = index == otherIndex
    fun hasPathTo(otherRoom: Room): Boolean = otherRoom.index in connectedRooms
    override fun toString(): String = "$index"
}