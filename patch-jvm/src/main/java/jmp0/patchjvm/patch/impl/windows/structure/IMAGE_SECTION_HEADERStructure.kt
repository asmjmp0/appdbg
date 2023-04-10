package jmp0.patchjvm.patch.impl.windows.structure

import com.sun.jna.Structure

//typedef struct DECLSPEC_ALIGN(16) _MEMORY_BASIC_INFORMATION64 {
//    ULONGLONG BaseAddress;
//    ULONGLONG AllocationBase;
//    DWORD     AllocationProtect;
//    DWORD     __alignment1;
//    ULONGLONG RegionSize;
//    DWORD     State;
//    DWORD     Protect;
//    DWORD     Type;
//    DWORD     __alignment2;
//} MEMORY_BASIC_INFORMATION64, *PMEMORY_BASIC_INFORMATION64;
@Structure.FieldOrder("Name","VirtualSize","VirtualAddress","SizeOfRawData","PointerToRawData","PointerToRelocations",
    "PointerToLinenumbers","NumberOfRelocations","NumberOfLinenumbers","Characteristics")
class IMAGE_SECTION_HEADERStructure:Structure() {
    @JvmField var Name:ByteArray = ByteArray(8)
    @JvmField var VirtualSize:Int = 0
    @JvmField var VirtualAddress:Int = 0
    @JvmField var SizeOfRawData:Int? = null
    @JvmField var PointerToRawData:Int? = null
    @JvmField var PointerToRelocations:Int? =null
    @JvmField var PointerToLinenumbers:Int? =null
    @JvmField var NumberOfRelocations:Short? = null
    @JvmField var NumberOfLinenumbers:Short? = null
    @JvmField var Characteristics:Int? = null

    fun name() = String(Name)

}