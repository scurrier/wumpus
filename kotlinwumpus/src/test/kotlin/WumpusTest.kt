import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyOrder
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import wumpus.UI
import java.util.Random

class WumpusTest {
    private val console = mockk<Console>()
    private val testObj = Wumpus()
    @BeforeEach
    fun setUp() {
        testObj.random = Random(0)
        testObj.gameState = GameState((testObj.random))
        testObj.ui = UI(console)
    }

    @Test
    fun pinningTest() {
        testObj.exitOnWin = true
        every { console.print(any<Int>()) } returns Unit
        every { console.print(any<String>()) } returns Unit
        every { console.println(any<Int>()) } returns Unit
        every { console.println(any<String>()) } returns Unit
        every { console.readln() } returnsMany (listOf(
            'Y', 'M',
            'Y', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'S',
            'Y', 'S'
        ))
        every { console.readInt() } returnsMany (listOf(
            8,
            2, 3, 4, 14, 13, 20, 19, 18, 9,
            1, 10,
            2, 8, 9
        ))
        every { console.input(any()) } returns Unit
        testObj.main()
        verifyOrder {
            console.print("INSTRUCTIONS (Y-N) ")
            console.readln()
            console.println("""
                WELCOME TO 'HUNT THE WUMPUS'
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
            console.println("HUNT THE WUMPUS")
            console.println("")
            console.println("I FEEL A DRAFT")
            console.print("YOUR ARE IN ROOM ")
            console.println(1)
            console.print("TUNNELS LEAD TO ")
            console.print(2)
            console.print(" "); console.print(5)
            console.print(" "); console.println(8)
            console.println("")
            console.print("SHOOT OR MOVE (S-M) ")
            console.readln()
            console.print("WHERE TO ")
            console.readInt()
            console.println("YYYYIIIIEEEE . . . FELL IN PIT")
            console.println("HA HA HA - YOU LOSE!")

            console.print("SAME SETUP (Y-N)")
            console.readln()
            console.println("HUNT THE WUMPUS")
            console.println("")
            console.println("I FEEL A DRAFT")
            console.print("YOUR ARE IN ROOM ")
            console.println(1)
            console.print("TUNNELS LEAD TO ")
            console.print(2)
            console.print(" "); console.print(5)
            console.print(" "); console.println(8)
            console.println("")
            console.print("SHOOT OR MOVE (S-M) ")
            console.readln()
            console.print("WHERE TO ")
            console.readInt()
            console.println("I FEEL A DRAFT")
            console.print("YOUR ARE IN ROOM ")
            console.println(2)
            console.print("TUNNELS LEAD TO ")
            console.print(1)
            console.print(" "); console.print(3)
            console.print(" "); console.println(10)
            console.println("")
            console.print("SHOOT OR MOVE (S-M) ")
            console.readln()
            console.print("WHERE TO ")
            console.readInt()
            console.print("YOUR ARE IN ROOM ")
            console.println(3)
            console.print("TUNNELS LEAD TO ")
            console.print(2)
            console.print(" "); console.print(4)
            console.print(" "); console.println(12)
            console.println("")
            console.print("SHOOT OR MOVE (S-M) ")
            console.readln()
            console.print("WHERE TO ")
            console.readInt()
            console.println("BATS NEARBY!")
            console.print("YOUR ARE IN ROOM ")
            console.println(4)
            console.print("TUNNELS LEAD TO ")
            console.print(3)
            console.print(" "); console.print(5)
            console.print(" "); console.println(14)
            console.println("")
            console.print("SHOOT OR MOVE (S-M) ")
            console.readln()
            console.print("WHERE TO ")
            console.readInt()
            console.println("ZAP--SUPER BAT SNATCH! ELSEWHEREVILLE FOR YOU!")
            console.println("")
            console.print("YOUR ARE IN ROOM ")
            console.println(12)
            console.print("TUNNELS LEAD TO ")
            console.print(3)
            console.print(" "); console.print(11)
            console.print(" "); console.println(13)
            console.println("")
            console.print("SHOOT OR MOVE (S-M) ")
            console.readln()
            console.print("WHERE TO ")
            console.readInt()
            console.println("BATS NEARBY!")
            console.print("YOUR ARE IN ROOM ")
            console.println(13)
            console.print("TUNNELS LEAD TO ")
            console.print(12)
            console.print(" "); console.print(14)
            console.print(" "); console.println(20)
            console.println("")
            console.print("SHOOT OR MOVE (S-M) ")
            console.readln()
            console.print("WHERE TO ")
            console.readInt()
            console.println("BATS NEARBY!")
            console.print("YOUR ARE IN ROOM ")
            console.println(20)
            console.print("TUNNELS LEAD TO ")
            console.print(13)
            console.print(" "); console.print(16)
            console.print(" "); console.println(19)
            console.println("")
            console.print("SHOOT OR MOVE (S-M) ")
            console.readln()
            console.print("WHERE TO ")
            console.readInt()
            console.print("YOUR ARE IN ROOM ")
            console.println(19)
            console.print("TUNNELS LEAD TO ")
            console.print(11)
            console.print(" "); console.print(18)
            console.print(" "); console.println(20)
            console.println("")
            console.print("SHOOT OR MOVE (S-M) ")
            console.readln()
            console.print("WHERE TO ")
            console.readInt()
            console.println("I SMELL A WUMPUS!")
            console.print("YOUR ARE IN ROOM ")
            console.println(18)
            console.print("TUNNELS LEAD TO ")
            console.print(9)
            console.print(" "); console.print(17)
            console.print(" "); console.println(19)
            console.println("")
            console.print("SHOOT OR MOVE (S-M) ")
            console.readln()
            console.print("WHERE TO ")
            console.readInt()
            console.println("... OOPS! BUMPED A WUMPUS!")
            console.println("")
            console.println("I SMELL A WUMPUS!")
            console.println("I FEEL A DRAFT")
            console.println("I FEEL A DRAFT")
            console.print("YOUR ARE IN ROOM ")
            console.println(9)
            console.print("TUNNELS LEAD TO ")
            console.print(8)
            console.print(" "); console.print(10)
            console.print(" "); console.println(18)
            console.println("")
            console.print("SHOOT OR MOVE (S-M) ")
            console.readln()
            console.print("NO. OF ROOMS (1-5) ")
            console.readInt()
            console.print("ROOM # ")
            console.readInt()
            console.println("MISSED")
            console.println("TSK TSK TSK - WUMPUS GOT YOU!")
            console.println("HA HA HA - YOU LOSE!")

            console.print("SAME SETUP (Y-N)")
            console.readln()
            console.println("HUNT THE WUMPUS")
            console.println("")
            console.println("I FEEL A DRAFT")
            console.print("YOUR ARE IN ROOM ")
            console.println(1)
            console.print("TUNNELS LEAD TO ")
            console.print(2)
            console.print(" "); console.print(5)
            console.print(" "); console.println(8)
            console.println("")
            console.print("SHOOT OR MOVE (S-M) ")
            console.readln()
            console.println("AHA! YOU GOT THE WUMPUS!")
            console.println("HEE HEE HEE - THE WUMPUS'LL GET YOU NEXT TIME!!")
        }
    }
}