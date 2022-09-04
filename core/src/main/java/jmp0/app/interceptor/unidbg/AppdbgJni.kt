package jmp0.app.interceptor.unidbg

import com.github.unidbg.linux.android.dvm.*
import jmp0.app.AndroidEnvironment
import jmp0.util.ReflectUtilsBase
import jmp0.util.SystemReflectUtils
import org.apache.log4j.Logger

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/4/22
 */
class AppdbgJni(private val vm: VM,private val androidEnvironment: AndroidEnvironment):AbstractJni() {
    private val logger = Logger.getLogger(AppdbgJni::class.java)
    override fun callStaticObjectMethodV(
        vm: BaseVM,
        dvmClass: DvmClass,
        dvmMethod: DvmMethod,
        vaList: VaList
    ): DvmObject<*> {
        logger.info("from jni call ${dvmClass.className}.${dvmMethod.methodName}${dvmMethod.args} pass to appdbg!!")
        val clazzName = dvmClass.className.replace('/','.')
        val methodName = dvmMethod.methodName
        val signatureInfo = SystemReflectUtils.getSignatureInfo(clazzName+'.'+methodName+dvmMethod.args,androidEnvironment.id)
        val clazz = androidEnvironment.findClass(clazzName)
        val method = clazz.getMethod(methodName,*signatureInfo.paramTypes)
        val params = UnidbgWrapperUtils.toOriginalObject(vaList,signatureInfo)
        val res = method.invoke(null,*params)
        return UnidbgWrapperUtils.toUnidbgObject(vm,res)
    }

    override fun newObjectV(
        vm: BaseVM,
        dvmClass: DvmClass,
        dvmMethod: DvmMethod,
        vaList: VaList
    ): DvmObject<*> {
        logger.info("new object ${dvmClass.className}.${dvmMethod.methodName}${dvmMethod.args} pass to appdbg!!")
        val clazzName = dvmClass.className.replace('/', '.')
        val methodName = dvmMethod.methodName
        val signatureInfo = SystemReflectUtils.getSignatureInfo(
            clazzName + '.' + methodName + dvmMethod.args,
            androidEnvironment.id
        )
        val clazz = androidEnvironment.findClass(clazzName)
        val method = clazz.getConstructor(*signatureInfo.paramTypes)
        val params = UnidbgWrapperUtils.toOriginalObject(vaList, signatureInfo)
        return UnidbgWrapperUtils.toUnidbgObject(vm, method.newInstance(*params))
    }

    override fun callVoidMethodV(
        vm: BaseVM,
        dvmObject: DvmObject<*>,
        dvmMethod: DvmMethod,
        vaList: VaList
    ) {
        logger.info("call method ${dvmObject.objectType.className}.${dvmMethod.methodName}${dvmMethod.args} pass to appdbg!!")
        val clazzName = dvmObject.objectType.className.replace('/','.')
        val methodName = dvmMethod.methodName
        val signatureInfo = SystemReflectUtils.getSignatureInfo(
            clazzName + '.' + methodName + dvmMethod.args,
            androidEnvironment.id
        )
        val clazz = androidEnvironment.findClass(clazzName)
        val method = clazz.getMethod(methodName,*signatureInfo.paramTypes)
        val params = UnidbgWrapperUtils.toOriginalObject(vaList,signatureInfo)
        method.invoke(dvmObject.value,*params)
    }

    override fun callObjectMethodV(
        vm: BaseVM,
        dvmObject: DvmObject<*>,
        dvmMethod: DvmMethod,
        vaList: VaList
    ): DvmObject<*> {
        logger.info("call method ${dvmObject.objectType.className}.${dvmMethod.methodName}${dvmMethod.args} pass to appdbg!!")
        val clazzName = dvmObject.objectType.className.replace('/','.')
        val methodName = dvmMethod.methodName
        val signatureInfo = SystemReflectUtils.getSignatureInfo(
            clazzName + '.' + methodName + dvmMethod.args,
            androidEnvironment.id
        )
        val clazz = androidEnvironment.findClass(clazzName)
        val method = clazz.getMethod(methodName,*signatureInfo.paramTypes)
        val params = UnidbgWrapperUtils.toOriginalObject(vaList,signatureInfo)
        val res = method.invoke(dvmObject.value,*params)
        return UnidbgWrapperUtils.toUnidbgObject(vm, res)
    }

