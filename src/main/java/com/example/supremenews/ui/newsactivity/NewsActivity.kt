package com.example.supremenews.ui.newsactivity

import android.app.DownloadManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.supremenews.Adapters.HomeNewsAdapter
import com.example.supremenews.R
import com.example.supremenews.models.Global
import com.example.supremenews.models.News
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

const val AD_UNIT_ID = "ca-app-pub-8904820275824881/6954300639"
class NewsActivity : AppCompatActivity() {

    private lateinit var publishedDate:TextView
    private lateinit var title:TextView
    private lateinit var detail:TextView
    private lateinit var newsImage:ImageView
    private lateinit var shareImage:ImageView
    private lateinit var mAdView:AdView

    private var mInterstitialAd: InterstitialAd? = null
    private val TAG = "NewsActivity";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)


        publishedDate = findViewById(R.id.published_date)
        title = findViewById(R.id.title)
        detail = findViewById(R.id.detail_news)
        newsImage = findViewById(R.id.image_news)
        shareImage = findViewById(R.id.image_share)
        mAdView = findViewById(R.id.adView)

        shareImage.setOnClickListener {
            val news:News = Global.news
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT,news.subtitle)
            startActivity(Intent.createChooser(intent,"Share Via"))
        }

        loadAd()
        setNews(Global.news)
    }

    fun setNews(news:News){
        val date = news.published_at.substring(0, 10)
        val time = news.published_at.substring(11, 16)
        publishedDate.text = HomeNewsAdapter.getTimeDiff(date,time)
        title.text = news.title
        detail.text = news.body
        Glide.with(applicationContext).load(news.image).into(newsImage)
        loadBanner();
    }

    fun back(view:View){
        onBackPressed()
    }

    private fun loadBanner()
    {
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }

    private fun loadAd()
    {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(this, AD_UNIT_ID, adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
               // Toast.makeText(applicationContext,"failed",Toast.LENGTH_LONG).show()
                Log.d(TAG, adError.message)
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
              //  Toast.makeText(applicationContext,"loaded",Toast.LENGTH_LONG).show()
                mInterstitialAd = interstitialAd
                showAd()
            }
        })
    }

    private fun showAd()
    {
        if(mInterstitialAd!=null)
        {
            mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                   // Toast.makeText(applicationContext,"dismissed",Toast.LENGTH_LONG).show()
                    setNews(Global.news)
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                    //Toast.makeText(applicationContext,"failed",Toast.LENGTH_LONG).show()
                    setNews(Global.news)
                }

                override fun onAdShowedFullScreenContent() {
                   // Toast.makeText(applicationContext,"showed",Toast.LENGTH_LONG).show()
                    mInterstitialAd = null;
                    setNews(Global.news)
                }
            }

            mInterstitialAd!!.show(this)
        }else{
           // Toast.makeText(applicationContext,"null",Toast.LENGTH_LONG).show()
            setNews(Global.news)
        }
    }

}