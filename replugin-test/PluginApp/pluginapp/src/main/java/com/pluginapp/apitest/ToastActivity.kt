package com.pluginapp.apitest

import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.pluginapp.Utils

/**
 * Test [Toast]
 */
class ToastActivity : BaseActivity() {
    override fun onClicked(view: View?) {
        Utils.toast(this@ToastActivity, (view as? TextView)?.text)
    }
}