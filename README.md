# Appdbg
make it possible to run android dex file in original Java Virtual Machine.

## Features
- change every class before it will be loaded
- change every item of the class after it was loaded
- hook java method
- implement native method by yourself or [unidbg](https://github.com/zhkl0228/unidbg)...

## Start

- **only test fully with jdk 1.8**

1. open the project with IDEA

2. add vm option => -Xverify:none

3. patch jdk_path/...../libjvm.dylib,which make it possible to load class,the name begin with "java"
![](assets/package.png)

4. use the jre you modified

5. build test apk by [test-app sub project](test-app)

6. run [main](core/src/main/java/jmp0/Main.kt)
   
![](assets/1.png)

## Implement native method with unidbg
**auto implement reflection method by appdbg**
- invoke method 
![](assets/jni0.png)
- native code source
![](assets/jni1.png)  
![](assets/jni2.png)
- result
![](assets/jni3.png)

## About
it's hard for me to implement all Android runtime well，if you can help me to make this project better,Thanks for your contribution. 

## Thanks
- [robolectric](https://github.com/robolectric/robolectric)
- [dex2jar](https://github.com/pxb1988/dex2jar)
- [Apktool](https://github.com/iBotPeaches/Apktool)
- [javassist](https://github.com/jboss-javassist/javassist)
- [unidbg](https://github.com/zhkl0228/unidbg)