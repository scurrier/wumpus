package wumpus

import Console
import GameMap
import GameState

class UI(val console: Console) {

    fun provideInstructions() {
        if (askIfInstructionsNeeded()) {
            giveInstructions()
        }
    }

    fun askIfInstructionsNeeded(): Boolean {
        console.print("INSTRUCTIONS (Y-N) ")
        return readIsYes()
    }

    private fun readIsYes(): Boolean {
        val iS = console.readln()
        return (iS != 'N' && iS != 'n')
    }

    fun askIfNewSetup(): Boolean {
        console.print("SAME SETUP (Y-N)")
        return !readIsYes()
    }

    fun askForAction(): Int {
        console.print("SHOOT OR MOVE (S-M) ")
        return when (console.readln()) {
            'S', 's' -> 1
            'M', 'm' -> 2
            else -> askForAction()
        }
    }


    fun giveInstructions() {
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

    fun showTitle() {
        console.println("HUNT THE WUMPUS")
    }

    fun printRoomDescription(gameState: GameState, map: GameMap) {
        val ll = gameState.playerRoom
        console.println("")
        (2..6).forEach { j ->
            if (!map.roomHasPathTo(gameState.playerRoom, gameState.locations[j])) return@forEach
            when (j) {
                2 -> console.println("I SMELL A WUMPUS!")
                3, 4 -> console.println("I FEEL A DRAFT")
                5, 6 -> console.println("BATS NEARBY!")
            }
        }
        console.println("YOU ARE IN ROOM ${gameState.playerRoom}")
        console.println("TUNNELS LEAD TO ${map.tunnelFrom(ll, 1)} ${map.tunnelFrom(ll, 2)} ${map.tunnelFrom(ll, 3)}")
        console.println("")
    }

}
