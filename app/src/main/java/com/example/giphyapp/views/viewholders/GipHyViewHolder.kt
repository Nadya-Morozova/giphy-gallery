package com.example.giphyapp.views.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.giphyapp.data.GlobalGipHy
import com.example.giphyapp.databinding.ItemGifBinding

class GipHyViewHolder(private val binding: ItemGifBinding) : RecyclerView.ViewHolder(binding.root) {

    var positionForDetail = 0
    var gipHy: GlobalGipHy? = null

    fun bind(gipHy: GlobalGipHy, position: Int) {
        positionForDetail = position
        this.gipHy = gipHy
        binding.apply {
            Glide
                .with(imgGif.context)
                .load(gipHy.url)
                .apply(
                    RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .into(imgGif)

            tvTitle.text = gipHy.title
        }
    }

    fun clear() {
        gipHy?.url = ""
        gipHy?.title = "BLOCKED"
    }
}