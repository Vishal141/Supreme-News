package com.example.supremenews.ui.downloaded

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.supremenews.Adapters.HomeNewsAdapter
import com.example.supremenews.R
import com.example.supremenews.models.News
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.io.ObjectInputStream

class Downloaded : AppCompatActivity() {

    private lateinit var recyclerView:RecyclerView
    private var adapter:HomeNewsAdapter?=null
    private var mNews:MutableLiveData<List<News>>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_downloaded)

        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.layoutManager = LinearLayoutManager(this)

        mNews = MutableLiveData()
        mNews!!.value = ArrayList()
        adapter = HomeNewsAdapter(this,mNews)
        recyclerView.adapter = adapter

        mNews!!.observe(this, Observer {
            adapter!!.setmNews(mNews)
        })

        readData()
    }

    fun readData()
    {
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
            {
                var downloadDir = File(dataDir.absolutePath+File.separator+"download")
                var list:ArrayList<News> = ArrayList()
                for(f:File in downloadDir.listFiles()){
                    var fi = FileInputStream(f)
                    var oi = ObjectInputStream(fi)
                    var news:News = oi.readObject() as News
                    list.add(news)
                    mNews!!.postValue(list)
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }


    fun back(view:View){
        onBackPressed()
    }
}