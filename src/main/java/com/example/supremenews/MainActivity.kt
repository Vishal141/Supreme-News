package com.example.supremenews

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
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
            startActivity(Intent(applicationContext,MainDrawerActivity::class.java))
            finish()
        },2000)

    }
}