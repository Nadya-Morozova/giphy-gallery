package com.example.giphyapp.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.giphyapp.data.GlobalGipHy
import com.example.giphyapp.data.Type
import com.example.giphyapp.databinding.ItemGifBinding
import com.example.giphyapp.databinding.ItemInnerGifBinding
import com.example.giphyapp.utils.BlockListGifs
import com.example.giphyapp.utils.OnGifClick
import com.example.giphyapp.utils.ViewUtils
import com.example.giphyapp.views.viewholders.GipHyViewHolder
import com.example.giphyapp.views.viewholders.InnerGifViewHolder

class GipHyAdapter(
    private val onGifClick: OnGifClick? = null,
    private val blockListGifs: BlockListGifs? = null,
    val item: Type
) : PagingDataAdapter<GlobalGipHy, RecyclerView.ViewHolder>(GipHyDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holder: RecyclerView.ViewHolder
        if (item == Type.LIST) {
            val binding = ItemGifBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            holder = GipHyViewHolder(binding)

            holder.itemView.setOnClickListener {
                onGifClick?.onGifClick(holder.positionForDetail)
            }

            binding.btnDelete.setOnClickListener {
                ViewUtils.showDialogForAddToBlockList(
                    binding.root.context,
                    holder.gipHy?.title,
                    removalProcess = {
                        blockListGifs?.addToBlockList(holder.gipHy?.id ?: "")
                        Glide.with(parent.context).clear(binding.imgGif)
                        holder.clear()
                        binding.tvTitle.text = "BLOCKED"
                        Toast.makeText(
                            binding.root.context,
                            "Gif was deleted from list!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                )
            }
        } else {
            val binding =
                ItemInnerGifBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            holder = InnerGifViewHolder(binding)
        }
        return holder
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is GipHyViewHolder) {
            val item = getItem(position) as GlobalGipHy
            holder.bind(item, position)
        } else if (holder is InnerGifViewHolder) {
            val item = getItem(position) as GlobalGipHy
            holder.bind(item)
        }
    }

    class GipHyDiffUtil : DiffUtil.ItemCallback<GlobalGipHy>() {
        override fun areItemsTheSame(oldItem: GlobalGipHy, newItem: GlobalGipHy): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GlobalGipHy, newItem: GlobalGipHy): Boolean {
            return oldItem == newItem
        }

    }
}