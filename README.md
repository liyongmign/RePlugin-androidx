Source from: [360-RePlugin](https://github.com/Qihoo360/RePlugin)

##### 主程序接入指南
见[主程序接入指南](https://github.com/Qihoo360/RePlugin/wiki/主程序接入指南)

在项目根目录的 build.gradle（注意：不是 app/build.gradle） 中添加 replugin-host-gradle 依赖：
```Gradle
    buildscript {
        dependencies {
            classpath 'androidx.qihoo360.replugin:replugin-host-gradle:2.3.3'
            ...
        }
    }
```
