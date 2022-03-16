package jmp0.app.mock.annotations

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
/**
 * @param type real type it is
 */
annotation class HookReturnType(val type:String)
