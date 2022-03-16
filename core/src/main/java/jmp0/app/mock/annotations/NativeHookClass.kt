package jmp0.app.mock.annotations


/**
 * @param targetClass which target class you want to hook
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class NativeHookClass(val targetClass:String)
