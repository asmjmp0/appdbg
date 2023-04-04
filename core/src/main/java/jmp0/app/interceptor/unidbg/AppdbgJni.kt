package jmp0.app.interceptor.unidbg

import com.github.unidbg.linux.android.dvm.*
import jmp0.app.AndroidEnvironment
import jmp0.app.interceptor.unidbg.UnidbgWrapperUtils.repair
import jmp0.util.ReflectUtilsBase
import jmp0.util.SystemReflectUtils
import jmp0.util.SystemReflectUtils.getEx
import jmp0.util.SystemReflectUtils.invokeEx
import jmp0.util.SystemReflectUtils.setEx
import org.apache.log4j.Logger

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/4/22
 */
class AppdbgJni(private val androidEnvironment: AndroidEnvironment):AbstractJni() {
    private val logger = Logger.getLogger(AppdbgJni::class.java)
    override fun callStaticObjectMethodV(
        vm: BaseVM,
        dvmClass: DvmClass,
        dvmMethod: DvmMethod,
        vaList: VaList
    ): DvmObject<*> {
        logger.info("callStaticObjectMethodV ${dvmClass.className}.${dvmMethod.methodName}${dvmMethod.args} pass to appdbg!!")
        val clazzName = dvmClass.className.replace('/','.')
        val methodName = dvmMethod.methodName
        val signatureInfo = SystemReflectUtils.getSignatureInfo(clazzName+'.'+methodName+dvmMethod.args,androidEnvironment.id)
        val clazz = androidEnvironment.findClass(clazzName)
        val method = clazz.getDeclaredMethod(methodName,*signatureInfo.paramTypes)
        val params = UnidbgWrapperUtils.toOriginalObject(vaList,signatureInfo,androidEnvironment)
        val res = method.invokeEx(null,*params)
        val name = method.returnType.name.replace('.','/')
        return UnidbgWrapperUtils.toUnidbgObject(vm,res,name)
    }

    override fun newObjectV(
        vm: BaseVM,
        dvmClass: DvmClass,
        dvmMethod: DvmMethod,
        vaList: VaList
    ): DvmObject<*> {
        logger.info("newObjectV ${dvmClass.className}.${dvmMethod.methodName}${dvmMethod.args} pass to appdbg[newObject]!!")
        return this.newObject(vm,dvmClass,dvmMethod,vaList)
    }

    override fun newObject(vm: BaseVM, dvmClass: DvmClass, dvmMethod: DvmMethod, varArg: VarArg): DvmObject<*> {
        logger.info("newObject ${dvmClass.className}.${dvmMethod.methodName}${dvmMethod.args} pass to appdbg!!")
        val clazzName = dvmClass.className.replace('/', '.')
        val methodName = dvmMethod.methodName
        val signatureInfo = SystemReflectUtils.getSignatureInfo(
            clazzName + '.' + methodName + dvmMethod.args,
            androidEnvironment.id
        )
        val clazz = androidEnvironment.findClass(clazzName)
        val method = clazz.getConstructor(*signatureInfo.paramTypes)
        val params = UnidbgWrapperUtils.toOriginalObject(varArg, signatureInfo,androidEnvironment)
        return UnidbgWrapperUtils.toUnidbgObject(vm, method.newInstance(*params))
    }

    override fun callVoidMethod(vm: BaseVM, dvmObject: DvmObject<*>, dvmMethod: DvmMethod, varArg: VarArg) {
        logger.info("callVoidMethod ${dvmObject.objectType.className}.${dvmMethod.methodName}${dvmMethod.args} pass to appdbg!!")
        val clazzName = dvmObject.objectType.className.replace('/','.')
        val methodName = dvmMethod.methodName
        val signatureInfo = SystemReflectUtils.getSignatureInfo(
            clazzName + '.' + methodName + dvmMethod.args,
            androidEnvironment.id
        )
        val clazz = androidEnvironment.findClass(clazzName)
        val method = clazz.getDeclaredMethod(methodName,*signatureInfo.paramTypes)
        val params = UnidbgWrapperUtils.toOriginalObject(varArg,signatureInfo,androidEnvironment)
        dvmObject.repair(androidEnvironment)
        method.invokeEx(dvmObject.value,*params)
    }

