package jmp0.app.interceptor.unidbg

import com.github.unidbg.linux.android.dvm.DvmClass
import com.github.unidbg.linux.android.dvm.DvmObject

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/30
 */
class DvmObjectWrapper(dvmClass: DvmClass,val obj:Any?): DvmObject<Any>(dvmClass,obj)