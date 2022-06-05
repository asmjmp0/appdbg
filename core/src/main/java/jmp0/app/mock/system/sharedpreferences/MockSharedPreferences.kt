package jmp0.app.mock.system.sharedpreferences

import android.content.SharedPreferences
import jmp0.app.mock.annotations.ClassReplaceTo
import org.apache.log4j.Logger

@ClassReplaceTo("")
class MockSharedPreferences(private val name: String):SharedPreferences {
    private val logger = Logger.getLogger(MockSharedPreferences::class.java)
    private val editor = MockEditor()


    override fun getAll(): MutableMap<String, *> {
        TODO("Not yet implemented")
    }

    override fun getString(p0: String, p1: String): String {
        return if (editor.valueMap.containsKey(p0)){
            editor.valueMap[p0] as String
        }else{
            logger.warn("can't find $p0 in $name, just use $p1")
            p1
        }
    }

    override fun getStringSet(p0: String, p1: MutableSet<String>): MutableSet<String> {
        return if (editor.valueMap.containsKey(p0)){
            editor.valueMap[p0] as MutableSet<String>
        }else{
            logger.warn("can't find $p0 in $name, just use $p1")
            p1
        }
    }

    override fun getInt(p0: String, p1: Int): Int {
        return if (editor.valueMap.containsKey(p0)){
            editor.valueMap[p0] as Int
        }else{
            logger.warn("can't find $p0 in $name, just use $p1")
            p1
        }
    }

    override fun getLong(p0: String?, p1: Long): Long {
        return if (editor.valueMap.containsKey(p0)){
            editor.valueMap[p0] as Long
        }else{
            logger.warn("can't find $p0 in $name, just use $p1")
            p1
        }
    }

    override fun getFloat(p0: String?, p1: Float): Float {
        return if (editor.valueMap.containsKey(p0)){
            editor.valueMap[p0] as Float
        }else{
            logger.warn("can't find $p0 in $name, just use $p1")
            p1
        }
    }

    override fun getBoolean(p0: String?, p1: Boolean): Boolean {
        return if (editor.valueMap.containsKey(p0)){
            editor.valueMap[p0] as Boolean
        }else{
            logger.warn("can't find $p0 in $name, just use $p1")
            p1
        }
    }

    override fun contains(p0: String?): Boolean {
        return editor.valueMap.containsKey(p0)
    }

    override fun edit(): SharedPreferences.Editor = editor

    override fun registerOnSharedPreferenceChangeListener(p0: SharedPreferences.OnSharedPreferenceChangeListener?) {
        TODO("Not yet implemented")
    }

    override fun unregisterOnSharedPreferenceChangeListener(p0: SharedPreferences.OnSharedPreferenceChangeListener?) {
        TODO("Not yet implemented")
    }

}