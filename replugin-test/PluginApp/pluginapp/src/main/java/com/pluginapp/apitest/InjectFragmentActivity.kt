package com.pluginapp.apitest

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.pluginapp.Utils
import kotlinx.android.synthetic.main.activity_simple_button.*

/**
 * Test inject [Fragment] and permissions request.
 */
class InjectFragmentActivity : BaseActivity() {
    private var mFragment: InjectFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        button.text = TEXT_ATTACH
    }

    override fun onClicked(view: View?) {
        if (mFragment == null) {
            val fragment = InjectFragment()
            supportFragmentManager.beginTransaction()
                .replace(container.id, fragment)
                .commit()
            mFragment = fragment
            button.text = TEXT_DETACH
            return
        }

        val fragment = mFragment ?: return
        mFragment = null
        supportFragmentManager.beginTransaction()
            .remove(fragment)
            .commit()
        button.text = TEXT_ATTACH
    }

    companion object {
        private const val TEXT_ATTACH = "Attach Fragment"
        private const val TEXT_DETACH = "Detach Fragment"
    }
}

/**
 * Test inject [Fragment] and permissions request.
 */
internal class InjectFragment : BaseFragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        button.text = TEXT_PERMISSIONS_TEST
    }

    override fun onClicked(view: View?) {
        requestPermissions(
            arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
            REQUEST_PERMISSIONS_TEST
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        for (i in permissions.indices) {
            val permission = permissions[i]
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                return Utils.toast(activity, "$permission DENIED!")
            }
        }

        Utils.toast(activity, "Permissions OK!")
    }

    companion object {
        private const val REQUEST_PERMISSIONS_TEST = 1
        private const val TEXT_PERMISSIONS_TEST = "Request Permissions"
    }
}