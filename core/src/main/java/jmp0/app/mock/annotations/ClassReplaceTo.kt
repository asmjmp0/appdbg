package jmp0.app.mock.annotations

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
/**
 * @param to which class you want to replace to
 * and insert uuid as xxUuid
 */
annotation class ClassReplaceTo(val to:String)
