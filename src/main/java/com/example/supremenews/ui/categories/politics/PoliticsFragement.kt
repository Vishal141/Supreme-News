package com.example.supremenews.ui.categories.politics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.supremenews.Adapters.HomeNewsAdapter
import com.example.supremenews.R
import com.example.supremenews.models.News

class PoliticsFragement : Fragment() {

    private lateinit var viewModel: PoliticsViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var loadingAnimation:LottieAnimationView
    private var adapter:HomeNewsAdapter?=null

    private var mNews:LiveData<List<News>>?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_politics, container, false)

        recyclerView = root.findViewById(R.id.politics_recycler_view)
        loadingAnimation = root.findViewById(R.id.p_loading_animation)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel =
            ViewModelProviders.of(requireActivity()).get(PoliticsViewModel::class.java)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        mNews = viewModel.getNews(loadingAnimation)
        adapter = HomeNewsAdapter(activity,mNews)
        recyclerView.adapter = adapter

        mNews!!.observe(requireActivity(), Observer {
            adapter!!.setmNews(mNews)
        })
    }
}