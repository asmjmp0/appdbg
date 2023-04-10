package jmp0.patchjvm.patch

import com.sun.jna.Platform
import java.io.File
import java.lang.Exception

abstract class StaticPatch(workDir:File): DynamicPatch() {
    /*
     * add x1,x1,xxx
     * movz w2,4
     * */
    private val strARMPtrPattern = byteArrayOf(0x21,0,0, 0x91.toByte(), 0x82.toByte(),0, 0x80.toByte(),0x52)

    protected val patchJDKDir = File(workDir,"temp${File.separator}JDKPatch").apply { if (!isDirectory) mkdirs() }

    protected abstract fun patchStatic(file:File)

    protected fun generateStrPtrPattern(strIdx:Int): ByteArray {
        if (Platform.isARM()){
            var strPtr = strIdx and  0xfff
            strPtr *= 4
            val ret = strARMPtrPattern.clone()
            ret[1] = (strPtr and 0xff).toByte()
            ret[2] = (strPtr shr 8).toByte()
            return ret
        }
        throw Exception("${Platform.ARCH} not support.")
    }
}