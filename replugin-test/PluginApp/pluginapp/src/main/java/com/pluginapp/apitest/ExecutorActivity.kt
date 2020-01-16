package com.pluginapp.apitest

import android.view.View
import com.pluginapp.Utils
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * Test [ExecutorService]
 */
class ExecutorActivity : BaseActivity() {
    private val mExecutor: ExecutorService = Executors.newCachedThreadPool()

    override fun onDestroy() {
        super.onDestroy()
        try {
            mExecutor.shutdown()
            mExecutor.awaitTermination(100, TimeUnit.MILLISECONDS)
        } catch (e: Exception) {
            mExecutor.shutdownNow()
        }
    }

    override fun onClicked(view: View?) {
        for (i in 0 until 10) {
            mExecutor.execute {
                Thread.sleep(50L * i)
                runOnUiThread {
                    Utils.toast(this@ExecutorActivity, "Thread in executor run -> $i")
                }
            }
        }
    }

}