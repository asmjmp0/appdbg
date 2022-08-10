package jmp0.decompiler

import org.objectweb.asm.*
import org.objectweb.asm.commons.CodeSizeEvaluator
import java.io.File

class DebugInfoBuilder(private val classFile:File,
                       private val fullClassName:String,
                       private val mapping:MutableMap<String, MutableMap<String, MutableMap<Int, Int>>>,
                       private val decompileText:String) {

    val infoOpcode = Opcodes.ASM5

    val classBeginLine = mapping[fullClassName]!!["headerCounts"]!!.keys.iterator().next()

    val apkPathDir by lazy {
        classFile.canonicalPath.run {
            val idx = indexOf("/classes/")
            File(substring(0,idx),"decompile_source")
        }
    }

    private inner class DebugInfoMethodVisitor(private val mMapping:MutableMap<Int, Int>, methodVisitor:MethodVisitor):CodeSizeEvaluator(infoOpcode,methodVisitor){
        private fun checkAndInsertDebugLabel(type: String){
            if (mMapping.containsKey(minSize)) with(Label()) {
                val sourceLine = classBeginLine+ mMapping[minSize]!!
                visitLabel(this)
                visitLineNumber(sourceLine,this)
            }
        }
        override fun visitInsn(opcode: Int) {
            checkAndInsertDebugLabel("visitInsn")
            super.visitInsn(opcode)
        }

        override fun visitIntInsn(opcode: Int, operand: Int) {
            checkAndInsertDebugLabel("visitIntInsn")
            super.visitIntInsn(opcode, operand)
        }

        override fun visitVarInsn(opcode: Int, `var`: Int) {
            checkAndInsertDebugLabel("visitVarInsn")
            super.visitVarInsn(opcode, `var`)
        }

        override fun visitTypeInsn(opcode: Int, type: String?) {
            checkAndInsertDebugLabel("visitTypeInsn")
            super.visitTypeInsn(opcode, type)
        }

        override fun visitFieldInsn(opcode: Int, owner: String?, name: String?, desc: String?) {
            checkAndInsertDebugLabel("visitFieldInsn")
            super.visitFieldInsn(opcode, owner, name, desc)
        }


        override fun visitMethodInsn(opcode: Int, owner: String?, name: String?, desc: String?, itf: Boolean) {
            checkAndInsertDebugLabel("visitMethodInsn")
            super.visitMethodInsn(opcode, owner, name, desc, itf)
        }

        override fun visitMethodInsn(opcode: Int, owner: String?, name: String?, desc: String?) {
            checkAndInsertDebugLabel("visitMethodInsn")
            super.visitMethodInsn(opcode, owner, name, desc)
        }

        override fun visitInvokeDynamicInsn(name: String?, desc: String?, bsm: Handle?, vararg bsmArgs: Any?) {
            checkAndInsertDebugLabel("visitInvokeDynamicInsn")
            super.visitInvokeDynamicInsn(name, desc, bsm, *bsmArgs)
        }

        override fun visitJumpInsn(opcode: Int, label: Label?) {
            checkAndInsertDebugLabel("visitJumpInsn")
            super.visitJumpInsn(opcode, label)
        }

        override fun visitLdcInsn(cst: Any?) {
            checkAndInsertDebugLabel("visitLdcInsn")
            super.visitLdcInsn(cst)
        }

        override fun visitIincInsn(`var`: Int, increment: Int) {
            checkAndInsertDebugLabel("visitIincInsn")
            super.visitIincInsn(`var`, increment)
        }

        override fun visitTableSwitchInsn(min: Int, max: Int, dflt: Label?, vararg labels: Label?) {
            checkAndInsertDebugLabel("visitTableSwitchInsn")
            super.visitTableSwitchInsn(min, max, dflt, *labels)
        }

        override fun visitLookupSwitchInsn(dflt: Label?, keys: IntArray?, labels: Array<out Label>?) {
            checkAndInsertDebugLabel("visitLookupSwitchInsn")
            super.visitLookupSwitchInsn(dflt, keys, labels)
        }

        override fun visitMultiANewArrayInsn(desc: String?, dims: Int) {
            checkAndInsertDebugLabel("visitMultiANewArrayInsn")
            super.visitMultiANewArrayInsn(desc, dims)
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
            return DebugInfoMethodVisitor(mMapping, methodVisitor)

        }
    }


    fun build(){
        val classReader = ClassReader(classFile.readBytes())
        val classWriter = ClassWriter(classReader,ClassWriter.COMPUTE_MAXS)
        classReader.accept(DebugInfoClassVisitor(classWriter),0)
        val data = classWriter.toByteArray()
        classFile.writeBytes(data)
        val idx = fullClassName.lastIndexOf('/')
        val packageName = fullClassName.substring(0, idx)
        val sourceName = fullClassName.substring(idx) + ".java"
        File(File(apkPathDir,packageName).apply { mkdirs() },sourceName).writeBytes(decompileText.toByteArray())
    }
}