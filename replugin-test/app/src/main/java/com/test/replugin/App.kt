package com.test.replugin

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.alibaba.android.arouter.launcher.ARouter
import com.qihoo360.replugin.RePlugin
import com.qihoo360.replugin.RePluginConfig

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        RePlugin.App.onCreate()
        RePlugin.enableDebugger(this, BuildConfig.DEBUG)

        //ARouter
        if (BuildConfig.DEBUG) {
            ARouter.openDebug()
            ARouter.openLog()
        }
        ARouter.init(this)
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
        // verify sign
        val config = RePluginConfig()
        config.verifySign = !BuildConfig.DEBUG
        RePlugin.App.attachBaseContext(this, config)
        RePlugin.addCertSignature("DFDF449F1D9973E82A6E5A7F3A5FC57B")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        RePlugin.App.onConfigurationChanged(newConfig)
    }
}