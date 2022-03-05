package jmp0.dex

import jmp0.conf.CommonConf
import java.io.File
import java.lang.Exception
import java.net.URL
import java.net.URLClassLoader

class XDexClassLoader:ClassLoader() {
    companion object {
        val instance: XDexClassLoader by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { XDexClassLoader() }
    }

    private data class DefineStatus(val clazz: Class<*>?,val className:String)

    private fun privateDefineClass(name: String?,data: ByteArray,off:Int,size:Int): DefineStatus {
        return try {
            DefineStatus(defineClass(name,data,off,size),"")
        }catch (e:ClassNotFoundException){
            DefineStatus(null,e.message!!)
        }catch (e:NoClassDefFoundError){
            DefineStatus(null,e.message!!)
        }
    }

    private fun findFromAndroidFrameWork(className: String): File? {
        val fileName = className.replace('/','_')+".class"
        File(CommonConf.tempDirName+File.separator+CommonConf.frameworkDirName).listFiles()!!.forEach {
            val f = File(it,fileName)
            if (f.exists()) return f
        }
        return null
    }


    fun loadClass(file: File): Class<*>? {
        val data = file.readBytes()
        while (true){
            val res = privateDefineClass(null,data,0,data.size)
            if(res.clazz != null) return res.clazz
            val f = findFromAndroidFrameWork(res.className)
            if (f == null) {
                // TODO: 2022/3/6 留接口等待实现
                println("${res.className} no found")
                return null
            }else loadClass(f)
        }
    }

    fun loadClass(data: ByteArray):Class<*>{
        return defineClass(null,data,0,data.size)
    }

}