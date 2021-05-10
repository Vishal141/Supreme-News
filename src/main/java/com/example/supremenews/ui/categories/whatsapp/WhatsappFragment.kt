package com.example.supremenews.ui.categories.whatsapp

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.supremenews.Adapters.HomeNewsAdapter
import com.example.supremenews.R
import com.example.supremenews.models.News

class WhatsappFragment : Fragment() {

    private lateinit var viewModel: WhatsappViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var loadingAnimation: LottieAnimationView
    private var adapter: HomeNewsAdapter?=null

    private var mNews: LiveData<List<News>>?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.whatsapp_fragment, container, false)

        recyclerView = root.findViewById(R.id.whatsapp_recycler_view)
        loadingAnimation = root.findViewById(R.id.w_loading_animation)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel =
            ViewModelProviders.of(requireActivity()).get(WhatsappViewModel::class.java)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        mNews = viewModel.getNews(loadingAnimation)
        adapter = HomeNewsAdapter(activity,mNews)
        recyclerView.adapter = adapter

        mNews!!.observe(requireActivity(), Observer {
            adapter!!.setmNews(mNews)
        })
    }

}