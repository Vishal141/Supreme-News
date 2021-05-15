package com.example.supremenews.asynctasks

import android.os.AsyncTask

import androidx.lifecycle.MutableLiveData
import com.example.supremenews.models.News
import com.example.supremenews.models.NewsArray
import com.google.gson.Gson
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class SearchAsyncTask(var mNews: MutableLiveData<List<News>>) : AsyncTask<String,Void,Void>(){
    private val SEARCH_URL = "http://supremenews.herokuapp.com/api/search/news?search="
    override fun doInBackground(vararg params: String?): Void? {
        try {
            val item = params[0];
            val url = URL(SEARCH_URL+item)
            val httpConnection:HttpURLConnection = url.openConnection() as HttpURLConnection

            val response:String = httpConnection.inputStream.bufferedReader().readText()
            println(response)
            val gson = Gson()
            val newsArray:NewsArray = gson.fromJson(response,NewsArray::class.java)
            if(newsArray.newsBulk!=null)
               mNews.postValue(newsArray.newsBulk)

        }catch (e:Exception){
            e.printStackTrace()
        }

        return null
    }

}