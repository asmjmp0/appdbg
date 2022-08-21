package jmp0.apk.config

interface IApkConfig {
    /**
     * force to build class file from origin apk file
     */
    fun forceDecompile():Boolean

    /**
     * generate Jar file which make it possible to debug it.
     */
    fun generateJarFile():Boolean

    /**
     *  use fernflower decompiler,generate fake source code，make it possible
     *  to debug single step.
     */
    fun jarWithDebugInfo():Boolean
}