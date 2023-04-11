package jmp0.patchjvm.patch.impl.linux

import com.sun.jna.Pointer
import jmp0.patchjvm.PatchUtil
import jmp0.patchjvm.library.CLibrary
import jmp0.patchjvm.patch.DynamicPatch
import java.io.File
import java.util.LinkedList

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2023/4/7
 */
class LinuxPatch(override val cLibrary: CLibrary, override val jvmLibraryName: String = "libjvm.so") : DynamicPatch() {
    override fun patch() {
        val jvmMapsLines = File("/proc/self/maps").readLines().filter { it.endsWith(jvmLibraryName) }
        val segmentList = LinkedList<Pair<Pointer,Long>>()
        jvmMapsLines.forEach {
            if (it.contains("r-x")){
                val spl = it.split(' ')
                val ps = spl[0].split('-')
                segmentList.push(Pair(Pointer(ps[0].toLong(16)),ps[1].toLong(16) - ps[0].toLong(16)))
            }
        }
        if (!segmentList.isEmpty()){
            val pair = segmentList[0]
            val bs = pair.first.getByteArray(0,pair.second.toInt())
            //checkPatched
            if(PatchUtil.findSubByteArray(bs,replaceString.toByteArray()).isNotEmpty()) return
            val find = PatchUtil.findSubByteArray(bs,pattern)
            if (find.isEmpty()){
                println("ERROR: LinuxPatch not find pattern.")
                return
            }
            if (this.cLibrary.mprotect(pair.first,pair.second.toInt(),CLibrary.PROT_EXEC or CLibrary.PROT_READ or CLibrary.PROT_WRITE) == 0){
                pair.first.setString(find[0].toLong(), replaceString)
                this.cLibrary.mprotect(pair.first,pair.second.toInt(),CLibrary.PROT_EXEC or CLibrary.PROT_READ)
            }else{
                println("ERROR: LinuxPatch mprotect error.")
                return
            }

        }else{
            println("ERROR: LinuxPatch not find r-w segment.")
            return
        }
    }
}