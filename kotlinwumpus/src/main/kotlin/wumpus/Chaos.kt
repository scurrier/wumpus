package wumpus

import java.util.Random

internal class Chaos(val random: Random) {
    fun pickRoom(): Int {
        return random.nextInt(20) + 1
    }

    fun pickTunnel(): Int {
        return random.nextInt(3) + 1
    }

    fun pickWumpusMovement(): Int {
        return random.nextInt(4) + 1
    }
}
