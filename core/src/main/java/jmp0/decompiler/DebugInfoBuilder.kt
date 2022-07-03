package jmp0.decompiler

import org.objectweb.asm.*
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.InsnList
import java.io.File

class DebugInfoBuilder(private val classFile:File,
                       private val fullClassName:String,
                       private val mapping:MutableMap<String, MutableMap<String, MutableMap<Int, Int>>>,
                       private val decompileText:String) {

    val infoOpcode = Opcodes.ASM9

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
    private inner class TestMethodVisitor(private val mMapping:MutableMap<Int, Int>,private val methodVisitor:MethodVisitor):MethodVisitor(infoOpcode,methodVisitor){
        override fun visitInsn(opcode: Int) {
            super.visitInsn(opcode)
        }
    }

    private inner class DebugInfoClassVisitor(classWriter: ClassWriter):ClassVisitor(infoOpcode,classWriter){
        private fun getMethodMapping(name: String,desc: String):MutableMap<Int, Int>?{
            if (!mapping.containsKey(fullClassName)) return null
            val contentMapping = mapping[fullClassName] as MutableMap<String, MutableMap<Int, Int>>
            val signature = "$name $desc"
            if (!contentMapping.containsKey(signature)) return null
            return contentMapping[signature] as MutableMap<Int, Int>
        }

        override fun visitMethod(
            access: Int,
            name: String,
            desc: String,
            signature: String?,
            exceptions: Array<out String>?
        ): MethodVisitor {
            val mMapping = getMethodMapping(name, desc) ?: return super.visitMethod(access, name, desc, signature, exceptions)
            val methodVisitor = super.visitMethod(access, name, desc, signature, exceptions)
            val insnList = InsnList()
            insnList.accept(methodVisitor)
            println(name)
            insnList.iterator().forEach {
                println(it.opcode)
            }
            return methodVisitor

        }
    }
    fun build(){
        val classReader = ClassReader(classFile.readBytes())
        val classWriter = ClassWriter(classReader,ClassWriter.COMPUTE_FRAMES)
        val classNode = ClassNode(infoOpcode)
        classReader.accept(classNode,0)
        classNode.methods.forEach {
            println(it.name)
            it.instructions.iterator().forEach {
                it.opcode
                println(it.opcode)
            }
        }
    }
}