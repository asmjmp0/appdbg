package jmp0.apk

import org.apache.log4j.Logger
import org.dom4j.io.SAXReader
import org.dom4j.tree.DefaultElement
import java.io.File
import java.util.jar.Manifest

class ManifestAnalyse(private val manifestFile: File) {
    private val logger = Logger.getLogger(manifestFile::class.java)
    lateinit var packaeName:String
    init {
        analyse()
    }

    private fun analyseApplication(node:DefaultElement){
    }

    private fun analyseManifest(element: DefaultElement){
        element.attributes().forEach { attribute->
            if(attribute.qName.name == "package"){
                packaeName = attribute.value
            }
        }
    }

    private fun analyse(){
        val f = SAXReader().read(manifestFile)
        f.content().forEach {
            if(it is DefaultElement){
                if(it.qName.name == "manifest"){
                    analyseManifest(it)
                }
            }

        }
    }
}