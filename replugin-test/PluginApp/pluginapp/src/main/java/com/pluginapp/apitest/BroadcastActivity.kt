package com.pluginapp.apitest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import com.pluginapp.Utils
import kotlinx.android.synthetic.main.activity_simple_button.*

/**
 * Test [BroadcastReceiver]
 */
class BroadcastActivity : BaseActivity() {
    private var mReceiver: BroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textView.text = TEXT_SEND_BROADCAST
        textView.setOnClickListener {
            mReceiver ?: return@setOnClickListener Utils.toast(applicationContext, "No receiver!")

            val intent = Intent(ACTION)
            intent.putExtra(TestBroadcastReceiver.KEY_MESSAGE, "This message is from Broadcast!")
            sendBroadcast(intent)
        }
        updateButton()
    }

    private fun updateButton() {
        button.text = if (mReceiver != null) TEXT_UNREGISTER_BROADCAST else TEXT_REGISTER_BROADCAST
    }

    override fun onDestroy() {
        super.onDestroy()
        stopReceiver(false)
    }

    private fun stopReceiver(update: Boolean = true) {
        val receiver = mReceiver ?: return
        mReceiver = null
        unregisterReceiver(receiver)
        if (update) updateButton()
    }

    override fun onClicked(view: View?) {
        if (mReceiver == null) {
            val receiver = TestBroadcastReceiver()
            mReceiver = receiver
            registerReceiver(receiver, IntentFilter(ACTION))
            updateButton()
            return
        }

        stopReceiver()
    }

    companion object {
        private const val TEXT_REGISTER_BROADCAST = "Register Broadcast"
        private const val TEXT_UNREGISTER_BROADCAST = "Unregister Broadcast"
        private const val TEXT_SEND_BROADCAST = "Send Broadcast"
        private const val ACTION = "TestBroadcast"

    }
}

private class TestBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra(KEY_MESSAGE)
        Utils.toast(context, message)
    }

    companion object {
        const val KEY_MESSAGE = "msgBroadcast"
    }
}