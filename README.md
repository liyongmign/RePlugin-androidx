Source from: [360-RePlugin](https://github.com/Qihoo360/RePlugin)

### 主程序接入指南
见[360-RePlugin主程序接入指南](https://github.com/Qihoo360/RePlugin/wiki/主程序接入指南)

#### 第 1 步：添加 RePlugin Host Gradle 依赖
在项目根目录的 build.gradle（注意：不是 app/build.gradle） 中添加 replugin-host-gradle 依赖：
```Gradle
buildscript {
        dependencies {
            classpath 'androidx.qihoo360.replugin:replugin-host-gradle:2.3.3'
            ...
        }
    }
```

#### 第 2 步：添加 RePlugin Host Library 依赖
```Gradle
android {
    // ATTENTION!!! Must CONFIG this to accord with Gradle's standard, and avoid some error
    defaultConfig {
        applicationId "com.test.replugin"
        ...
    }
    ...
}

// ATTENTION!!! Must be PLACED AFTER "android{}" to read the applicationId
apply plugin: 'replugin-host-gradle'
 /**
 * 配置项均为可选配置，默认无需添加
 * 更多可选配置项参见replugin-host-gradle的RepluginConfig类
 * 可更改配置项参见 自动生成RePluginHostConfig.java
 */
repluginHostConfig {
    /**
     * 是否使用 AppCompat 库
     * 不需要个性化配置时，无需添加
     */
    useAppCompat = true
    /**
     * 背景不透明的坑的数量
     * 不需要个性化配置时，无需添加
     */
    countNotTranslucentStandard = 6
    countNotTranslucentSingleTop = 2
    countNotTranslucentSingleTask = 3
    countNotTranslucentSingleInstance = 2
}

dependencies {
    implementation 'androidx.qihoo360.replugin:replugin-host-lib:2.3.3'
    ...
}
```

#### 第 3 步：配置 Application 类
    让工程的 Application 直接继承自 RePluginApplication。
```Kotlin
class App : RePluginApplication
```
或者使用自定义 Application 类
```Kotlin
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        RePlugin.App.onCreate()
        // 开启插件调试
        RePlugin.enableDebugger(this, BuildConfig.DEBUG)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        RePlugin.App.onLowMemory()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        RePlugin.App.onTrimMemory(level)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        RePlugin.App.attachBaseContext(this)
        // 签名验证
        val config = RePluginConfig()
        config.verifySign = !BuildConfig.DEBUG
        RePlugin.App.attachBaseContext(this, config)
        RePlugin.addCertSignature("签名文件MD5字符串，记得去掉':'")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        RePlugin.App.onConfigurationChanged(newConfig)
    }
}
```


### 插件接入指南
见[360-RePlugin插件接入指南](https://github.com/Qihoo360/RePlugin/wiki/插件接入指南)

#### 第 1 步：添加 RePlugin Plugin Gradle 依赖
在项目根目录的 build.gradle（注意：不是 app/build.gradle） 中添加 replugin-plugin-gradle 依赖：
```Gradle
buildscript {
    dependencies {
        classpath 'androidx.qihoo360.replugin:replugin-plugin-gradle:2.2.4'
        ...
    }
}
```

#### 第 2 步：添加 RePlugin Plugin Library 依赖
在 app/build.gradle 中应用 replugin-plugin-gradle 插件，并添加 replugin-plugin-lib 依赖:
```Gradle
// 这个plugin需要放在android配置之后，因为需要读取android中的配置项
apply plugin: 'replugin-plugin-gradle'

repluginPluginConfig {
    appModule = ':pluginapp'
    //插件名
    pluginName = "pluginapp"
    //编译的插件包推送到手机的目录
    phoneStorageDir = "/storage/emulated/0/Android/data/com.test.replugin/files/"
    //宿主app的包名
    hostApplicationId = "com.test.replugin"
    //宿主app的启动activity
    hostAppLauncherActivity = "com.test.replugin.MainActivity"
}

dependencies {
    implementation 'androidx.qihoo360.replugin:replugin-plugin-lib:2.3.3'
    ……
}
```