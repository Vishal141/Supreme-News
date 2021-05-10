package com.example.supremenews.ui.newsactivity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.supremenews.R
import com.example.supremenews.models.Global
import com.example.supremenews.models.News

class NewsActivity : AppCompatActivity() {

    private lateinit var publishedDate:TextView
    private lateinit var title:TextView
    private lateinit var subtitle:TextView
    private lateinit var detail:TextView
    private lateinit var newsImage:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        var news:News = Global.news

        publishedDate = findViewById(R.id.published_date)
        title = findViewById(R.id.title)
        subtitle = findViewById(R.id.news_subtitle)
        detail = findViewById(R.id.detail_news)
        newsImage = findViewById(R.id.image_news)

        setNews(news)

    }

    fun setNews(news:News){
        val date = news.published_at.substring(0, 10)
        val time = news.published_at.substring(11, 16)
        publishedDate.text = date + " " + time
        title.text = news.title
        subtitle.text = news.subtitle
        detail.text = news.body
        Glide.with(applicationContext).load(news.image).into(newsImage)
    }

    fun back(view:View){
        onBackPressed()
    }
}