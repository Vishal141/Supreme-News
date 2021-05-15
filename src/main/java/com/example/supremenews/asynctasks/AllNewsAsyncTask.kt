package com.example.supremenews.asynctasks

import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.lifecycle.LiveData
import com.airbnb.lottie.LottieAnimationView
import com.example.supremenews.models.News
import com.example.supremenews.models.NewsArray
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class AllNewsAsyncTask(val loadingAnimation: LottieAnimationView): AsyncTask<String, Void,List<News>>() {

    private var mNews:ArrayList<News> = ArrayList()

    override fun onPreExecute() {
        //super.onPreExecute()
        Handler(Looper.getMainLooper()).post {
            loadingAnimation.visibility = View.VISIBLE
            loadingAnimation.playAnimation()
        }
        println("on preExecute")
    }

    override fun doInBackground(vararg params: String?): List<News>? {
        try {
            val url = URL(params[0])
            val httpConnection:HttpURLConnection = url.openConnection() as HttpURLConnection
            httpConnection.requestMethod = "GET"
            val response = httpConnection.inputStream.bufferedReader().readText()
            println(response)
            val gson = GsonBuilder().setLenient().create()
            val allNews:NewsArray = gson.fromJson(response,NewsArray::class.java)

            if(allNews.newsBulk!=null)
                return allNews.newsBulk

        }catch (e:Exception){
            e.printStackTrace()
        }
        return mNews
    }

    override fun onPostExecute(result: List<News>?) {
        loadingAnimation.pauseAnimation();
        loadingAnimation.visibility = View.GONE
        super.onPostExecute(result)
    }

}