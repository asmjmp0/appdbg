package jmp0.patchjvm.patch.impl.osx.structure

import com.sun.jna.Structure
import com.sun.jna.Structure.FieldOrder

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2023/4/7
 */

//struct segment_command_64 { /* for 64-bit architectures */
//    uint32_t	cmd;		/* LC_SEGMENT_64 */
//    uint32_t	cmdsize;	/* includes sizeof section_64 structs */
//    char		segname[16];	/* segment name */
//    uint64_t	vmaddr;		/* memory address of this segment */
//    uint64_t	vmsize;		/* memory size of this segment */
//    uint64_t	fileoff;	/* file offset of this segment */
//    uint64_t	filesize;	/* amount to map from the file */
//    vm_prot_t	maxprot;	/* maximum VM protection */
//    vm_prot_t	initprot;	/* initial VM protection */
//    uint32_t	nsects;		/* number of sections in segment */
//    uint32_t	flags;		/* flags */
//};
@FieldOrder("cmd","cmdsize","segname","vmaddr","vmsize","fileoff","filesize","maxprot","initprot","nsects","flags")
class SegmentCommand64: Structure() {
    @JvmField var cmd:Int? = null
    @JvmField var cmdsize:Int? = null
    @JvmField var segname: ByteArray? = ByteArray(16)
    @JvmField var vmaddr:Long? = null
    @JvmField var vmsize:Long? = null
    @JvmField var fileoff:Long? = null
    @JvmField var filesize:Long? = null
    @JvmField var maxprot:Int? = null
    @JvmField var initprot:Int? = null
    @JvmField var nsects:Int? = null
    @JvmField var flags:Int? = null

    fun getSegnameStr() = String(segname!!)

}