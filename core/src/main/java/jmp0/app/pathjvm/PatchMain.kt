package jmp0.app.pathjvm

import com.sun.jna.Native
import com.sun.jna.Platform

class PatchMain {
    val cl = Native.load(if (Platform.isWindows()) "msvcrt" else "c", CLibrary::class.java) as CLibrary

    fun patch() {
    }
}