package jmp0.patchjvm.patch.impl.windows

import com.sun.jna.Native
import com.sun.jna.Platform
import com.sun.jna.Pointer
import com.sun.jna.Structure
import jmp0.patchjvm.PatchUtil
import jmp0.patchjvm.library.CLibrary
import jmp0.patchjvm.patch.DynamicPatch
import jmp0.patchjvm.patch.impl.windows.structure.IMAGE_SECTION_HEADERStructure

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2023/4/7
 */
class WindowsPatch(override val cLibrary: CLibrary, override val jvmLibraryName: String = "jvm.dll") : DynamicPatch() {
    private val kernel32Library: Kernel32Library = Native.load("Kernel32", Kernel32Library::class.java) as Kernel32Library
    override fun patch() {
        if(!Platform.is64Bit()) throw  Exception("WindowsPatch only support 64bit now.")
        val mHandle = kernel32Library.GetModuleHandleA(jvmLibraryName)
        val ntHeaderAddressPointer = Pointer(Pointer.nativeValue(mHandle) + 0x3c)
        val ntHeaderPointer = Pointer(ntHeaderAddressPointer.getInt(0) + Pointer.nativeValue(mHandle))
        var sectionPointer = Pointer(Pointer.nativeValue(ntHeaderPointer) + 264)
        var header = Structure.newInstance(IMAGE_SECTION_HEADERStructure::class.java,sectionPointer).apply{read()}
        while (header.VirtualAddress != 0){
            val n = header.name()
            if(header.name().startsWith(".rdata")){
                val bs = mHandle.getByteArray(header.VirtualAddress.toUInt().toLong(),header.VirtualSize)
                val list = PatchUtil.findSubByteArray(bs,pattern)
                if(list.isEmpty()){
                    println("ERROR: pattern not fount")
                    return
                }
                val idx = list[0]
                val rdataPointer = Pointer(Pointer.nativeValue(mHandle) + header.VirtualAddress.toUInt().toLong())
                val lpflOldProtect = cLibrary.malloc(4)
                if (kernel32Library.VirtualProtect(rdataPointer,header.VirtualSize,Kernel32Library.PAGE_EXECUTE_READWRITE,lpflOldProtect)){
                    rdataPointer.setString(idx.toLong(),replaceString)
                    kernel32Library.VirtualProtect(rdataPointer,header.VirtualSize,lpflOldProtect.getInt(0),lpflOldProtect)
                }else{
                    cLibrary.free(lpflOldProtect)
                    println("ERROR: VirtualProtect Error")
                    return
                }
                cLibrary.free(lpflOldProtect)
                return
            }
            sectionPointer = Pointer(Pointer.nativeValue(sectionPointer) + header.size())
            header = Structure.newInstance(IMAGE_SECTION_HEADERStructure::class.java,sectionPointer).apply{read()}
        }

    }
}