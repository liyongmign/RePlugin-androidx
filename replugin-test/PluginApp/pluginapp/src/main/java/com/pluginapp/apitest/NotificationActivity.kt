package com.pluginapp.apitest

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.pluginapp.MainActivity
import com.pluginapp.R
import kotlinx.android.synthetic.main.activity_simple_button.*

/**
 * Test [Notification]
 */
class NotificationActivity : BaseActivity() {
    private lateinit var mSmallIcon: Bitmap
    private var mNotifyId = 1
    private var usingCustomLayout = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mSmallIcon = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher_round)

        textView.setOnClickListener {
            usingCustomLayout = !usingCustomLayout
            updateText()
        }
        updateText()
    }

    override fun onDestroy() {
        super.onDestroy()
        mSmallIcon.recycle()
    }

    private fun updateText() {
        textView.text = if (usingCustomLayout) TEXT_USING_CUSTOM else TEXT_NO_USING_CUSTOM
    }

    override fun onClicked(view: View?) {
        val notifyId = mNotifyId
        mNotifyId++
        if (!usingCustomLayout) return sendDefaultNotification(notifyId)
        sendCustomNotification(notifyId)
    }

    private fun sendDefaultNotification(notifyId: Int) {
        // click to open activity
        val intent = Intent(this, MainActivity::class.java).apply {
            this.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle("Test Notification")
            .setContentText("This is a test notification!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        showNotification(notifyId, notification)
    }

    private fun sendCustomNotification(notifyId: Int) {
        // click to open activity
        val intent = Intent(this, MainActivity::class.java).apply {
            this.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        // custom layout
        val remoteViews = RemoteViews(packageName, R.layout.layout_custom_notification)
        remoteViews.setImageViewBitmap(R.id.notify_icon, mSmallIcon)
        remoteViews.setTextViewText(R.id.notify_title, "Test Notification")
        remoteViews.setTextViewText(R.id.notify_message, "This is a test notification!")

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContent(remoteViews)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        showNotification(notifyId, notification)
    }

    private fun showNotification(notifyId: Int, notification: Notification) {
        with(NotificationManagerCompat.from(this)) {
            if (TEST_CHANNEL != null) this.createNotificationChannel(TEST_CHANNEL)
            this.notify(notifyId, notification)
        }
    }

    companion object {
        private const val CHANNEL_NAME = "test_notification_name"
        private const val CHANNEL_ID = "test_notification_id"
        private val TEST_CHANNEL: NotificationChannel?
        private const val TEXT_USING_CUSTOM = "Using custom."
        private const val TEXT_NO_USING_CUSTOM = "No using custom."

        init {
            TEST_CHANNEL = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            } else null
        }
    }
}