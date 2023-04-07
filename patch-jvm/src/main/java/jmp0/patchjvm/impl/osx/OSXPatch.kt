package jmp0.patchjvm.impl.osx

import com.sun.jna.Native
import com.sun.jna.Platform
import com.sun.jna.Pointer
import com.sun.jna.Structure
import jmp0.patchjvm.library.CLibrary
import jmp0.patchjvm.IPatch
import jmp0.patchjvm.PatchUtil
import jmp0.patchjvm.impl.osx.structure.LoadCommand64
import jmp0.patchjvm.impl.osx.structure.SegmentCommand64
import java.util.LinkedList
import kotlin.Exception

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2023/4/7
 */
class OSXPatch(override val jvmLibraryName: String = "libjvm.dylib") :IPatch {
    private val dyldLibrary:DyldLibrary = Native.load("c",DyldLibrary::class.java) as DyldLibrary

    private fun patchImpl(pointer: Pointer,size:Long){
        val bytes = pointer.getByteArray(0,size.toInt())
        val find = byteArrayOf(0x6a,0x61,0x76,0x61,0x0)
        val list = PatchUtil.findSubByteArray(bytes,find)
        list.forEach{
            var str = pointer.getString(it.toLong())
            println("OSXPatch find ${str} at __TEXT[${it}] before patched")
            pointer.setString(it.toLong(),"#####")
            str = pointer.getString(it.toLong())
            println("OSXPatch ${str} at __TEXT[${it}] after patched")
            return
        }
    }

    override fun patch(cLibrary: CLibrary) {
        if(!Platform.is64Bit()) throw Exception("OSX not 64 bit,not support.")
        val count = dyldLibrary._dyld_image_count()
        for (i in 0 until count){
            val name = dyldLibrary._dyld_get_image_name(i)
            if(name.contains(jvmLibraryName)){
                //find the libjvm.dylib
                val header = dyldLibrary._dyld_get_image_header(i)
                var pointer = Pointer(Pointer.nativeValue(header.pointer) + header.size())
                var loadCommand64 = Structure.newInstance(LoadCommand64::class.java,pointer).apply { read() }
                for (c_idx in 0 until header.ncmds!!){
                    if (loadCommand64.cmd == 25){ // LC_SEGMENT_64
                        val segmentCommand64 = Structure.newInstance(SegmentCommand64::class.java,pointer).apply { read() }
                        val name = segmentCommand64.getSegnameStr()
                        if(name.startsWith("__TEXT")){
                            val base = Pointer(Pointer.nativeValue(header.pointer) + segmentCommand64.vmaddr!!)
                            val size = segmentCommand64.vmsize!!
                            if(cLibrary.mprotect(base,size.toInt(),7) == 0){
                                patchImpl(base,size)
                            }else throw Exception("mprotect error.")
                            cLibrary.mprotect(base,size.toInt(),5)
                            return
                        }
                    }
                    pointer = Pointer(Pointer.nativeValue(pointer) + loadCommand64.cmdsize!!)
                    loadCommand64 = Structure.newInstance(LoadCommand64::class.java,pointer).apply { read() }
                }

            }
        }
    }
}