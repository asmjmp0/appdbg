1. patch jdk_path/...../libjvm.dylib,which make it possible to load class,the name begin with "java"
   ![](assets/package.png)

2. use the jre you modified(**or use jdk in [appdbg-JDK](https://github.com/asmjmp0/appdbg-JDK)**)

3. change idea settings
- set gradle jdk version with the patched jdk
  ![](assets/gradle0.png)
- set gradle java home with the patched jdk in [gradle.properties](gradle.properties)
  ![](assets/gradle1.png)