    override fun callVoidMethodV(
        vm: BaseVM,
        dvmObject: DvmObject<*>,
        dvmMethod: DvmMethod,
        vaList: VaList
    ) {
        logger.info("callVoidMethodV ${dvmObject.objectType.className}.${dvmMethod.methodName}${dvmMethod.args} pass to appdbg[callVoidMethod]!!")
        this.callVoidMethod(vm,dvmObject,dvmMethod,vaList)
    }

    override fun callIntMethod(vm: BaseVM, dvmObject: DvmObject<*>, dvmMethod: DvmMethod, varArg: VarArg): Int {
        logger.info("callIntMethod ${dvmObject.objectType.className}.${dvmMethod.methodName}${dvmMethod.args} pass to appdbg[callObjectMethod]!!")
        return this.callObjectMethod(vm, dvmObject, dvmMethod, varArg).value as Int
    }

    override fun callBooleanMethod(
        vm: BaseVM,
        dvmObject: DvmObject<*>,
        dvmMethod: DvmMethod,
        varArg: VarArg
    ): Boolean {
        logger.info("callBooleanMethod ${dvmObject.objectType.className}.${dvmMethod.methodName}${dvmMethod.args} pass to appdbg[callObjectMethod]!!")
        return this.callObjectMethod(vm, dvmObject, dvmMethod, varArg).value as Boolean
    }

    override fun callObjectMethod(
        vm: BaseVM,
        dvmObject: DvmObject<*>,
        dvmMethod: DvmMethod,
        varArg: VarArg
    ): DvmObject<*> {
        logger.info("callObjectMethod ${dvmObject.objectType.className}.${dvmMethod.methodName}${dvmMethod.args} pass to appdbg!!")
        val clazzName = dvmObject.objectType.className.replace('/','.')
        val methodName = dvmMethod.methodName
        val signatureInfo = SystemReflectUtils.getSignatureInfo(
            clazzName + '.' + methodName + dvmMethod.args,
            androidEnvironment.id
        )
        val clazz = androidEnvironment.findClass(clazzName)
        val method = clazz.getDeclaredMethod(methodName,*signatureInfo.paramTypes)
        val params = UnidbgWrapperUtils.toOriginalObject(varArg,signatureInfo,androidEnvironment)
        dvmObject.repair(androidEnvironment)
        val res = method.invokeEx(dvmObject.value,*params)
        val name = method.returnType.name.replace('.','/')
        return UnidbgWrapperUtils.toUnidbgObject(vm, res, name)
    }

    override fun callObjectMethodV(
        vm: BaseVM,
        dvmObject: DvmObject<*>,
        dvmMethod: DvmMethod,
        vaList: VaList
    ): DvmObject<*> {
        logger.info("callObjectMethodV ${dvmObject.objectType.className}.${dvmMethod.methodName}${dvmMethod.args} pass to appdbg[callObjectMethod]!!")
        return this.callObjectMethod(vm,dvmObject,dvmMethod,vaList)
    }

    override fun setObjectField(vm: BaseVM?, dvmObject: DvmObject<*>, dvmField: DvmField, value: DvmObject<*>) {
        logger.info("call method ${dvmObject.objectType.className} setObjectField[${dvmField.fieldName}] pass to appdbg!!")
        val ins = dvmObject.repair(androidEnvironment).value
        ins!!.javaClass.getDeclaredField(dvmField.fieldName).setEx(ins,value.value)
    }

    override fun getObjectField(vm: BaseVM, dvmObject: DvmObject<*>, dvmField: DvmField): DvmObject<*> {
        logger.info("call method ${dvmObject.objectType.className} getObjectField[${dvmField.fieldName}] pass to appdbg!!")
        val ins = dvmObject.repair(androidEnvironment).value
        val res = ins!!.javaClass.getDeclaredField(dvmField.fieldName).getEx(ins)
        return UnidbgWrapperUtils.toUnidbgObject(vm,res)
    }

    override fun setIntField(vm: BaseVM?, dvmObject: DvmObject<*>, dvmField: DvmField, value: Int) {
        logger.info("call method ${dvmObject.objectType.className} setIntField[${dvmField.fieldName}] pass to appdbg!!")
        val ins = dvmObject.repair(androidEnvironment).value
        ins!!.javaClass.getDeclaredField(dvmField.fieldName).setEx(ins,value)
    }

