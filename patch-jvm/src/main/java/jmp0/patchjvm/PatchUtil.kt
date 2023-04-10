package jmp0.patchjvm

import java.util.*

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2023/4/7
 */
object PatchUtil {
    private fun findSubByteArrayInner(byteArray: ByteArray,idx:Int,find: ByteArray): Boolean {
        if(idx + find.size > byteArray.size) return false
        for (i in find.indices){
            if(find[i] != byteArray[i+idx]) return false
        }
        return true
    }

    fun findSubByteArray(byteArray: ByteArray,find:ByteArray):List<Int>{
        val list = LinkedList<Int>()
        for(idx in byteArray.indices){
            if(byteArray[idx] == find[0]){
                if(findSubByteArrayInner(byteArray,idx,find))
                    list.add(idx)
            }
        }
        return list
    }
}