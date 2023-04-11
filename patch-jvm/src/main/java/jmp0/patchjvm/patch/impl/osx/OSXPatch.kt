package jmp0.patchjvm.patch.impl.osx

import com.sun.jna.Native
import com.sun.jna.Platform
import com.sun.jna.Pointer
import com.sun.jna.Structure
import jmp0.patchjvm.library.CLibrary
import jmp0.patchjvm.PatchUtil
import jmp0.patchjvm.patch.DynamicPatch
import jmp0.patchjvm.patch.StaticPatch
import jmp0.patchjvm.patch.impl.osx.structure.LoadCommand64
import jmp0.patchjvm.patch.impl.osx.structure.SegmentCommand64
import java.io.File
import kotlin.Exception

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2023/4/7
 */
class OSXPatch( workDir:File, override val cLibrary: CLibrary,override val jvmLibraryName: String = "libjvm.dylib") : StaticPatch(workDir) {

    private val dyldLibrary: DyldLibrary = Native.load("c", DyldLibrary::class.java) as DyldLibrary

    private fun patchImpl(pointer: Pointer,size:Long){
        val bytes = pointer.getByteArray(0,size.toInt())
        //checkPatched
        if(PatchUtil.findSubByteArray(bytes,replaceString.toByteArray()).isNotEmpty()) return
        val list = PatchUtil.findSubByteArray(bytes,pattern)
        list.forEach{
            var str = pointer.getString(it.toLong())
            println("OSXPatch find ${str} at __TEXT[${it}] before patched")
            pointer.setString(it.toLong(),replaceString)
            str = pointer.getString(it.toLong())
            println("OSXPatch ${str} at __TEXT[${it}] after patched")
            return
        }
    }

    override fun patchStatic(file:File){
        // security find-identity -v -p codesigning
        // sudo codesign --force --timestamp --sign <name of certificate> libjvm.dylib
        // 88
        val bytes = file.readBytes()
        val bakName = file.name+".bak"
        val bakFile = File(patchJDKDir,bakName)
        bakFile.writeBytes(bytes)
        bytes[88] = 7
        if(Platform.isARM()){
            println("INFO: patch OSX M1")
            val strIdxList = PatchUtil.findSubByteArray(bytes,pattern)
            if (strIdxList.isEmpty()){
                println("ERROR: pattern not found!")
                return
            }
            val strIdx = strIdxList[0]
            val list = PatchUtil.findSubByteArray(bytes, generateStrPtrPattern(strIdx))
            if(list.isNotEmpty()){
                val idx = list[0]
                bytes[idx + 1] = (bytes[idx + 1] + 4).toByte()
            }else{
                println("ERROR: strPtrPattern not found!")
                return
            }
        }
        val patchFile = File(patchJDKDir,file.name)
        patchFile.writeBytes(bytes)
        println("INFO: patch file has been created at ${patchFile.canonicalPath}")
        println("INFO: bak file has been created at ${bakFile.canonicalPath}")
        println("patch it at your own risk!!!")
        println("you need to do something:\n" +
                "1. execute command: ```security find-identity -v -p codesigning``` to find available certification.\n" +
                "2. execute command: ```sudo codesign --force --timestamp --sign <name of certificate> ${patchFile.canonicalPath}``` to replace libjvm.dylibc signature.\n" +
                "3. execute command: ```sudo cp ${patchFile.canonicalPath} ${file.canonicalPath}``` to replace patch file.")

    }

    override fun patch() {
        if(!Platform.is64Bit()) throw Exception("OSX not 64 bit,not support.")
        val count = dyldLibrary._dyld_image_count()
        for (i in 0 until count){
            val name = dyldLibrary._dyld_get_image_name(i)
            if(name.contains(jvmLibraryName)){
                //find the libjvm.dylib
                val header = dyldLibrary._dyld_get_image_header(i)
                var pointer = Pointer(Pointer.nativeValue(header.pointer) + header.size())
                var loadCommand64 = Structure.newInstance(LoadCommand64::class.java,pointer).apply { read() }
                for (c_idx in 0 until header.ncmds){
                    if (loadCommand64.cmd == 25){ // LC_SEGMENT_64
                        val segmentCommand64 = Structure.newInstance(SegmentCommand64::class.java,pointer).apply { read() }
                        if(segmentCommand64.getSegnameStr().startsWith("__TEXT")){
                            val base = Pointer(Pointer.nativeValue(header.pointer) + segmentCommand64.vmaddr)
                            val size = segmentCommand64.vmsize
                            if(segmentCommand64.maxprot == 7 && Platform.isARM()) return
                            if (segmentCommand64.maxprot < 7){
                                println("INFO: Patch libjvm.dylib file")
                                patchStatic(File(name))
                                return
                            }
                            if(cLibrary.mprotect(base,size.toInt(),CLibrary.PROT_EXEC or CLibrary.PROT_READ or CLibrary.PROT_WRITE) == 0){
                                patchImpl(base,size)
                            }else throw Exception("ERROR: mprotect error.")
                            cLibrary.mprotect(base,size.toInt(),segmentCommand64.initprot)
                            return
                        }
                    }
                    pointer = Pointer(Pointer.nativeValue(pointer) + loadCommand64.cmdsize)
                    loadCommand64 = Structure.newInstance(LoadCommand64::class.java,pointer).apply { read() }
                }

            }
        }
    }
}