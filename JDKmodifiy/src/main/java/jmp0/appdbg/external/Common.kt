package jmp0.appdbg.external

import java.io.File
import java.util.*
import kotlin.collections.HashMap

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/5/17
 */
object Common {
    const val rootPackageName = "jmp0.appdbg.external"
    const val creatorName = "creator"

    private const val osxPackageName = "osx"
    private const val windowsPackageName = "windows"
    private const val linuxPackageName = "linux"

    val rtFile = File(System.getProperty("java.home"),"lib${File.separator}rt.jar")
    val rtBakFile = File("temp${File.separator}JDK","rt.jar.bak").apply {
        //create bak file
        parentFile.mkdirs()
        if (!exists()) {
            createNewFile()
            writeBytes(rtFile.readBytes())
        }
        println("back rt.jar path is $canonicalPath")
    }

    const val modifiedSig = "appdbg_modified"

    val systemNowPackage = System.getProperty("os.name").run {
        when(this){
            "Mac OS X"-> osxPackageName
            else -> throw Exception("os not support")
        }
    }

    /**
     * @key target class
     * @value creator class
     */
    val generateMap:HashMap<String, String> = HashMap()
}