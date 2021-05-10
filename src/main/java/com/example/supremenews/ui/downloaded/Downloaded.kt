package com.example.supremenews.ui.downloaded

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.supremenews.Adapters.HomeNewsAdapter
import com.example.supremenews.R
import com.example.supremenews.models.News
import java.io.File

class Downloaded : AppCompatActivity() {

    private lateinit var recyclerView:RecyclerView
    private var adapter:HomeNewsAdapter?=null
    private var mNews:LiveData<List<News>>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_downloaded)

        recyclerView = findViewById(R.id.recycle_view);

    }

    fun readData()
    {
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
            {
                var downloadDir = File(dataDir.absolutePath+File.separator+"download")
                for(f:File in downloadDir.listFiles()){
                    println(f.name)
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