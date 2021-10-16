import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class WumpusTest {
    @BeforeEach
    fun setUp() {
        Wumpus.random = Random(0) //seed random for expected results
    }

    @Test
    fun fnA() {
        assertEquals(1, Wumpus.fnA())
        assertEquals(9, Wumpus.fnA())
        assertEquals(10, Wumpus.fnA())
        assertEquals(8, Wumpus.fnA())
        assertEquals(16, Wumpus.fnA())
        assertEquals(14, Wumpus.fnA())
        assertEquals(12, Wumpus.fnA())
        assertEquals(2, Wumpus.fnA())
        assertEquals(20, Wumpus.fnA())
        assertEquals(15, Wumpus.fnA())
    }

    @Test
    fun fnB() {
        assertEquals(1, Wumpus.fnB())
        assertEquals(2, Wumpus.fnB())
        assertEquals(2, Wumpus.fnB())
        assertEquals(3, Wumpus.fnB())
        assertEquals(3, Wumpus.fnB())
        assertEquals(3, Wumpus.fnB())
        assertEquals(3, Wumpus.fnB())
        assertEquals(1, Wumpus.fnB())
        assertEquals(1, Wumpus.fnB())
        assertEquals(3, Wumpus.fnB())
    }

    @Test
    fun fnC() {
        assertEquals(3, Wumpus.fnC())
        assertEquals(4, Wumpus.fnC())
        assertEquals(1, Wumpus.fnC())
        assertEquals(3, Wumpus.fnC())
        assertEquals(3, Wumpus.fnC())
        assertEquals(2, Wumpus.fnC())
        assertEquals(3, Wumpus.fnC())
        assertEquals(1, Wumpus.fnC())
        assertEquals(3, Wumpus.fnC())
        assertEquals(4, Wumpus.fnC())
    }
}