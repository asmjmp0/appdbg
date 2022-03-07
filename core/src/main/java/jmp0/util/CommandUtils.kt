package jmp0.util

import kotlin.concurrent.thread

object CommandUtils {

    fun exec(array: Array<String>): Process = Runtime.getRuntime().exec(array)

    fun readProcessPipe(process: Process){
        val stdinBR = process.inputStream.bufferedReader()
        val stderrBR = process.errorStream.bufferedReader()
        thread {
            while (true){
                val inL = stdinBR.readLine()
                val errL = stderrBR.readLine()
                if ((inL == null) and (errL == null)) break
                inL?.also { println(it) }
                errL?.also { System.err.println(it) }
            }
        }
    }
}