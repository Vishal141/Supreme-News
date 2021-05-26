package com.example.supremenews

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.supremenews.asynctasks.NewsCountAsyncTask
import com.example.supremenews.ui.downloaded.Downloaded
import com.google.android.gms.ads.MobileAds


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MobileAds.initialize(
            this
        ) { }

        val handler = Handler()
        handler.postDelayed({
            if(checkInternet()==false || checkInternet()==null)
            {
                val intent = Intent(applicationContext,Downloaded::class.java)
                intent.putExtra("FROM","MAIN")
                startActivity(intent)
            }
            else
                startActivity(Intent(applicationContext,MainDrawerActivity::class.java))
            finish()
        },2000)

        NewsCountAsyncTask().execute()
    }

    private fun checkInternet():Boolean?{
        val cm:ConnectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork:NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting
    }
}