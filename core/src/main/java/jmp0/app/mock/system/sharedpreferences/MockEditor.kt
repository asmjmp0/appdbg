package jmp0.app.mock.system.sharedpreferences

import android.content.SharedPreferences
import com.google.android.collect.Maps
import jmp0.app.mock.annotations.ClassReplaceTo
import org.apache.log4j.Logger

@ClassReplaceTo("")
class MockEditor: SharedPreferences.Editor{
    private val logger = Logger.getLogger(MockEditor::class.java)
    val valueMap: HashMap<String, Any> = Maps.newHashMap()

    override fun putString(p0: String, p1: String): SharedPreferences.Editor {
        synchronized(this) {
            valueMap[p0] = p1
            return this
        }
    }

    override fun putStringSet(p0: String, p1: MutableSet<String>): SharedPreferences.Editor {
        synchronized(this) {
            valueMap[p0] = p1
            return this
        }
    }

    override fun putInt(p0: String, p1: Int): SharedPreferences.Editor {
        synchronized(this) {
            valueMap[p0] = p1
            return this
        }
    }

    override fun putLong(p0: String, p1: Long): SharedPreferences.Editor {
        synchronized(this) {
            valueMap[p0] = p1
            return this
        }
    }

    override fun putFloat(p0: String, p1: Float): SharedPreferences.Editor {
        synchronized(this) {
            valueMap[p0] = p1
            return this
        }
    }

    override fun putBoolean(p0: String, p1: Boolean): SharedPreferences.Editor {
        synchronized(this) {
            valueMap[p0] = p1
            return this
        }
    }

    override fun remove(p0: String): SharedPreferences.Editor {
        synchronized(this) {
            valueMap.remove(p0)
            return this
        }
    }

    override fun clear(): SharedPreferences.Editor {
        synchronized(this) {
            valueMap.clear()
            return this
        }
    }

    override fun commit(): Boolean {
            logger.debug("mock SharedPreferences editor will not save anything to file")
            return true
    }

    override fun apply() {
        logger.debug("mock SharedPreferences editor will not save anything to file")
    }

}