package com.branovitski.chililab.ui.gif_list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.branovitski.chililab.databinding.ListItemBinding
import com.bumptech.glide.Glide

class GifsAdapter : PagingDataAdapter<GifListItem, GifsAdapter.GifViewHolder>(GifsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = ListItemBinding.inflate(inflater, parent, false)
        return GifViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class GifViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val context: Context
            get() = binding.root.context

        fun bind(item: GifListItem) {
            bindUrl(item.url)
            bindTitle(item.title)
        }

        private fun bindUrl(url: String) {
            Glide.with(context).asGif().load(url).into(binding.gifImageView)
        }

        private fun bindTitle(title: String?) {
           binding.titleTextView.text = title
        }

    }
}

private class GifsDiffCallback : DiffUtil.ItemCallback<GifListItem>() {
    override fun areItemsTheSame(oldItem: GifListItem, newItem: GifListItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GifListItem, newItem: GifListItem): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: GifListItem, newItem: GifListItem): Any {
        val result = mutableMapOf<String, Any?>()
        if (oldItem.url != newItem.url) result[PAYLOAD_URL] = newItem.url
        if (oldItem.title != newItem.title) result[PAYLOAD_TITLE] = newItem.title
        return result
    }

    companion object {
        const val PAYLOAD_TITLE = "payload_title"
        const val PAYLOAD_URL = "payload_url"
    }
}
