package wumpus

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.Random

internal class ChaosTest {
    val testObj = Chaos(Random(0))

    fun pickRoom() {
        assertEquals(1, testObj.pickRoom())
        assertEquals(9, testObj.pickRoom())
        assertEquals(10, testObj.pickRoom())
        assertEquals(8, testObj.pickRoom())
        assertEquals(16, testObj.pickRoom())
        assertEquals(14, testObj.pickRoom())
        assertEquals(12, testObj.pickRoom())
        assertEquals(2, testObj.pickRoom())
        assertEquals(20, testObj.pickRoom())
        assertEquals(15, testObj.pickRoom())
    }

    @Test
    fun pickTunnel() {
        assertEquals(1, testObj.pickTunnel())
        assertEquals(2, testObj.pickTunnel())
        assertEquals(2, testObj.pickTunnel())
        assertEquals(3, testObj.pickTunnel())
        assertEquals(3, testObj.pickTunnel())
        assertEquals(3, testObj.pickTunnel())
        assertEquals(3, testObj.pickTunnel())
        assertEquals(1, testObj.pickTunnel())
        assertEquals(1, testObj.pickTunnel())
        assertEquals(3, testObj.pickTunnel())
    }

    @Test
    fun pickWumpusMovement() {
        assertEquals(3, testObj.pickWumpusMovement())
        assertEquals(4, testObj.pickWumpusMovement())
        assertEquals(1, testObj.pickWumpusMovement())
        assertEquals(3, testObj.pickWumpusMovement())
        assertEquals(3, testObj.pickWumpusMovement())
        assertEquals(2, testObj.pickWumpusMovement())
        assertEquals(3, testObj.pickWumpusMovement())
        assertEquals(1, testObj.pickWumpusMovement())
        assertEquals(3, testObj.pickWumpusMovement())
        assertEquals(4, testObj.pickWumpusMovement())
    }



}