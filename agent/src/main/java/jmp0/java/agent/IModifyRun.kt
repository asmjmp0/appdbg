package jmp0.java.agent
/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2023/4/6
 */

import java.lang.instrument.Instrumentation

interface IModifyRun {
    fun run(instrumentation: Instrumentation)
}