    override fun setObjectField(vm: BaseVM?, dvmObject: DvmObject<*>, dvmField: DvmField, value: DvmObject<*>) {
        logger.info("call method ${dvmObject.objectType.className} setObjectField[${dvmField.fieldName}] pass to appdbg!!")
        val ins = dvmObject.value
        ins!!.javaClass.getField(dvmField.fieldName).set(ins,value.value)
    }

    override fun getObjectField(vm: BaseVM, dvmObject: DvmObject<*>, dvmField: DvmField): DvmObject<*> {
        logger.info("call method ${dvmObject.objectType.className} getObjectField[${dvmField.fieldName}] pass to appdbg!!")
        val ins = dvmObject.value
        val res = ins!!.javaClass.getField(dvmField.fieldName).get(ins)
        return UnidbgWrapperUtils.toUnidbgObject(vm,res)
    }

    override fun setIntField(vm: BaseVM?, dvmObject: DvmObject<*>, dvmField: DvmField, value: Int) {
        logger.info("call method ${dvmObject.objectType.className} setIntField[${dvmField.fieldName}] pass to appdbg!!")
        val ins = dvmObject.value
        ins!!.javaClass.getField(dvmField.fieldName).set(ins,value)
    }

    override fun getIntField(vm: BaseVM, dvmObject: DvmObject<*>, dvmField: DvmField): Int {
        logger.info("call method ${dvmObject.objectType.className} getIntField[${dvmField.fieldName}] pass to appdbg!!")
        val ins = dvmObject.value
        return ins!!.javaClass.getField(dvmField.fieldName).get(ins) as Int
    }

    override fun setBooleanField(vm: BaseVM, dvmObject: DvmObject<*>, dvmField: DvmField, value: Boolean) {
        logger.info("call method ${dvmObject.objectType.className} setBooleanField[${dvmField.fieldName}] pass to appdbg!!")
        val ins = dvmObject.value
        ins!!.javaClass.getField(dvmField.fieldName).set(ins,value)
    }

    override fun getBooleanField(vm: BaseVM, dvmObject: DvmObject<*>, dvmField: DvmField): Boolean {
        logger.info("call method ${dvmObject.objectType.className} getBooleanField[${dvmField.fieldName}] pass to appdbg!!")
        val ins = dvmObject.value
        return ins!!.javaClass.getField(dvmField.fieldName).get(ins) as Boolean
    }

    override fun setFloatField(vm: BaseVM, dvmObject: DvmObject<*>, dvmField: DvmField, value: Float) {
        logger.info("call method ${dvmObject.objectType.className} setFloatField[${dvmField.fieldName}] pass to appdbg!!")
        val ins = dvmObject.value
        ins!!.javaClass.getField(dvmField.fieldName).set(ins,value)
    }

    override fun getFloatField(vm: BaseVM, dvmObject: DvmObject<*>, dvmField: DvmField): Float {
        logger.info("call method ${dvmObject.objectType.className} getFloatField[${dvmField.fieldName}] pass to appdbg!!")
        val ins = dvmObject.value
        return ins!!.javaClass.getField(dvmField.fieldName).get(ins) as Float
    }

    override fun setLongField(vm: BaseVM, dvmObject: DvmObject<*>, dvmField: DvmField, value: Long) {
        logger.info("call method ${dvmObject.objectType.className} setLongField[${dvmField.fieldName}] pass to appdbg!!")
        val ins = dvmObject.value
        ins!!.javaClass.getField(dvmField.fieldName).set(ins,value)
    }

    override fun getLongField(vm: BaseVM, dvmObject: DvmObject<*>, dvmField: DvmField): Long {
        logger.info("call method ${dvmObject.objectType.className} getLongField[${dvmField.fieldName}] pass to appdbg!!")
        val ins = dvmObject.value
        return ins!!.javaClass.getField(dvmField.fieldName).get(ins) as Long
    }

    override fun setDoubleField(vm: BaseVM, dvmObject: DvmObject<*>, dvmField: DvmField, value: Double) {
        logger.info("call method ${dvmObject.objectType.className} setDoubleField[${dvmField.fieldName}] pass to appdbg!!")
        val ins = dvmObject.value
        ins!!.javaClass.getField(dvmField.fieldName).set(ins,value)
    }

    // FIXME: 2022/4/22 getDoubleField not implement

    override fun setStaticObjectField(vm: BaseVM?, dvmClass: DvmClass, dvmField: DvmField, value: DvmObject<*>) {
        logger.info("call method ${dvmClass.className} setStaticObjectField[${dvmField.fieldName}] pass to appdbg!!")
        val clazz = androidEnvironment.findClass(dvmClass.className.replace('/','.'))
        clazz.getField(dvmField.fieldName).set(null,value.value)
    }

