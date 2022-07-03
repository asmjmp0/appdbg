package jmp0.decompiler

import org.apache.log4j.Logger
import org.jetbrains.java.decompiler.main.decompiler.BaseDecompiler
import org.jetbrains.java.decompiler.main.extern.IBytecodeProvider
import org.jetbrains.java.decompiler.main.extern.IFernflowerLogger
import org.jetbrains.java.decompiler.main.extern.IFernflowerPreferences
import org.jetbrains.java.decompiler.main.extern.IResultSaver
import org.jetbrains.java.decompiler.util.TextUtil
import java.io.BufferedInputStream
import java.io.File
import java.io.InputStream
import java.util.jar.Manifest

class AppdbgDecompiler(private val classFile:File) : BaseDecompiler(IBytecodeProvider { externalPath, internalPath -> classFile.readBytes() }
    , AppdbgResultSaver(classFile), HashMap(IFernflowerPreferences.DEFAULTS).apply {
        put(IFernflowerPreferences.BYTECODE_SOURCE_MAPPING,"1")
        put(IFernflowerPreferences.DECOMPILE_GENERIC_SIGNATURES,"1") },AppdbgDecompilerLogger){

    private val  logger = Logger.getLogger(AppdbgDecompiler::class.java)

    fun decompile(){
        addSource(classFile)
        decompileContext()
    }

    private object AppdbgDecompilerLogger:IFernflowerLogger(){
        private val  logger = Logger.getLogger(AppdbgDecompiler::class.java)
        //just ignore all warning
        override fun writeMessage(message: String?, severity: Severity?) {}
        override fun writeMessage(message: String?, severity: Severity?, t: Throwable) {}
    }

    private class AppdbgResultSaver(private val classFile: File):IResultSaver{
        override fun saveFolder(path: String?) {}

        override fun copyFile(source: String?, path: String?, entryName: String?) {}

        override fun saveClassFile(
            path: String?,
            qualifiedName: String,
            entryName: String,
            content: String?,
            mapping: MutableMap<String, MutableMap<String, MutableMap<Int, Int>>>?
        ) {
            if ((content == null) or (content == "") ) return
            if ((mapping!=null) and (mapping!!.isNotEmpty()))
                DebugInfoBuilder(classFile,qualifiedName,mapping,content!!).build()
        }

        override fun createArchive(path: String?, archiveName: String?, manifest: Manifest?) {}

        override fun saveDirEntry(path: String?, archiveName: String?, entryName: String?) {}

        override fun copyEntry(source: String?, path: String?, archiveName: String?, entry: String?) {}

        override fun saveClassEntry(path: String?, archiveName: String?, qualifiedName: String?, entryName: String?, content: String?) {}

        override fun closeArchive(path: String?, archiveName: String?) {}

    }
}