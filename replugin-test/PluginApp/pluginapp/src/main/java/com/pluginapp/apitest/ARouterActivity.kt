package com.pluginapp.apitest

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.pluginapp.Utils

/**
 * Test [ARouter]
 */
@Route(path = ARouterActivity.PATH_AROUTER_ACTIVITY)
class ARouterActivity : BaseActivity() {
    override fun onClicked(view: View?) {
        Utils.toast(this, "This is activity open by ARouter.")
    }

    companion object {
        private const val TAG = "ARouterActivity"
        const val PATH_AROUTER_ACTIVITY = "/$TAG/AROUTER/activity"
    }
}