package com.example.memolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.memolist.databinding.ItemMemoBinding
import com.example.model.ShortenMemo

class MemoListAdapter(
    private val onClick: (ShortenMemo) -> Unit
) : ListAdapter<ShortenMemo, MemoListAdapter.ViewHolder>(itemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemMemoBinding.inflate(LayoutInflater.from(parent.context)), onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onViewRecycled(holder: ViewHolder) {
        holder.recycle()
    }

    class ViewHolder(
        private val binding: ItemMemoBinding,
        private val onClick: (ShortenMemo) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private var item: ShortenMemo? = null

        init {
            binding.tvText.setOnClickListener {
                item?.let { onClick.invoke(it) }
            }
        }

        fun bind(item: ShortenMemo) {
            this.item = item
            binding.tvText.text = item.simpleText
        }

        fun recycle() {
            item = null
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


