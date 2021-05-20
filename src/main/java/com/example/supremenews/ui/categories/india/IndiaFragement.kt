package com.example.supremenews.ui.categories.india

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.lottie.LottieAnimationView
import com.example.supremenews.Adapters.HomeNewsAdapter
import com.example.supremenews.R
import com.example.supremenews.models.News
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

class IndiaFragement : Fragment() {

    private lateinit var viewModel: IndiaViewModel
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var india_recycler_view:RecyclerView
    private lateinit var adapter:HomeNewsAdapter
    private lateinit var loadingAnimation:LottieAnimationView
    private lateinit var mAdView:AdView
    private var mNews:LiveData<List<News>>?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root:View =  inflater.inflate(R.layout.fragment_india, container, false)

        india_recycler_view = root.findViewById(R.id.india_recycler_view)
        loadingAnimation = root.findViewById(R.id.i_loading_animation)
        refreshLayout = root.findViewById(R.id.refresh_layout)
        mAdView = root.findViewById(R.id.adView)
        return root;
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(requireActivity()).get(IndiaViewModel::class.java)

        india_recycler_view.layoutManager = LinearLayoutManager(activity)
        mNews = viewModel.getNews(loadingAnimation)
        adapter = HomeNewsAdapter(activity,mNews)
        india_recycler_view.adapter = adapter

        mNews!!.observe(requireActivity(), Observer<List<News>> {
                adapter.setmNews(mNews)
            })

        refreshLayout.setOnRefreshListener {
            println("refresh")
            refreshLayout.isRefreshing = false
            adapter!!.setmNews(viewModel.refresh(loadingAnimation))
        }

        val adRequest: AdRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

    }

}