package com.poptato.app

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber
import com.poptato.design_system.R

class FcmService: FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Timber.d("[FCM] FcmService -> token: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val title = message.data["title"] ?: message.notification?.title
        val body = message.data["todolist"] ?: message.notification?.body

        if (message.data.isNotEmpty()) {
            Timber.d("[FCM] FcmService -> title: ${message.data["title"].toString()} & message: ${message.data["todolist"].toString()}")



            sendNotification(title, body)
        } else {
            Timber.d("[FCM] FcmService -> 수신에러: data가 비어있습니다. 메시지를 수신하지 못했습니다.")
        }

    }

    private fun sendNotification(title: String?, body: String?) {

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.ic_app)
            .setAutoCancel(true)
            .build()


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_TITLE,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    companion object {
        private const val CHANNEL_ID = "CHANNEL_ID"
        private const val NOTIFICATION_ID = 1
        private const val ID = "ID"
        private const val CHANNEL_NAME = "CHANNEL_NAME"
        private const val CHANNEL_TITLE = "CHANNEL_TITLE"
    }
}