    override fun getIntField(vm: BaseVM, dvmObject: DvmObject<*>, dvmField: DvmField): Int {
        logger.info("call method ${dvmObject.objectType.className} getIntField[${dvmField.fieldName}] pass to appdbg!!")
        val ins = dvmObject.repair(androidEnvironment).value
        return ins!!.javaClass.getDeclaredField(dvmField.fieldName).getEx(ins) as Int
    }

    override fun setBooleanField(vm: BaseVM, dvmObject: DvmObject<*>, dvmField: DvmField, value: Boolean) {
        logger.info("call method ${dvmObject.objectType.className} setBooleanField[${dvmField.fieldName}] pass to appdbg!!")
        val ins = dvmObject.repair(androidEnvironment).value
        ins!!.javaClass.getDeclaredField(dvmField.fieldName).setEx(ins,value)
    }

    override fun getBooleanField(vm: BaseVM, dvmObject: DvmObject<*>, dvmField: DvmField): Boolean {
        logger.info("call method ${dvmObject.objectType.className} getBooleanField[${dvmField.fieldName}] pass to appdbg!!")
        val ins = dvmObject.repair(androidEnvironment).value
        return ins!!.javaClass.getDeclaredField(dvmField.fieldName).getEx(ins) as Boolean
    }

    override fun setFloatField(vm: BaseVM, dvmObject: DvmObject<*>, dvmField: DvmField, value: Float) {
        logger.info("call method ${dvmObject.objectType.className} setFloatField[${dvmField.fieldName}] pass to appdbg!!")
        val ins = dvmObject.repair(androidEnvironment).value
        ins!!.javaClass.getDeclaredField(dvmField.fieldName).setEx(ins,value)
    }

    override fun getFloatField(vm: BaseVM, dvmObject: DvmObject<*>, dvmField: DvmField): Float {
        logger.info("call method ${dvmObject.objectType.className} getFloatField[${dvmField.fieldName}] pass to appdbg!!")
        val ins = dvmObject.repair(androidEnvironment).value
        return ins!!.javaClass.getDeclaredField(dvmField.fieldName).getEx(ins) as Float
    }

    override fun setLongField(vm: BaseVM, dvmObject: DvmObject<*>, dvmField: DvmField, value: Long) {
        logger.info("call method ${dvmObject.objectType.className} setLongField[${dvmField.fieldName}] pass to appdbg!!")
        val ins = dvmObject.repair(androidEnvironment).value
        ins!!.javaClass.getDeclaredField(dvmField.fieldName).setEx(ins,value)
    }

    override fun getLongField(vm: BaseVM, dvmObject: DvmObject<*>, dvmField: DvmField): Long {
        logger.info("call method ${dvmObject.objectType.className} getLongField[${dvmField.fieldName}] pass to appdbg!!")
        val ins = dvmObject.repair(androidEnvironment).value
        return ins!!.javaClass.getDeclaredField(dvmField.fieldName).getEx(ins) as Long
    }

    override fun setDoubleField(vm: BaseVM, dvmObject: DvmObject<*>, dvmField: DvmField, value: Double) {
        logger.info("call method ${dvmObject.objectType.className} setDoubleField[${dvmField.fieldName}] pass to appdbg!!")
        val ins = dvmObject.repair(androidEnvironment).value
        ins!!.javaClass.getDeclaredField(dvmField.fieldName).setEx(ins,value)
    }

    // FIXME: 2022/4/22 getDoubleField not implement

    override fun setStaticObjectField(vm: BaseVM?, dvmClass: DvmClass, dvmField: DvmField, value: DvmObject<*>) {
        logger.info("call method ${dvmClass.className} setStaticObjectField[${dvmField.fieldName}] pass to appdbg!!")
        val clazz = androidEnvironment.findClass(dvmClass.className.replace('/','.'))
        clazz.getDeclaredField(dvmField.fieldName).setEx(null,value.repair(androidEnvironment).value)
    }

    override fun getStaticObjectField(vm: BaseVM, dvmClass: DvmClass, dvmField: DvmField): DvmObject<*> {
        logger.info("call method ${dvmClass.className} getStaticObjectField[${dvmField.fieldName}] pass to appdbg!!")
        val clazz = androidEnvironment.findClass(dvmClass.className.replace('/','.'))
        return UnidbgWrapperUtils.toUnidbgObject(vm,clazz.getDeclaredField(dvmField.fieldName).getEx(null))
    }

