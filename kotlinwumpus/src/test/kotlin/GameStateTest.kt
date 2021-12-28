import io.mockk.every
import io.mockk.mockk
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

    @Test
    fun initalizeLocations() {
        val random = mockk<Random>()
        // first six return values that cross over, second values get used (plus 1)
        every {random.nextInt(20) }.returnsMany(1,1,1,1,1,1,2,3,4,5,6,7)
        val testObj = GameState(random)
        testObj.intializeLocations()
        assertEquals(3, testObj.playerRoom)
        assertEquals(4, testObj.wumpusRoom)
    }

    @Test
    fun hasCrossover() {
        testObj.setNewLocations(arrayOf(0,1,2,3,4,5,6))
        assertFalse(testObj.hasCrossovers())
        testObj.setNewLocations(arrayOf(0,1,2,3,4,5,1))
        assertTrue(testObj.hasCrossovers())
        testObj.setNewLocations(arrayOf(0,6,2,3,4,5,6))
        assertTrue(testObj.hasCrossovers())
        testObj.setNewLocations(arrayOf(0,6,2,3,3,5,6))
        assertTrue(testObj.hasCrossovers())
    }

    @Test
    fun generateLocations() {
        val random = mockk<Random>()
        every {random.nextInt(20) }.returnsMany(3,1,4,1,5,9)
        val testObj = GameState(random)
        val result = testObj.generateLocations()
        assertArrayEquals(arrayOf(0,4,2,5,2,6,10), result)
    }

    @Test
    fun setNewLocations() {
        testObj.setNewLocations(arrayOf(0,3,4,5,6,7,8))
        assertEquals(3, testObj.playerRoom)
        assertEquals(4, testObj.wumpusRoom)
        assertEquals(5, testObj.pit1)
        assertEquals(6, testObj.pit2)
        assertEquals(7, testObj.bat1)
        assertEquals(8, testObj.bat2)
    }
}