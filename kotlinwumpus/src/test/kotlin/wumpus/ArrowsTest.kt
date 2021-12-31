package wumpus

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ArrowsTest {
    private val testObj = Arrows()

    @Test
    fun arrows() {
        val arrowsConsumed = consumeAllArrows()
        assertEquals(5, arrowsConsumed)
        testObj.resetArrows()
        val resetArrows = consumeAllArrows()
        assertEquals(5, resetArrows)
        assertEquals(0, consumeAllArrows(), "Just proving that there are no arrows after consumeAllArrows()")
    }

    private fun consumeAllArrows(): Int {
        var c = 0
        while (testObj.hasArrows()) {
            c++
            testObj.consumeArrow()
        }
        return c
    }
}