package com.pluginapp.apitest

import android.view.View
import android.widget.Toast
import com.pluginapp.Utils
import com.pluginapp.so.TestSo

/**
 * Test [Toast]
 */
class SoFilesActivity : BaseActivity() {
    private val testSo = TestSo()

    override fun onClicked(view: View?) {
        Utils.toast(this@SoFilesActivity, testSo.testMessage())
    }
}