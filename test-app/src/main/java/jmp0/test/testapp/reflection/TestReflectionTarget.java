package jmp0.test.testapp.reflection;

public class TestReflectionTarget {
    public String testPublicString = "target";
    private final String testPrivateString = "target";

    public TestReflectionTarget(){}

    public String targetMethod(){
        return testPrivateString;
    }

    public static String staticTargetMethod(){
        return "target";
    }

}
