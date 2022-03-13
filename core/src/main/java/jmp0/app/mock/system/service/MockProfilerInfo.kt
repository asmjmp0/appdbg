package jmp0.app.mock.system.service

import android.app.ProfilerInfo
import android.os.ParcelFileDescriptor
import jmp0.app.mock.ClassReplaceTo
import org.apache.log4j.Logger
import java.io.File

@ClassReplaceTo("")
class MockProfilerInfo:ProfilerInfo("", null,0,true) {
    private val logger = Logger.getLogger(MockProfilerInfo::class.java)
    
}