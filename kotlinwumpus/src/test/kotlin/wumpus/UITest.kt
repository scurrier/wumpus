package wumpus

import Console
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class UITest {
    private val console = mockk<Console>(relaxed = true)
    private val testObj = UI(console)

    @ParameterizedTest(name = "{0} should be {1}")
    @CsvSource("N, false", "n, false", "Y, true", "y, true")
    fun askIfInstructionsNeeded(userInput: Char, expectedResult: Boolean) {
        every { console.readln() } returns userInput
        assertEquals(expectedResult, testObj.askIfInstructionsNeeded(), "\"${userInput}\" should have been $expectedResult")
    }
}