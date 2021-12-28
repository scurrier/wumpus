import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.Random

internal class GameStateTest {
    private val testObj = GameState(Random(0))
    @Test
    fun fnA() {
        assertEquals(1, testObj.fnA())
        assertEquals(9, testObj.fnA())
        assertEquals(10, testObj.fnA())
        assertEquals(8, testObj.fnA())
        assertEquals(16, testObj.fnA())
        assertEquals(14, testObj.fnA())
        assertEquals(12, testObj.fnA())
        assertEquals(2, testObj.fnA())
        assertEquals(20, testObj.fnA())
        assertEquals(15, testObj.fnA())
    }

    @Test
    fun fnB() {
        assertEquals(1, testObj.fnB())
        assertEquals(2, testObj.fnB())
        assertEquals(2, testObj.fnB())
        assertEquals(3, testObj.fnB())
        assertEquals(3, testObj.fnB())
        assertEquals(3, testObj.fnB())
        assertEquals(3, testObj.fnB())
        assertEquals(1, testObj.fnB())
        assertEquals(1, testObj.fnB())
        assertEquals(3, testObj.fnB())
    }

    @Test
    fun fnC() {
        assertEquals(3, testObj.fnC())
        assertEquals(4, testObj.fnC())
        assertEquals(1, testObj.fnC())
        assertEquals(3, testObj.fnC())
        assertEquals(3, testObj.fnC())
        assertEquals(2, testObj.fnC())
        assertEquals(3, testObj.fnC())
        assertEquals(1, testObj.fnC())
        assertEquals(3, testObj.fnC())
        assertEquals(4, testObj.fnC())
    }


}