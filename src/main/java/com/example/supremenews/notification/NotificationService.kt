package com.example.supremenews.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LiveData
import com.example.supremenews.BuildConfig
import com.example.supremenews.MainActivity
import com.example.supremenews.MainDrawerActivity
import com.example.supremenews.R
import com.example.supremenews.asynctasks.AllNewsAsyncTask
import com.example.supremenews.asynctasks.NewsCountAsyncTask
import com.example.supremenews.models.News
import java.lang.Exception
import java.net.URL

class NotificationService: Service() {
    private companion object{
        const val CHANNEL_ID:String = "LATEST_NEWS_NOTIFICATION"
    }

    private var newsList:List<News>?=null
    private var newCount:Int=0
    private var prevCount:Int = 0

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if(isShow())
        {
            for(i in 0..(newCount-prevCount))
            {
                val news:News = newsList!!.get(i)
                showNotification(news.title,news.image)
                val sp:SharedPreferences = getSharedPreferences("MY_PREP", Context.MODE_PRIVATE);
                sp.edit().putInt("NEWS_COUNT",newCount).apply()
            }
        }
        return START_STICKY
    }

    private fun showNotification(title:String,url:String){
        val manager:NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(this,MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pIntent = PendingIntent.getActivity(this,1,intent,0)
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
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Notification Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            builder.setChannelId(CHANNEL_ID)
            manager.createNotificationChannel(channel)
        }
        manager.notify(System.currentTimeMillis().toInt(),builder.build())
    }

    private fun isShow():Boolean
    {
        val sp:SharedPreferences = getSharedPreferences("MY_PREP", Context.MODE_PRIVATE);
        newCount = sp.getInt("NEWS_COUNT",0)
        newsList = NewsCountAsyncTask().execute().get()
        prevCount = newsList!!.size
        return !(newCount==prevCount)
    }
}