package jmp0.util

import net.lingala.zip4j.ZipFile
import net.lingala.zip4j.model.ExcludeFileFilter
import net.lingala.zip4j.model.ZipParameters
import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import kotlin.io.path.Path
import kotlin.io.path.name

object ZipUtility {
    private val BUFFER_SIZE = 4096
    /**
     * Extracts a zip file specified by the zipFilePath to a directory specified by
     * destDirectory (will be created if does not exists)
     * @param zipFilePath
     * @param destDirectory
     * @throws IOException
     */
    fun unzip(zipFilePath: String?, destDirectory: String) {
        unzip(FileInputStream(zipFilePath),destDirectory)
    }

    fun unzip(inputStream: InputStream,destDirectory: String){
        val zipIn = ZipInputStream(inputStream)
        val destDir = File(destDirectory)
        if (!destDir.exists()) {
            destDir.mkdir()
        }
        var entry: ZipEntry? = zipIn.getNextEntry()
        // iterates over entries in the zip file
        while (entry != null) {
            val filePath = destDirectory + File.separator + entry.name
            if (!entry.isDirectory) {
                // if the entry is a file, extracts it
                extractFile(zipIn, filePath)
            } else {
                // if the entry is a directory, make the directory
                val dir = File(filePath)
                dir.mkdir()
            }
            zipIn.closeEntry()
            entry = zipIn.nextEntry
        }
        zipIn.close()
    }

    /**
     * Extracts a zip entry (file entry)
     * @param zipIn
     * @param filePath
     * @throws IOException
     */
    private fun extractFile(zipIn: ZipInputStream, filePath: String) {
        val bos = BufferedOutputStream(FileOutputStream(filePath))
        val bytesIn = ByteArray(BUFFER_SIZE)
        var read = 0
        while (zipIn.read(bytesIn).also { read = it } != -1) {
            bos.write(bytesIn, 0, read)
        }
        bos.close()
    }

     fun zip(sourceDirPath: String, zipFilePath: String,mExcludeFileFilter: ExcludeFileFilter?) {
         ZipFile(zipFilePath).addFolder(File(sourceDirPath), ZipParameters().apply {
             isIncludeRootFolder = false
             mExcludeFileFilter?.let { excludeFileFilter = it }
         })
//        val p: Path = Files.createFile(Paths.get(zipFilePath))
//        ZipOutputStream(Files.newOutputStream(p)).use { zs ->
//            val pp: Path = Paths.get(sourceDirPath)
//            Files.walk(pp)
//                .filter { path -> !Files.isDirectory(path) }
//                .forEach { path ->
//                    if (path.startsWith("$pp${File.separator}kotlin"))
//                        //exclude kotlin package
//                        return@forEach
//                    val zipEntry = ZipEntry(pp.relativize(path).toString())
//                    zs.putNextEntry(zipEntry)
//                    zs.write(Files.readAllBytes(path))
//                    zs.closeEntry()
//                }
//        }
    }
}