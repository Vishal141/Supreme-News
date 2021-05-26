package com.example.supremenews.asynctasks

import android.os.AsyncTask
import com.example.supremenews.models.Like
import com.google.gson.Gson
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class LikeAsyncTask:AsyncTask<String,Void,Int>() {
    private val link = "https://supremenews.herokuapp.com/news/like/"
    override fun doInBackground(vararg params: String?): Int? {
        println("like task called")
        try {
            val url = URL(link+params[0])
            val httpConnection:HttpURLConnection = url.openConnection() as HttpURLConnection
            httpConnection.requestMethod = "POST"
           // httpConnection.doOutput = true
            val like = Like("1")
            val body:String = Gson().toJson(like)
            httpConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8")
            httpConnection.setFixedLengthStreamingMode(body.length)
            val os = httpConnection.outputStream
            os.write(body.toByteArray())
            os.flush()
            os.close()
            val responseCode = httpConnection.responseCode
            println(body)
            println(responseCode)
        }catch (e:Exception){
            e.printStackTrace()
        }
        return 0
    }
}