# Appdbg
make it possible to run android dex file in original Java Virtual Machine.

## Features
- change every class before it will be loaded
- change every item of the class after it was loaded
- hook java method
- implement native method by yourself or [unidbg](https://github.com/zhkl0228/unidbg)...
- provide fake source line level debug ability powered by fernflower(https://github.com/fesh0r/fernflower)!!!

## Start

***powered by [patch-jvm](patch-jvm) we do not need to patch jvm by myself anymore!***
- if it has any problem, please see [PatchJDK.md](PatchJDK.md)

1. open the project with IDEA

2. run test suites in [testSuites](core/src/test/java/suites)
   
![](assets/1.png)

## Build test app
include test-app module in [settings.gradle](settings.gradle)
```
include ':test-app'
```

## Publish to mavenLocal
1. run command ` ./gradlew publishToMavenLocal`
2. `implementation "jmp0.appdbg:core:1.0-SNAPSHOT"`

## Implement native method with unidbg
**auto implement reflection method by appdbg**
- invoke method 
![](assets/jni0.png)
- native code source
![](assets/jni1.png)  
![](assets/jni2.png)
- result
![](assets/jni3.png)
  
## source line level debug
1. implement IApkConfig and set generateJarFile and jarWithDebugInfo **true**,run code,and the jar file will be generated in libs/tempLibs dir
![](assets/debug/debug0.png)
2. sync gradle file,and IDEA will index it
![](assets/debug/debug1.png)
3. select one file you want to debug,and click the label.
![](assets/debug/debug2.png)
4. chose the source files generated at temp/**apkName**/decompile_source, click **open** button.
![](assets/debug/debug3.png)
5. set IApkConfig forceDecompile **false**,and set source line breakpoint.
![](assets/debug/debug4.png)
![](assets/debug/debug5.png)
6. run code with **debug mode**!!!
![](assets/debug/debug0.gif)

## About
it's hard for me to implement all Android runtime well，if you can help me to make this project better,Thanks for your contribution. 

## Thanks
- [robolectric](https://github.com/robolectric/robolectric)
- [dex2jar](https://github.com/pxb1988/dex2jar)
- [Apktool](https://github.com/iBotPeaches/Apktool)
- [javassist](https://github.com/jboss-javassist/javassist)
- [unidbg](https://github.com/zhkl0228/unidbg)
- [fernflower](https://github.com/fesh0r/fernflower)