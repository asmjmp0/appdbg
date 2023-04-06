package jmp0.patchjvm

import com.sun.jna.Native
import com.sun.jna.Platform

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2023/4/7
 */
class PatchMain {
    val cl = Native.load(if (Platform.isWindows()) "msvcrt" else "c", CLibrary::class.java) as CLibrary

    fun patch() {
    }
}