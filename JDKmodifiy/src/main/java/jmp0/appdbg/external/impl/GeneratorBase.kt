package jmp0.appdbg.external.impl

import jmp0.appdbg.external.Common
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream

abstract class GeneratorBase {
    protected val dir = "temp${File.separator}JDK${File.separator}${Common.systemNowPackage}"
}