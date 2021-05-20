package com.example.supremenews.ui.home

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.lottie.LottieAnimationView

import com.example.supremenews.Adapters.HomeNewsAdapter
import com.example.supremenews.R
import com.example.supremenews.models.News
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var homeNewsRecyclerView: RecyclerView
    private lateinit var homeNewsAdapter:HomeNewsAdapter
    private lateinit var mAdView:AdView
    private lateinit var loadingAnimation:LottieAnimationView

    private var mNews:LiveData<List<News>>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(requireActivity()).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        homeNewsRecyclerView = root.findViewById(R.id.home_recycler_view);
        loadingAnimation = root.findViewById(R.id.h_loading_animation);
        refreshLayout = root.findViewById(R.id.refresh_layout)
        mAdView = root.findViewById(R.id.adView)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        homeNewsRecyclerView.layoutManager = LinearLayoutManager(activity)
        mNews = homeViewModel.getAllNews(loadingAnimation)
        homeNewsAdapter = HomeNewsAdapter(activity,mNews)
        homeNewsRecyclerView.adapter = homeNewsAdapter
        mNews!!.observe(requireActivity(), Observer {
            homeNewsAdapter.setmNews(mNews)
            setNewsCount(mNews!!.value!!.size)
        })

        refreshLayout.setOnRefreshListener {
            println("refresh")
            refreshLayout.isRefreshing = false
            val tempNews:LiveData<List<News>> = homeViewModel.refresh(loadingAnimation)
            homeNewsAdapter.setmNews(tempNews)
            setNewsCount(tempNews.value!!.size)
        }

        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }

    private fun setNewsCount(count:Int){
        val sp = requireActivity().getSharedPreferences("MY_PREP",MODE_PRIVATE)
        sp.edit().putInt("NEWS_COUNT",count).apply()
    }
}
