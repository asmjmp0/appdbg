package jmp0.app.mock.utils

object PropertiesReadUtils {
    private val propertiesFile = ClassLoader.getSystemClassLoader().getResource("mproperties")!!.openStream()

     val propertiesList = generatePropertiesList()

    private fun generatePropertiesList(): HashMap<String, String> {
        val lines = propertiesFile.bufferedReader().readLines()
        val ret = HashMap<String,String>()
        lines.forEach {
            val ls = it.split(':')
            if (ls.size == 1){
                ret[ls[0].replace("[","").replace("]","")]=""
            }else{
                val key = ls[0].replace("[","").replace("]","").trim()
                val value = ls[1].replace("[","").replace("]","").trim()
                ret[key] = value
            }
        }
        return ret
    }

    fun getProperty(id:String):String?{
        return propertiesList[id]
    }

}