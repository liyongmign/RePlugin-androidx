package com.pluginapp.apitest

import android.os.*
import android.view.View
import com.pluginapp.Utils
import kotlinx.android.synthetic.main.activity_simple_button.*

/**
 * Test [Handler]
 */
class HandlerActivity : BaseActivity() {
    private var uiHandler: Handler? = null
    private var threadHandler: Handler? = null
    private var testUiHandler = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateButton()
    }

    override fun onDestroy() {
        super.onDestroy()
        uiHandler?.removeCallbacksAndMessages(null)
        uiHandler = null
        threadHandler?.looper?.thread?.interrupt()
        threadHandler?.removeCallbacksAndMessages(null)
        threadHandler = null
    }

    override fun onClicked(view: View?) {
        if (testUiHandler) {
            if (uiHandler == null) {
                uiHandler = Handler(Looper.getMainLooper())
            }
            uiHandler?.post {
                Utils.toast(this@HandlerActivity, "This is UI Handler!")
                testUiHandler = false
                updateButton()
            }
            return
        }

        if (threadHandler?.looper?.thread?.isInterrupted != false) {
            val thread = HandlerThread("threadHandler")
            thread.start()
            threadHandler = object : Handler(thread.looper) {
                override fun handleMessage(msg: Message) {
                    runOnUiThread {
                        Utils.toast(this@HandlerActivity, "This is Thread Handler!")
                        testUiHandler = true
                        updateButton()
                    }
                }
            }
        }
        threadHandler?.sendEmptyMessage(0)
    }

    private fun updateButton() {
        button.text = if (testUiHandler) {
            "Test UI Handler"
        } else {
            "Test Thread Handler"
        }
    }

}