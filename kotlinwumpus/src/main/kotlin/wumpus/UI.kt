package wumpus

import Console

class UI(val console: Console) {

    fun askIfInstructionsNeeded(): Boolean {
        console.print("INSTRUCTIONS (Y-N) ")
        val iS = console.readln()
        return (iS != 'N' && iS != 'n')
    }
}
