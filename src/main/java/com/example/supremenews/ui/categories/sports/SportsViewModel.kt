package com.example.supremenews.ui.categories.sports

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.airbnb.lottie.LottieAnimationView
import com.example.supremenews.asynctasks.AllNewsAsyncTask
import com.example.supremenews.models.News
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SportsViewModel : ViewModel() {
    private val NEWS_URL:String = "https://supremenews.herokuapp.com/api/category/sports"
    private var mNews:MutableLiveData<List<News>> = MutableLiveData<List<News>>()

    fun getNews(loadingAnimation: LottieAnimationView):LiveData<List<News>>{
        if(mNews.value==null) {
            mNews.value = ArrayList<News>()
            viewModelScope.launch(Dispatchers.IO) {
                mNews.postValue(AllNewsAsyncTask(loadingAnimation).execute(NEWS_URL).get())
            }
        }
        return mNews
    }

    fun refresh(loadingAnimation: LottieAnimationView):LiveData<List<News>>{
        viewModelScope.launch(Dispatchers.IO){
            mNews.postValue(AllNewsAsyncTask(loadingAnimation).execute(NEWS_URL).get())
        }
        return mNews;
    }
}