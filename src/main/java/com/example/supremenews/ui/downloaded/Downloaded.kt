package com.example.supremenews.ui.downloaded

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.supremenews.Adapters.HomeNewsAdapter
import com.example.supremenews.MainDrawerActivity
import com.example.supremenews.R
import com.example.supremenews.models.News
import java.io.File
import java.io.FileInputStream
import java.io.ObjectInputStream

class Downloaded : AppCompatActivity() {

    private lateinit var recyclerView:RecyclerView
    private lateinit var warningLayout:RelativeLayout
    private var adapter:HomeNewsAdapter?=null
    private var mNews:MutableLiveData<List<News>>?=null

    private var receivedIntent:Intent?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_downloaded)

        receivedIntent = intent

        warningLayout = findViewById(R.id.warningLayout)
        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.layoutManager = LinearLayoutManager(this)

        warningLayout.visibility = View.VISIBLE

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
            if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
            {
                val downloadDir = File(dataDir.absolutePath+File.separator+"download")
                val list:ArrayList<News> = ArrayList()
                for(f:File in downloadDir.listFiles()){
                    val fi = FileInputStream(f)
                    val oi = ObjectInputStream(fi)
                    val news:News = oi.readObject() as News
                    list.add(news)
                    mNews!!.postValue(list)
                    warningLayout.visibility = View.GONE
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }


    fun back(view:View){
        try {
            val from = receivedIntent!!.getStringExtra("FROM")
            if(from.equals("MAIN") && checkInternet()==true)
                startActivity(Intent(applicationContext,MainDrawerActivity::class.java))
        }catch (e:Exception){
            e.printStackTrace()
        }
        onBackPressed()
    }

    private fun checkInternet():Boolean?{
        val cm: ConnectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting
    }
}