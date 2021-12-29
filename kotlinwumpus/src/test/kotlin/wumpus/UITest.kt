package wumpus

import Console
import GameMap
import GameState
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.converter.ConvertWith
import org.junit.jupiter.params.converter.SimpleArgumentConverter
import org.junit.jupiter.params.provider.CsvSource
import java.util.Random

internal class UITest {
    private val console = mockk<Console>(relaxed = true)
    private val testObj = UI(console)

    @Test
    fun provideInstructions() {
        every { console.readln() } returns 'N'
        testObj.provideInstructions()
        verify(exactly = 0) { console.println("WELCOME TO 'HUNT THE WUMPUS'") }
        every { console.readln() } returns 'Y'
        testObj.provideInstructions()
        verify { console.println("WELCOME TO 'HUNT THE WUMPUS'") }
    }

    @ParameterizedTest(name = "{0} should be {1}")
    @CsvSource("N, false", "n, false", "Y, true", "y, true")
    fun askIfInstructionsNeeded(userInput: Char, expectedResult: Boolean) {
        every { console.readln() } returns userInput
        assertEquals(expectedResult, testObj.askIfInstructionsNeeded(), "\"${userInput}\" should have been $expectedResult")
    }

    @ParameterizedTest(name = "{0} should be {1}")
    @CsvSource("N, true", "n, true", "Y, false", "y, false")
    fun askIfNewSetup(userInput: Char, expectedResult: Boolean) {
        every { console.readln() } returns userInput
        assertEquals(expectedResult, testObj.askIfNewSetup(), "\"${userInput}\" should have been $expectedResult")
    }

    class PlayerActionConverter : SimpleArgumentConverter() {
        override fun convert(source: Any?, targetType: Class<*>?): Any {
            return when (source) {
                "Move" -> Move
                "Shoot" -> Shoot
                else -> error("could not convert $source")
            }
        }
    }
    @ParameterizedTest(name = "{0} should be {1}")
    @CsvSource("S, Shoot", "s, Shoot", "M, Move", "m, Move")
    fun askForAction(userInput: Char, @ConvertWith(PlayerActionConverter::class) expectedResult: PlayerAction) {
        every { console.readln() } returns userInput
        assertEquals(expectedResult, testObj.askForAction(), "\"${userInput}\" should have been $expectedResult")
    }

    @Test
    fun askForActionRetries() {
        every { console.readln() } returnsMany listOf('b', 'a', 'd', 'm')
        assertEquals(Move, testObj.askForAction(), "move should have been selected as the first valid action")
    }

    @ParameterizedTest(name = "{0} should be {1}")
    @CsvSource("1, 1", "2, 2", "3, 3", "4, 4", "5, 5")
    fun askForNumberOfRooms(userInput: Int, expectedResult: Int) {
        every { console.readInt() } returns userInput
        assertEquals(expectedResult, testObj.askForNumberOfRooms(), "\"${userInput}\" should have been $expectedResult")
    }

    @Test
    fun askForNumberOfRoomsRetries() {
        every { console.readInt() } returnsMany listOf(0,6,3)
        assertEquals(3, testObj.askForNumberOfRooms(), "should have been 3 since that's the first valid answer")
    }

    @Test
    fun giveInstructions() {
        testObj.giveInstructions()
        verifyOrder {
            console.println("WELCOME TO 'HUNT THE WUMPUS'")
            console.println("""
                  THE WUMPUS LIVES IN A CAVE OF 20 ROOMS. EACH ROOM
                HAS 3 TUNNELS LEADING TO OTHER ROOMS. (LOOK AT A
                DODECAHEDRON TO SEE HOW THIS WORKS-IF YOU DON'T KNOW
                WHAT A DODECAHEDRON IS, ASK SOMEONE)
                
                     HAZARDS:
                 BOTTOMLESS PITS - TWO ROOMS HAVE BOTTOMLESS PITS IN THEM
                     IF YOU GO THERE, YOU FALL INTO THE PIT (& LOSE!)
                 SUPER BATS - TWO OTHER ROOMS HAVE SUPER BATS. IF YOU
                     GO THERE, A BAT GRABS YOU AND TAKES YOU TO SOME OTHER
                     ROOM AT RANDOM. (WHICH MAY BE TROUBLESOME)
            """.trimIndent())
            console.input("HIT RETURN TO CONTINUE")
            console.println("""
                     WUMPUS:
                 THE WUMPUS IS NOT BOTHERED BY HAZARDS (HE HAS SUCKER
                 FEET AND IS TOO BIG FOR A BAT TO LIFT).  USUALLY
                 HE IS ASLEEP.  TWO THINGS WAKE HIM UP: YOU SHOOTING AN
                ARROW OR YOU ENTERING HIS ROOM.
                     IF THE WUMPUS WAKES HE MOVES (P=.75) ONE ROOM
                 OR STAYS STILL (P=.25).  AFTER THAT, IF HE IS WHERE YOU
                 ARE, HE EATS YOU UP AND YOU LOSE!
                
                     YOU:
                 EACH TURN YOU MAY MOVE OR SHOOT A CROOKED ARROW
                   MOVING:  YOU CAN MOVE ONE ROOM (THRU ONE TUNNEL)
                   ARROWS:  YOU HAVE 5 ARROWS.  YOU LOSE WHEN YOU RUN OUT
                   EACH ARROW CAN GO FROM 1 TO 5 ROOMS. YOU AIM BY TELLING
                   THE COMPUTER THE ROOM#S YOU WANT THE ARROW TO GO TO.
                   IF THE ARROW CAN'T GO THAT WAY (IF NO TUNNEL) IT MOVES
                   AT RANDOM TO THE NEXT ROOM.
                     IF THE ARROW HITS THE WUMPUS, YOU WIN.
                     IF THE ARROW HITS YOU, YOU LOSE.                
            """.trimIndent())
            console.input("HIT RETURN TO CONTINUE")
            console.println("""
                    WARNINGS:
                     WHEN YOU ARE ONE ROOM AWAY FROM A WUMPUS OR HAZARD,
                     THE COMPUTER SAYS:
                 WUMPUS:  'I SMELL A WUMPUS'
                 BAT   :  'BATS NEARBY'
                 PIT   :  'I FEEL A DRAFT'
                                
            """.trimIndent())
        }
    }

    @Test
    fun printRoomDescription() {
        val gameState = GameState(Random())
        gameState.setNewLocations(arrayOf(0,3,4,12,19,2,20))
        testObj.printRoomDescription(gameState, GameMap())
        verify {
            console.println("I SMELL A WUMPUS!")
            console.println("I FEEL A DRAFT")
            console.println("BATS NEARBY!")
            console.println("YOU ARE IN ROOM 3")
            console.println("TUNNELS LEAD TO 2 4 12")
            console.println("")
        }
    }
}