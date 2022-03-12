package jmp0.app.mock

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
/**
 * @param to which class you want to replace to
 * and insert uuid as xxUuid
 */
annotation class ReplaceTo(val to:String)
