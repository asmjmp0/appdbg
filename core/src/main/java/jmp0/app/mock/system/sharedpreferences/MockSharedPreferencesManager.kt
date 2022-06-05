package jmp0.app.mock.system.sharedpreferences

import jmp0.app.mock.annotations.ClassReplaceTo

@ClassReplaceTo("")
object MockSharedPreferencesManager {

    private val map = HashMap<String,MockSharedPreferences>()

    fun getSharedPreferences(name: String): MockSharedPreferences {
        return if (map.containsKey(name)){ map[name]!! }
        else{
            val mockSharedPreferences = MockSharedPreferences(name)
            map[name] = mockSharedPreferences
            mockSharedPreferences
        }
    }
}