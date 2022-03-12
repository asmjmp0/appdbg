package jmp0.app.mock.system

import jmp0.app.mock.MockedBy
import jmp0.app.mock.ReplaceTo
import org.apache.harmony.dalvik.ddmc.Chunk
import org.apache.log4j.Logger

@ReplaceTo("org.apache.harmony.dalvik.ddmc.DdmServer")
class DdmServer {

    companion object{
        val logger = Logger.getLogger(DdmServer::class.java).also { it.warn("DdmServer don't support ") }

        @JvmStatic
        @MockedBy("asmjmp0")
        fun nativeSendChunk(type:Int,data:ByteArray,offset:Int,length:Int){
            return
        }

        @JvmStatic
        @MockedBy("asmjmp0")
        fun sendChunk(chunk:Chunk){

        }
    }

}