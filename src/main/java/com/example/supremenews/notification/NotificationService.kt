package com.example.supremenews.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.supremenews.BuildConfig
import com.example.supremenews.R
import java.lang.Exception

class NotificationService: Service() {
    private companion object{
        const val CHANNEL_ID:String = "LATEST NEWS NOTIFICATION"
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        showNotification("latest news","")
        return START_STICKY
    }

    private fun showNotification(title:String,url:String){
        val manager:NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
        builder.setSmallIcon(R.drawable.supreme_news_icon_2)
            .setContentTitle(title)

        try {
            val bitmap = BitmapFactory.decodeFile(url)
            builder.setLargeIcon(bitmap)
        }catch (e:Exception){
            e.printStackTrace()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var channel = NotificationChannel(
                CHANNEL_ID,
                "Notification Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            builder.setChannelId(CHANNEL_ID)
            manager.createNotificationChannel(channel)
        }
        manager.notify(System.currentTimeMillis().toInt(),builder.build())
    }
}