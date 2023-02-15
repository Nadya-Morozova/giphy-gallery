package com.example.giphyapp.views.adapters.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.giphyapp.network.data.Response
import com.example.giphyapp.views.adapters.GipHyAdapter


//@BindingAdapter("observeList")
//fun RecyclerView.observeList(list: List<Response>?) {
//    if (list != null) {
//        this.layoutManager =
//            StaggeredGridLayoutManager(
//                2,
//                StaggeredGridLayoutManager.VERTICAL
//            )

//        this.layoutManager = GridLayoutManager(this.context, 2)
//        this.adapter = GipHyAdapter(list)
//    }
//}