package com.example.memolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.memolist.databinding.ItemMemoBinding
import com.example.model.ShortenMemo

class MemoListAdapter : ListAdapter<ShortenMemo, MemoListAdapter.ViewHolder>(itemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemMemoBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ItemMemoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ShortenMemo) {
            binding.tvText.text = item.simpleText
        }
    }
}

private val itemCallback = object : DiffUtil.ItemCallback<ShortenMemo>() {
    override fun areItemsTheSame(oldItem: ShortenMemo, newItem: ShortenMemo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ShortenMemo, newItem: ShortenMemo): Boolean {
        return oldItem == newItem
    }

}


