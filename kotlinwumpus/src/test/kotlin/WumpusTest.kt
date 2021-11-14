import io.mockk.every
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import io.mockk.mockk
import io.mockk.verifyOrder
import java.util.*

class WumpusTest {
    private val console = mockk<Console>()

    @BeforeEach
    fun setUp() {
        Wumpus.random = Random(0) 
        Wumpus.console = console
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

    @Test
    fun pinningTest() {
        Wumpus.earlyExitHack = 370
        Wumpus.earlyExitHack = 336

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
        Wumpus.main(arrayOf())
        verifyOrder {
            console.print("INSTRUCTIONS (Y-N) ")
            console.readln()
            console.println("WELCOME TO 'HUNT THE WUMPUS'")
            console.println("  THE WUMPUS LIVES IN A CAVE OF 20 ROOMS. EACH ROOM")
            console.println("HAS 3 TUNNELS LEADING TO OTHER ROOMS. (LOOK AT A")
            console.println("DODECAHEDRON TO SEE HOW THIS WORKS-IF YOU DON'T KNOW")
            console.println("WHAT A DODECAHEDRON IS, ASK SOMEONE)")
            console.println("")
            console.println("     HAZARDS:")
            console.println(" BOTTOMLESS PITS - TWO ROOMS HAVE BOTTOMLESS PITS IN THEM")
            console.println("     IF YOU GO THERE, YOU FALL INTO THE PIT (& LOSE!)")
            console.println(" SUPER BATS - TWO OTHER ROOMS HAVE SUPER BATS. IF YOU")
            console.println("     GO THERE, A BAT GRABS YOU AND TAKES YOU TO SOME OTHER")
            console.println("     ROOM AT RANDOM. (WHICH MAY BE TROUBLESOME)")
            console.input("HIT RETURN TO CONTINUE")
            console.println("     WUMPUS:")
            console.println(" THE WUMPUS IS NOT BOTHERED BY HAZARDS (HE HAS SUCKER")
            console.println(" FEET AND IS TOO BIG FOR A BAT TO LIFT).  USUALLY")
            console.println(" HE IS ASLEEP.  TWO THINGS WAKE HIM UP: YOU SHOOTING AN")
            console.println("ARROW OR YOU ENTERING HIS ROOM.")
            console.println("     IF THE WUMPUS WAKES HE MOVES (P=.75) ONE ROOM")
            console.println(" OR STAYS STILL (P=.25).  AFTER THAT, IF HE IS WHERE YOU")
            console.println(" ARE, HE EATS YOU UP AND YOU LOSE!")
            console.println("")
            console.println("     YOU:")
            console.println(" EACH TURN YOU MAY MOVE OR SHOOT A CROOKED ARROW")
            console.println("   MOVING:  YOU CAN MOVE ONE ROOM (THRU ONE TUNNEL)")
            console.println("   ARROWS:  YOU HAVE 5 ARROWS.  YOU LOSE WHEN YOU RUN OUT")
            console.println("   EACH ARROW CAN GO FROM 1 TO 5 ROOMS. YOU AIM BY TELLING")
            console.println("   THE COMPUTER THE ROOM#S YOU WANT THE ARROW TO GO TO.")
            console.println("   IF THE ARROW CAN'T GO THAT WAY (IF NO TUNNEL) IT MOVES")
            console.println("   AT RANDOM TO THE NEXT ROOM.")
            console.println("     IF THE ARROW HITS THE WUMPUS, YOU WIN.")
            console.println("     IF THE ARROW HITS YOU, YOU LOSE.")
            console.input("HIT RETURN TO CONTINUE")
            console.println("    WARNINGS:")
            console.println("     WHEN YOU ARE ONE ROOM AWAY FROM A WUMPUS OR HAZARD,")
            console.println("     THE COMPUTER SAYS:")
            console.println(" WUMPUS:  'I SMELL A WUMPUS'")
            console.println(" BAT   :  'BATS NEARBY'")
            console.println(" PIT   :  'I FEEL A DRAFT'")
            console.println("")
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