package jmp0.app.mock.system

import jmp0.app.mock.annotations.ClassReplaceTo
import org.apache.harmony.dalvik.ddmc.Chunk
import org.apache.log4j.Logger

@ClassReplaceTo("org.apache.harmony.dalvik.ddmc.DdmServer")
class DdmServer {

    companion object{
        val logger = Logger.getLogger(DdmServer::class.java).also { it.warn("DdmServer don't support ") }

        @JvmStatic
        fun nativeSendChunk(type:Int,data:ByteArray,offset:Int,length:Int){
            return
        }

        @JvmStatic
        fun sendChunk(chunk:Chunk){

        }
    }

}