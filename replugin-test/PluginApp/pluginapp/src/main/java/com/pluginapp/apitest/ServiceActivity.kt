package com.pluginapp.apitest

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.view.View
import com.pluginapp.Utils
import kotlinx.android.synthetic.main.activity_simple_button.*

/**
 * Test [Service]
 */
class ServiceActivity : BaseActivity() {
    private var mService: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateButton()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(false)
    }

    private fun updateButton() {
        button.text = if (mService != null) TEXT_STOP_SERVICE else TEXT_START_SERVICE
    }

    override fun onClicked(view: View?) {
        if (mService == null) {
            val service = Intent(this, TestService::class.java)
            mService = service
            startService(service)
            updateButton()
            return
        }

        stopService()
    }

    private fun stopService(update: Boolean = true) {
        val name = mService ?: return
        mService = null
        stopService(name)
        if (update) updateButton()
    }

    companion object {
        private const val TEXT_START_SERVICE = "start service"
        private const val TEXT_STOP_SERVICE = "stop service"
    }
}

internal class TestService : Service() {
    override fun onBind(intent: Intent?): IBinder? = null
    override fun onCreate() {
        super.onCreate()
        Utils.toast(this, "Service onCreate!")
    }

    override fun onDestroy() {
        super.onDestroy()
        Utils.toast(this, "Service onDestroy!")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Utils.toast(this, "Service onStartCommand!")
        return super.onStartCommand(intent, flags, startId)
    }
}