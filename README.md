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
            applicationId "com.qihoo360.replugin.sample.host"
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
        compile 'androidx.qihoo360.replugin:replugin-host-lib:2.3.3'
        ...
    }
```

#### 第 3 步：配置 Application 类
    让工程的 Application 直接继承自 RePluginApplication。
```Java
    public class MainApplication extends RePluginApplication {
    }
```
或者使用自定义 Application 类
```Java
    public class MainApplication extends Application {

        @Override
        protected void attachBaseContext(Context base) {
            super.attachBaseContext(base);

            RePlugin.App.attachBaseContext(this);
            ....
        }

        @Override
        public void onCreate() {
            super.onCreate();

            RePlugin.App.onCreate();
            ....
        }

        @Override
        public void onLowMemory() {
            super.onLowMemory();

            /* Not need to be called if your application's minSdkVersion > = 14 */
            RePlugin.App.onLowMemory();
            ....
        }

        @Override
        public void onTrimMemory(int level) {
            super.onTrimMemory(level);

            /* Not need to be called if your application's minSdkVersion > = 14 */
            RePlugin.App.onTrimMemory(level);
            ....
        }

        @Override
        public void onConfigurationChanged(Configuration config) {
            super.onConfigurationChanged(config);

            /* Not need to be called if your application's minSdkVersion > = 14 */
            RePlugin.App.onConfigurationChanged(config);
            ....
        }
    }
```
