package jmp0.app.mock.system

import jmp0.app.mock.ClassReplaceTo
import org.apache.log4j.Logger

@ClassReplaceTo("java.text.DateFormat")
class DateFormat {
    companion object{
        private val logger = Logger.getLogger(DateFormat::class.java)

        @JvmStatic
        fun set24HourTimePref(flag:Boolean){
            logger.debug("set24HourTimePref $flag called")
        }
    }
}