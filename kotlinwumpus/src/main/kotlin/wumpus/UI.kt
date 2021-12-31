package wumpus

import Console

internal class UI(val console: Console) {

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

    fun askForAction(): PlayerAction {
        console.print("SHOOT OR MOVE (S-M) ")
        return when (console.readln()) {
            'S', 's' -> Shoot
            'M', 'm' -> Move
            else -> askForAction()
        }
    }

    fun askForNumberOfRooms(): Int {
        var a: Int
        do {
            console.print("NO. OF ROOMS (1-5) ")
            a = console.readInt()
        } while (a < 1 || a > 5)
        return a
    }

    fun askForArrowPath(roomCount: Int): Array<Int> {
        val path = mutableListOf<Int>()
        repeat(roomCount) {
            path.add(askForNextRoom(path))
        }
        return path.toTypedArray()
    }

    fun askForNextRoom(path: List<Int>): Int {
        var nextRoom: Int
        do {
            console.print("ROOM # ")
            nextRoom = console.readInt()
            val invalidPath = (path.size >= 2) && path[path.size - 2] == nextRoom
            if (invalidPath) console.println("ARROWS AREN'T THAT CROOKED - TRY ANOTHER ROOM")
        } while (invalidPath)
        return nextRoom
    }

    fun askForDestinationRoom(): Int {
        var result: Int
        do {
            console.print("WHERE TO ")
            result = console.readInt()
        } while (result < 1 || result > 20)
        return result
    }

    fun askForValidDestinationRoom(gameState: GameState): Int {
        var result: Int
        do {
            result = askForDestinationRoom()
            val isValidRoom = result in gameState.playerRoom || gameState.playerRoom.isIndex(result)
            if (!isValidRoom) {
                console.print("NOT POSSIBLE - ")
            }
        } while (!isValidRoom)
        return result
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

    fun printRoomDescription(gameState: GameState) {
        val room = gameState.playerRoom
        console.println("")
        (1..5).forEach { j ->
            if (!room.hasPathTo(gameState.locations[j])) return@forEach
            when (j) {
                1 -> console.println("I SMELL A WUMPUS!")
                2, 3 -> console.println("I FEEL A DRAFT")
                4, 5 -> console.println("BATS NEARBY!")
            }
        }
        console.println("YOU ARE IN ROOM $room")
        console.println("TUNNELS LEAD TO ${room[1]} ${room[2]} ${room[3]}")
        console.println("")
    }

    fun reportShotWumpus() {
        console.println("AHA! YOU GOT THE WUMPUS!")
    }

    fun reportShotSelf() {
        console.println("OUCH! ARROW GOT YOU!")
    }

    fun reportMissedShot() {
        console.println("MISSED")
    }

    fun reportWumpusAtePlayer() {
        console.println("TSK TSK TSK - WUMPUS GOT YOU!")
    }

    fun reportWumpusBump() {
        console.println("... OOPS! BUMPED A WUMPUS!")
    }

    fun reportFall() {
        console.println("YYYYIIIIEEEE . . . FELL IN PIT")
    }

    fun reportBatEncounter() {
        console.println("ZAP--SUPER BAT SNATCH! ELSEWHEREVILLE FOR YOU!")
    }

    fun reportLoss() {
        console.println("HA HA HA - YOU LOSE!")
    }

    fun reportWin() {
        console.println("HEE HEE HEE - THE WUMPUS'LL GET YOU NEXT TIME!!")
    }
}
