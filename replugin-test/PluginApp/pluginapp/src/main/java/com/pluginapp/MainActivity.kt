package com.pluginapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pluginapp.apitest.*
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Test launcher
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activity_toast.setOnClickListener { startActivity(ToastActivity::class.java) }
        activity_inject_fragment.setOnClickListener { startActivity(InjectFragmentActivity::class.java) }
        activity_executor.setOnClickListener { startActivity(ExecutorActivity::class.java) }
        activity_handler.setOnClickListener { startActivity(HandlerActivity::class.java) }
        activity_service.setOnClickListener { startActivity(ServiceActivity::class.java) }
        activity_broadcast.setOnClickListener { startActivity(BroadcastActivity::class.java) }
        activity_notification.setOnClickListener { startActivity(NotificationActivity::class.java) }
    }

    private fun startActivity(activityClass: Class<out Activity>) {
        startActivity(Intent(this, activityClass))
    }
}