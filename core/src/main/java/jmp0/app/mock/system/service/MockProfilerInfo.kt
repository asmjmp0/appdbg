package jmp0.app.mock.system.service

import android.app.ProfilerInfo
import jmp0.app.mock.annotations.ClassReplaceTo
import org.apache.log4j.Logger

@ClassReplaceTo("")
class MockProfilerInfo:ProfilerInfo("", null,0,true) {
    private val logger = Logger.getLogger(MockProfilerInfo::class.java)
    
}