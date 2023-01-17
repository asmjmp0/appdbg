package jmp0.app.mock.system.sharedpreferences

import android.content.SharedPreferences
import jmp0.app.DbgContext
import jmp0.app.classloader.XAndroidClassLoader
import jmp0.app.conversation.AppdbgConversationSchemaEnum
import jmp0.app.conversation.impl.sp.SharedPreferencesConversation
import jmp0.app.conversation.impl.sp.SharedPreferencesData
import jmp0.app.mock.annotations.ClassReplaceTo
import org.apache.log4j.Logger

@ClassReplaceTo("")
class MockSharedPreferences(private val name: String) : SharedPreferences {
    private val ae =
        DbgContext.getAndroidEnvironmentWithClassLoader(this::class.java.classLoader as XAndroidClassLoader)
    private val logger = Logger.getLogger(MockSharedPreferences::class.java)
    private val editor = MockEditor()

    private fun conversationCall(name: String, retType:Class<*>,vararg value: Any?): Any? {
        ae.getConversationHandler(AppdbgConversationSchemaEnum.SHARED_PREFERENCES)
            ?.appdbgConversationHandle(ae, SharedPreferencesConversation(SharedPreferencesData(name, retType, value)))
            ?.let {
                return if (it.implemented) it.result else null
            }
        return null
    }


    override fun getAll(): MutableMap<String, *> {
        TODO("Not yet implemented")
    }

    override fun getString(p0: String, p1: String): String {
        conversationCall("getString",String::class.java,p0,p1)?.also { return it as String }
        return if (editor.valueMap.containsKey(p0)) {
            editor.valueMap[p0] as String
        } else {
            logger.warn("can't find $p0 in $name, just use $p1")
            p1
        }
    }

    override fun getStringSet(p0: String, p1: MutableSet<String>): MutableSet<String> {
        conversationCall("getStringSet",MutableSet::class.java,p0,p1)?.also { return it as MutableSet<String> }
        return if (editor.valueMap.containsKey(p0)) {
            editor.valueMap[p0] as MutableSet<String>
        } else {
            logger.warn("can't find $p0 in $name, just use $p1")
            p1
        }
    }

    override fun getInt(p0: String, p1: Int): Int {
        conversationCall("getInt",Int::class.java,p0,p1)?.also { return it as Int }
        return if (editor.valueMap.containsKey(p0)) {
            editor.valueMap[p0] as Int
        } else {
            logger.warn("can't find $p0 in $name, just use $p1")
            p1
        }
    }

    override fun getLong(p0: String?, p1: Long): Long {
        conversationCall("getLong",Long::class.java,p0,p1)?.also { return it as Long }
        return if (editor.valueMap.containsKey(p0)) {
            editor.valueMap[p0] as Long
        } else {
            logger.warn("can't find $p0 in $name, just use $p1")
            p1
        }
    }

    override fun getFloat(p0: String?, p1: Float): Float {
        conversationCall("getFloat",Float::class.java,p0,p1)?.also { return it as Float }
        return if (editor.valueMap.containsKey(p0)) {
            editor.valueMap[p0] as Float
        } else {
            logger.warn("can't find $p0 in $name, just use $p1")
            p1
        }
    }

    override fun getBoolean(p0: String?, p1: Boolean): Boolean {
        conversationCall("getBoolean",Boolean::class.java,p0,p1)?.also { return it as Boolean }
        return if (editor.valueMap.containsKey(p0)) {
            editor.valueMap[p0] as Boolean
        } else {
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