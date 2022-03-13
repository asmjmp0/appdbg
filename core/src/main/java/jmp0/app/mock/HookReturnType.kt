package jmp0.app.mock

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
/**
 * @param type real type it is
 */
annotation class HookReturnType(val type:String)
