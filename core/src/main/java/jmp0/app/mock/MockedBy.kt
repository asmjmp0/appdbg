package jmp0.app.mock

@Target(AnnotationTarget.CLASS,AnnotationTarget.FUNCTION,AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.SOURCE)
annotation class MockedBy(val name:String)
