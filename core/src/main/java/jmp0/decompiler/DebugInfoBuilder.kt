package jmp0.decompiler

import org.objectweb.asm.*
import org.objectweb.asm.commons.CodeSizeEvaluator
import java.io.File

class DebugInfoBuilder(private val classFile:File,
                       private val fullClassName:String,
                       private val mapping:MutableMap<String, MutableMap<String, MutableMap<Int, Int>>>,
                       private val decompileText:String) {

    val infoOpcode = Opcodes.ASM5

    val classBeginLine by lazy {
        val className = fullClassName.run {
            if (contains("/")) {
                val sr = split('/')
                sr[sr.size-1]
            }else this
        }
        var num = 1
        decompileText.reader().readLines().forEach { line->
            if (line.contains("class $className"))
                return@lazy num
            num++
        }
        -1
    }
    fun Label.addSourceLineOffset(sourceLine:Short,byteOffset: Int){
        val clazz = Label::class.java

        val flagsField = clazz.getDeclaredField("flags")
        flagsField.isAccessible = true
        flagsField.setShort(this,5)

        val lineNumberField = clazz.getDeclaredField("lineNumber")
        lineNumberField.isAccessible = true
        lineNumberField.setShort(this,sourceLine)

        val bytecodeOffsetField = clazz.getDeclaredField("bytecodeOffset")
        bytecodeOffsetField.isAccessible = true
        bytecodeOffsetField.setInt(this,byteOffset)
    }

    @Deprecated("test use only")
    private inner class TestMethodVisitor(private val mMapping:MutableMap<Int, Int>,private val methodVisitor:MethodVisitor):CodeSizeEvaluator(infoOpcode,methodVisitor){

        override fun visitInsn(opcode: Int) {
            println(minSize)
            println(maxSize)
            println("==============")
            super.visitInsn(opcode)
            println(minSize)
            println(maxSize)
        }
        override fun visitEnd() {
            super.visitEnd()

        }
    }
    private fun getMethodMapping(name: String,desc: String):MutableMap<Int, Int>?{
        if (!mapping.containsKey(fullClassName)) return null
        val contentMapping = mapping[fullClassName] as MutableMap<String, MutableMap<Int, Int>>
        val signature = "$name $desc"
        if (!contentMapping.containsKey(signature)) return null
        return contentMapping[signature] as MutableMap<Int, Int>
    }

    private inner class DebugInfoClassVisitor(classWriter: ClassWriter):ClassVisitor(infoOpcode,classWriter){

        override fun visitMethod(
            access: Int,
            name: String,
            desc: String,
            signature: String?,
            exceptions: Array<out String>?
        ): MethodVisitor {
            val mMapping =
                getMethodMapping(name, desc) ?: return super.visitMethod(access, name, desc, signature, exceptions)
            val methodVisitor = super.visitMethod(access, name, desc, signature, exceptions)
            println(name)
            return TestMethodVisitor(mMapping, methodVisitor)

        }
    }
    fun build(){
        val classReader = ClassReader(classFile.readBytes())
        val classWriter = ClassWriter(classReader,ClassWriter.COMPUTE_FRAMES or ClassWriter.COMPUTE_MAXS)
        classReader.accept(DebugInfoClassVisitor(classWriter),0)
        val data = classWriter.toByteArray()
        File("temp/TestAES.class").writeBytes(data)

    }
}