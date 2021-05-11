package com.example.supremenews.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.supremenews.Adapters.HomeNewsAdapter
import com.example.supremenews.R
import com.example.supremenews.asynctasks.SearchAsyncTask
import com.example.supremenews.models.News

class SearchActivity : AppCompatActivity() {

    private lateinit var searchItem:EditText
    private lateinit var recyclerView: RecyclerView

    private var mNews:MutableLiveData<List<News>>?=null
    private var adapter:HomeNewsAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        searchItem = findViewById(R.id.search_item)
        recyclerView = findViewById(R.id.recycle_view)
        recyclerView.layoutManager  = LinearLayoutManager(this)

        mNews = MutableLiveData()
        mNews!!.value = ArrayList()
        adapter = HomeNewsAdapter(this,mNews)
        recyclerView.adapter = adapter

        mNews!!.observe(this, Observer {
            adapter!!.setmNews(mNews)
        })

        var item = ""
        var task = SearchAsyncTask(mNews!!)
        task.execute(item)

        setListener()
    }

    private fun setListener(){
        searchItem.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var item = s.toString()
                var task = SearchAsyncTask(mNews!!)
                task.execute(item)
            }

        })
    }
}