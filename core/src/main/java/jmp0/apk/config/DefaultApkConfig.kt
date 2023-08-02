package jmp0.apk.config

class DefaultApkConfig:IApkConfig {
    override fun forceDecompile(): Boolean = false
    override fun useClassFiles(): Boolean = true
    override fun generateJarFile(): Boolean = false

    override fun forceGenerateJarFile(): Boolean = false

    override fun jarWithDebugInfo(): Boolean = false

}