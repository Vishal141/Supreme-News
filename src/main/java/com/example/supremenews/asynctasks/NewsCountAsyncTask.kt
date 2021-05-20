package com.example.supremenews.asynctasks

import android.os.AsyncTask

import androidx.lifecycle.MutableLiveData
import com.example.supremenews.models.News
import com.example.supremenews.models.NewsArray
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class NewsCountAsyncTask : AsyncTask<Void,Void,List<News>>(){
    private val SEARCH_URL = "https://supremenews.herokuapp.com/api/news"
    override fun doInBackground(vararg params: Void?): List<News>? {
        try {
            val url = URL(SEARCH_URL)
            val httpConnection:HttpURLConnection = url.openConnection() as HttpURLConnection
            httpConnection.requestMethod = "GET"
            val response:String = httpConnection.inputStream.bufferedReader().readText()
           // println(response)
            val gson = GsonBuilder().setLenient().create()
            val newsArray:NewsArray = gson.fromJson(response,NewsArray::class.java)
            if(newsArray.newsBulk!=null)
                newsArray.newsBulk

        }catch (e:Exception){
            e.printStackTrace()
        }

        return ArrayList();
    }

}