    override fun getStaticObjectField(vm: BaseVM, dvmClass: DvmClass, dvmField: DvmField): DvmObject<*> {
        logger.info("call method ${dvmClass.className} getStaticObjectField[${dvmField.fieldName}] pass to appdbg!!")
        val clazz = androidEnvironment.findClass(dvmClass.className.replace('/','.'))
        return UnidbgWrapperUtils.toUnidbgObject(vm,clazz.getField(dvmField.fieldName).get(null))
    }

    override fun setStaticBooleanField(vm: BaseVM, dvmClass: DvmClass, dvmField: DvmField, value: Boolean) {
        logger.info("call method ${dvmClass.className} setStaticBooleanField[${dvmField.fieldName}] pass to appdbg!!")
        val clazz = androidEnvironment.findClass(dvmClass.className.replace('/','.'))
        clazz.getField(dvmField.fieldName).set(null,value)
    }

    override fun getStaticBooleanField(vm: BaseVM, dvmClass: DvmClass, dvmField: DvmField): Boolean {
        logger.info("call method ${dvmClass.className} getStaticBooleanField[${dvmField.fieldName}] pass to appdbg!!")
        val clazz = androidEnvironment.findClass(dvmClass.className.replace('/','.'))
        return clazz.getField(dvmField.fieldName).get(null) as Boolean
    }

    override fun setStaticDoubleField(vm: BaseVM, dvmClass: DvmClass, dvmField: DvmField, value: Double) {
        logger.info("call method ${dvmClass.className} setStaticDoubleField[${dvmField.fieldName}] pass to appdbg!!")
        val clazz = androidEnvironment.findClass(dvmClass.className.replace('/','.'))
        clazz.getField(dvmField.fieldName).set(null,value)
    }

    // FIXME: 2022/4/22 getStaticDoubleField not implement

    // FIXME: 2022/4/22 setStaticByteField not implement

    override fun getStaticByteField(vm: BaseVM, dvmClass: DvmClass, dvmField: DvmField): Byte {
        logger.info("call method ${dvmClass.className} getStaticByteField[${dvmField.fieldName}] pass to appdbg!!")
        val clazz = androidEnvironment.findClass(dvmClass.className.replace('/','.'))
        return clazz.getField(dvmField.fieldName).get(null) as Byte
    }

    override fun setStaticIntField(vm: BaseVM, dvmClass: DvmClass, dvmField: DvmField, value: Int) {
        logger.info("call method ${dvmClass.className} setStaticIntField[${dvmField.fieldName}] pass to appdbg!!")
        val clazz = androidEnvironment.findClass(dvmClass.className.replace('/','.'))
        clazz.getField(dvmField.fieldName).set(null,value)
    }

    override fun getStaticIntField(vm: BaseVM, dvmClass: DvmClass, dvmField: DvmField): Int {
        logger.info("call method ${dvmClass.className} getStaticIntField[${dvmField.fieldName}] pass to appdbg!!")
        val clazz = androidEnvironment.findClass(dvmClass.className.replace('/','.'))
        return clazz.getField(dvmField.fieldName).get(null) as Int
    }

    override fun setStaticFloatField(vm: BaseVM, dvmClass: DvmClass, dvmField: DvmField, value: Float) {
        logger.info("call method ${dvmClass.className} setStaticFloatField[${dvmField.fieldName}] pass to appdbg!!")
        val clazz = androidEnvironment.findClass(dvmClass.className.replace('/','.'))
        clazz.getField(dvmField.fieldName).set(null,value)
    }

    // FIXME: 2022/4/22 getStaticFloatField not implement

    override fun setStaticLongField(vm: BaseVM, dvmClass: DvmClass, dvmField: DvmField, value: Long) {
        logger.info("call method ${dvmClass.className} setStaticLongField[${dvmField.fieldName}] pass to appdbg!!")
        val clazz = androidEnvironment.findClass(dvmClass.className.replace('/','.'))
        clazz.getField(dvmField.fieldName).set(null,value)
    }

    override fun getStaticLongField(vm: BaseVM, dvmClass: DvmClass, dvmField: DvmField): Long {
        logger.info("call method ${dvmClass.className} getStaticIntField[${dvmField.fieldName}] pass to appdbg!!")
        val clazz = androidEnvironment.findClass(dvmClass.className.replace('/','.'))
        return clazz.getField(dvmField.fieldName).get(null) as Long
    }


    // TODO: 2022/4/22 implement other get set method
}