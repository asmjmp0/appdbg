package jmp0.patchjvm.patch

import jmp0.patchjvm.PatchUtil
import java.io.File

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2023/4/8
 */

//replace java with #
abstract class DynamicPatch : IPatch {
    //C style string java\0
    protected val pattern:ByteArray
        get() =  if(PatchUtil.versionString.startsWith("1.8")) byteArrayOf(0x6a,0x61,0x76,0x61,0x2f,0x0)
                 else byteArrayOf(0x6a,0x61,0x76,0x61,0x0)

    protected val replaceString = "a#'m"
}