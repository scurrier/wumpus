import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyOrder
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import wumpus.Chaos
import wumpus.UI
import java.util.Random

class WumpusTest {
    private val console = mockk<Console>()
    private val testObj = Wumpus()
    @BeforeEach
    fun setUp() {
        testObj.gameState = GameState(Chaos(Random(0)), exitOnWin = true)
        testObj.ui = UI(console)
    }

    @Test
    fun pinningTest() {
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
            console.println("HUNT THE WUMPUS")
            console.println("")
            console.println("I FEEL A DRAFT")
            console.println("YOU ARE IN ROOM 1")
            console.println("TUNNELS LEAD TO 2 5 8")
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
            console.println("YOU ARE IN ROOM 1")
            console.println("TUNNELS LEAD TO 2 5 8")
            console.println("")
            console.print("SHOOT OR MOVE (S-M) ")
            console.readln()
            console.print("WHERE TO ")
            console.readInt()
            console.println("I FEEL A DRAFT")
            console.println("YOU ARE IN ROOM 2")
            console.println("TUNNELS LEAD TO 1 3 10")
            console.println("")
            console.print("SHOOT OR MOVE (S-M) ")
            console.readln()
            console.print("WHERE TO ")
            console.readInt()
            console.println("YOU ARE IN ROOM 3")
            console.println("TUNNELS LEAD TO 2 4 12")
            console.println("")
            console.print("SHOOT OR MOVE (S-M) ")
            console.readln()
            console.print("WHERE TO ")
            console.readInt()
            console.println("BATS NEARBY!")
            console.println("YOU ARE IN ROOM 4")
            console.println("TUNNELS LEAD TO 3 5 14")
            console.println("")
            console.print("SHOOT OR MOVE (S-M) ")
            console.readln()
            console.print("WHERE TO ")
            console.readInt()
            console.println("ZAP--SUPER BAT SNATCH! ELSEWHEREVILLE FOR YOU!")
            console.println("")
            console.println("YOU ARE IN ROOM 12")
            console.println("TUNNELS LEAD TO 3 11 13")
            console.println("")
            console.print("SHOOT OR MOVE (S-M) ")
            console.readln()
            console.print("WHERE TO ")
            console.readInt()
            console.println("BATS NEARBY!")
            console.println("YOU ARE IN ROOM 13")
            console.println("TUNNELS LEAD TO 12 14 20")
            console.println("")
            console.print("SHOOT OR MOVE (S-M) ")
            console.readln()
            console.print("WHERE TO ")
            console.readInt()
            console.println("BATS NEARBY!")
            console.println("YOU ARE IN ROOM 20")
            console.println("TUNNELS LEAD TO 13 16 19")
            console.println("")
            console.print("SHOOT OR MOVE (S-M) ")
            console.readln()
            console.print("WHERE TO ")
            console.readInt()
            console.println("YOU ARE IN ROOM 19")
            console.println("TUNNELS LEAD TO 11 18 20")
            console.println("")
            console.print("SHOOT OR MOVE (S-M) ")
            console.readln()
            console.print("WHERE TO ")
            console.readInt()
            console.println("I SMELL A WUMPUS!")
            console.println("YOU ARE IN ROOM 18")
            console.println("TUNNELS LEAD TO 9 17 19")
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
            console.println("YOU ARE IN ROOM 9")
            console.println("TUNNELS LEAD TO 8 10 18")
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
            console.println("YOU ARE IN ROOM 1")
            console.println("TUNNELS LEAD TO 2 5 8")
            console.println("")
            console.print("SHOOT OR MOVE (S-M) ")
            console.readln()
            console.println("AHA! YOU GOT THE WUMPUS!")
            console.println("HEE HEE HEE - THE WUMPUS'LL GET YOU NEXT TIME!!")
        }
    }
}