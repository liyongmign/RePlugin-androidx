package com.test.replugin

import android.graphics.Color
import android.os.Bundle
import android.os.RemoteException
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Button
import android.widget.ScrollView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import com.qihoo360.replugin.RePlugin
import kotterknife.bindView
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private val buttonCopyToSdcard: Button by bindView(R.id.button_copy_to_sdcard)
    private val buttonInstallApk: Button by bindView(R.id.button_install_apk)
    private val buttonOpenApk: Button by bindView(R.id.button_open_apk)
    private val buttonOpenClass: Button by bindView(R.id.button_open_class)
    private val tvMessage: TextView by bindView(R.id.tv_message)

    private val mExecutor = Executors.newCachedThreadPool()
    private lateinit var apkFile: File
    private var mPluginPkName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonCopyToSdcard.setOnClickListener { onCopyToSdcardClicked() }
        buttonInstallApk.setOnClickListener { onInstallApkClicked() }
        buttonOpenApk.setOnClickListener { onOpenApkClicked() }
        buttonOpenClass.setOnClickListener { onOpenClassClicked() }

        tvMessage.setOnLongClickListener {
            clearMessage()
            return@setOnLongClickListener true
        }

        // init apk file
        val dir = getExternalFilesDir(null)!!
        apkFile = File(dir, APK_SOURCE)
        addMessage(redMessage("apkFile:"))
        addMessage("path = ${apkFile.absolutePath} \nexists = ${apkFile.exists()}")

        buttonCopyToSdcard.setText(if (apkFile.isFile) R.string.delete_from_sdcard else R.string.copy_to_sdcard)

        // init plugin
        initInstalledPlugin()
    }

    private fun initInstalledPlugin() {
        buttonInstallApk.lock()
        mExecutor.execute {
            //todo
            val plugins = RePlugin.getPluginInfoList()
            if (plugins?.isNotEmpty() == true) {
                mPluginPkName = plugins[0].name
            }
            runOnUiThread {
                buttonInstallApk.setText(if (mPluginPkName != null) R.string.uninstall_apk else R.string.install_apk)
                buttonInstallApk.unlock()
            }
        }
    }

    private fun onCopyToSdcardClicked() {
        buttonCopyToSdcard.lock()
        // delete
        if (apkFile.isFile) {
            mExecutor.execute {
                apkFile.delete()

                runOnUiThread {
                    buttonCopyToSdcard.setText(R.string.copy_to_sdcard)
                    buttonCopyToSdcard.unlock()
                    addMessage(greenMessage("Delete ${apkFile.absolutePath}: ${!apkFile.exists()}"))
                }
            }
            return
        }

        // copy
        mExecutor.execute {
            try {
                copyApkToSdcard()
            } catch (e: Exception) {
                runOnUiThread {
                    addMessage(redMessage("No such file in assets: ${e.message}"))
                    buttonCopyToSdcard.unlock()
                }
            }
        }
    }

    private fun copyApkToSdcard() {
        assets.open(APK_SOURCE).use { inStream ->
            FileOutputStream(apkFile).use { outStream ->
                val bytes = ByteArray(1024)
                var len = inStream.read(bytes)
                while (len > 0) {
                    outStream.write(bytes, 0, len)
                    len = inStream.read(bytes)
                }
                outStream.flush()

                runOnUiThread {
                    buttonCopyToSdcard.setText(R.string.delete_from_sdcard)
                    buttonCopyToSdcard.unlock()
                    addMessage(greenMessage("Copy to ${apkFile.absolutePath}: ${apkFile.exists()}"))
                }
            }
        }
    }

    private fun onInstallApkClicked() {
        buttonInstallApk.lock()

        val installApk = apkFile

        // not prepared
        if (!installApk.isFile) {
            buttonInstallApk.unlock()
            return addMessage(redMessage("Please copy apk file to ${installApk.absolutePath} first!"))
        }

        if (mPluginPkName?.isNotEmpty() == true) {
            addMessage("Reinstalling... ${installApk.absolutePath}")
        } else {
            addMessage("Installing... ${installApk.absolutePath}")
        }
        mExecutor.execute {
            try {
                //todo
                val pluginInfo = RePlugin.install(installApk.absolutePath)
                val message = SpannableStringBuilder("Installed result: ")
                val stringId: Int
                if (pluginInfo != null) {
                    message.append(greenMessage("Success!"))
                    stringId = R.string.uninstall_apk
                    initInstalledPlugin()
                } else {
                    message.append(redMessage("Failed!"))
                    stringId = R.string.install_apk
                }

                runOnUiThread {
                    buttonInstallApk.setText(stringId)
                    addMessage(message)
                }
            } catch (e: RemoteException) {
                runOnUiThread { addMessage(redMessage("Install ${installApk.absolutePath} failed!")) }
            } finally {
                runOnUiThread { buttonInstallApk.unlock() }
            }
        }
    }

    private fun onOpenApkClicked() {
        if (mPluginPkName?.isNotEmpty() != true) {
            return addMessage(redMessage("Please install apk first!"))
        }

        addMessage("Open plugin...")
        try {
            //todo
            val intent = RePlugin.createIntent(mPluginPkName, APK_LAUNCH_CLASS)
            if (intent != null && RePlugin.startActivity(this, intent)) {
                addMessage(greenMessage("Plugin opened."))
            } else {
                addMessage(redMessage("Cannot create launch intent for package: $mPluginPkName"))
            }
        } catch (e: RemoteException) {
            addMessage(redMessage("Open plugin failed!"))
        }
    }

    private fun onOpenClassClicked() {
        if (mPluginPkName?.isNotEmpty() != true) {
            return addMessage(redMessage("Please install apk first!"))
        }

        addMessage("Open class from plugin...")
        try {
            //todo
            addMessage("Currently not supported.")
        } catch (e: RemoteException) {
            addMessage(redMessage("Open class from plugin failed!"))
        }
    }

    private fun redMessage(message: String?): CharSequence? {
        return colorMessage(message, Color.RED)
    }

    private fun greenMessage(message: String?): CharSequence? {
        return colorMessage(message, Color.GREEN)
    }

    private fun colorMessage(message: String?, @ColorInt color: Int): CharSequence? {
        val text = SpannableStringBuilder(message ?: return null)
        text.setSpan(ForegroundColorSpan(color), 0, message.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return text
    }

    private val mMessages = SpannableStringBuilder()
    private fun addMessage(message: CharSequence?) {
        mMessages.append("\n")
        if (message != null) {
            mMessages.append(message)
        }
        tvMessage.text = mMessages
        (tvMessage.parent as? ScrollView)?.fullScroll(ScrollView.FOCUS_DOWN)
    }

    private fun clearMessage() {
        mMessages.clear()
        tvMessage.text = null
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            mExecutor.shutdown()
            mExecutor.awaitTermination(100, TimeUnit.MILLISECONDS)
        } catch (e: Exception) {
            mExecutor.shutdownNow()
        }
    }

    private fun View.lock() = this.setLock(true)
    private fun View.unlock() = this.setLock(false)
    private fun View.setLock(locked: Boolean) {
        this@setLock.isEnabled = !locked
    }

    companion object {
        private const val APK_SOURCE = "cyzg.apk" // "app-plugin.apk"
        private const val APK_LAUNCH_CLASS = "com.gxcxjt.congyezige.LaunchActivity" // "com.pluginapp.MainActivity"
    }

}



