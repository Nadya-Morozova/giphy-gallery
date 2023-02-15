package com.example.giphyapp.views.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.giphyapp.R
import com.example.giphyapp.data.GlobalGipHy
import com.example.giphyapp.databinding.ItemInnerGifBinding

class InnerGifViewHolder(private val binding: ItemInnerGifBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(gipHy: GlobalGipHy) {
        binding.apply {
            Glide
                .with(imgInnerGif.context)
                .load(gipHy.url)
                .apply(
                    RequestOptions().placeholder(R.drawable.placeholder)
                )
                .into(imgInnerGif)
        }
    }
}