package com.example.supremenews.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.airbnb.lottie.LottieAnimationView
import com.example.supremenews.R
import com.example.supremenews.asynctasks.AllNewsAsyncTask
import com.example.supremenews.models.News
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val ALL_NEWS_URL:String = "https://supremenews.herokuapp.com/api/news"
    private var mNews:MutableLiveData<List<News>> = MutableLiveData<List<News>>()

    fun getAllNews(loadingAnimation:LottieAnimationView):LiveData<List<News>>{
        if(mNews.value==null){
            mNews.value = ArrayList<News>()
            viewModelScope.launch(Dispatchers.IO){
                mNews.postValue(AllNewsAsyncTask(loadingAnimation).execute(ALL_NEWS_URL).get())
            }
        }
        return this.mNews
    }

    fun refresh(loadingAnimation: LottieAnimationView):LiveData<List<News>>{
        viewModelScope.launch(Dispatchers.IO){
            mNews.postValue(AllNewsAsyncTask(loadingAnimation).execute(ALL_NEWS_URL).get())
        }
        return mNews;
    }
}