    override fun setStaticBooleanField(vm: BaseVM, dvmClass: DvmClass, dvmField: DvmField, value: Boolean) {
        logger.info("call method ${dvmClass.className} setStaticBooleanField[${dvmField.fieldName}] pass to appdbg!!")
        val clazz = androidEnvironment.findClass(dvmClass.className.replace('/','.'))
        clazz.getDeclaredField(dvmField.fieldName).setEx(null,value)
    }

    override fun getStaticBooleanField(vm: BaseVM, dvmClass: DvmClass, dvmField: DvmField): Boolean {
        logger.info("call method ${dvmClass.className} getStaticBooleanField[${dvmField.fieldName}] pass to appdbg!!")
        val clazz = androidEnvironment.findClass(dvmClass.className.replace('/','.'))
        return clazz.getDeclaredField(dvmField.fieldName).getEx(null) as Boolean
    }

    override fun setStaticDoubleField(vm: BaseVM, dvmClass: DvmClass, dvmField: DvmField, value: Double) {
        logger.info("call method ${dvmClass.className} setStaticDoubleField[${dvmField.fieldName}] pass to appdbg!!")
        val clazz = androidEnvironment.findClass(dvmClass.className.replace('/','.'))
        clazz.getDeclaredField(dvmField.fieldName).setEx(null,value)
    }

    // FIXME: 2022/4/22 setStaticByteField not implement

    override fun getStaticByteField(vm: BaseVM, dvmClass: DvmClass, dvmField: DvmField): Byte {
        logger.info("call method ${dvmClass.className} getStaticByteField[${dvmField.fieldName}] pass to appdbg!!")
        val clazz = androidEnvironment.findClass(dvmClass.className.replace('/','.'))
        return clazz.getDeclaredField(dvmField.fieldName).getEx(null) as Byte
    }

    override fun setStaticIntField(vm: BaseVM, dvmClass: DvmClass, dvmField: DvmField, value: Int) {
        logger.info("call method ${dvmClass.className} setStaticIntField[${dvmField.fieldName}] pass to appdbg!!")
        val clazz = androidEnvironment.findClass(dvmClass.className.replace('/','.'))
        clazz.getDeclaredField(dvmField.fieldName).setEx(null,value)
    }

    override fun getStaticIntField(vm: BaseVM, dvmClass: DvmClass, dvmField: DvmField): Int {
        logger.info("call method ${dvmClass.className} getStaticIntField[${dvmField.fieldName}] pass to appdbg!!")
        val clazz = androidEnvironment.findClass(dvmClass.className.replace('/','.'))
        return clazz.getDeclaredField(dvmField.fieldName).getEx(null) as Int
    }

    override fun setStaticFloatField(vm: BaseVM, dvmClass: DvmClass, dvmField: DvmField, value: Float) {
        logger.info("call method ${dvmClass.className} setStaticFloatField[${dvmField.fieldName}] pass to appdbg!!")
        val clazz = androidEnvironment.findClass(dvmClass.className.replace('/','.'))
        clazz.getDeclaredField(dvmField.fieldName).setEx(null,value)
    }

    // FIXME: 2022/4/22 getStaticFloatField not implement

    override fun setStaticLongField(vm: BaseVM, dvmClass: DvmClass, dvmField: DvmField, value: Long) {
        logger.info("call method ${dvmClass.className} setStaticLongField[${dvmField.fieldName}] pass to appdbg!!")
        val clazz = androidEnvironment.findClass(dvmClass.className.replace('/','.'))
        clazz.getDeclaredField(dvmField.fieldName).setEx(null,value)
    }

    override fun getStaticLongField(vm: BaseVM, dvmClass: DvmClass, dvmField: DvmField): Long {
        logger.info("call method ${dvmClass.className} getStaticIntField[${dvmField.fieldName}] pass to appdbg!!")
        val clazz = androidEnvironment.findClass(dvmClass.className.replace('/','.'))
        return clazz.getDeclaredField(dvmField.fieldName).getEx(null) as Long
    }


    // TODO: 2022/4/22 implement other get set method
}