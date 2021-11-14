import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class Console {
    fun print(s: String) {
        kotlin.io.print(s)
    }

    fun print(s: Int) {
        kotlin.io.print(s)
    }

    fun println(s: String) {
        kotlin.io.println(s)
    }

    fun println(s: Int) {
        kotlin.io.println(s)
    }

    fun readln(): Char {
        val result = System.`in`.read().toChar()
        System.`in`.read()
        return result
    }

    fun input(s: String) {
        kotlin.io.println(s); System.`in`.read()
    }

    fun readInt(): Int {
        val line: String
        val `is` = BufferedReader(InputStreamReader(System.`in`))
        try {
            line = `is`.readLine()!!
        } catch (e: IOException) {
            return 0
        }
        return Integer.parseInt(line)
    }
}