package jmp0.app
/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2023/4/3
 */
//if you want to use IAndroidInvokeFile function, generateJarFile of IApkConfig should be set to true
interface IAndroidInvokeFile {
    fun run(androidEnvironment: AndroidEnvironment)
}