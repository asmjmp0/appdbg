/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2023/4/10
 */
class TestClassLoader:ClassLoader(){
    fun testDefineClass(byteArray: ByteArray): Class<*> =
        defineClass(null,byteArray,0,byteArray.size)
}