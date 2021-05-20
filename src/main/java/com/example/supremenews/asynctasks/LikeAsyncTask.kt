package com.example.supremenews.asynctasks

import android.os.AsyncTask
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class LikeAsyncTask:AsyncTask<String,Void,Void>() {
    private val Url = "https://supremenews.heroku.com/news/like/"
    override fun doInBackground(vararg params: String?): Void? {
        try {
            val url = URL(Url+params[0])
            val httpConnection:HttpURLConnection = url.openConnection() as HttpURLConnection
            httpConnection.requestMethod = "POST"
            httpConnection.connect()
        }catch (e:Exception){
            e.printStackTrace()
        }
        return null
    }
}