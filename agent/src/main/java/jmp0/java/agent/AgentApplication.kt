package jmp0.java.agent
/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2023/4/6
 */

import jmp0.java.agent.io.IORedirect
import java.lang.instrument.Instrumentation

class AgentApplication {
    companion object{
        private val modifyRun = arrayOf(IORedirect())
        @JvmStatic
        fun premain(arg:String?, instrumentation: Instrumentation){

            modifyRun.forEach{it.run(instrumentation)}

        }
    }
}