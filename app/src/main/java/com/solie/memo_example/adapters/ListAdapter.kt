package com.solie.memo_example.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.solie.memo_example.R
import com.solie.memo_example.databinding.ItemContentListBinding
import com.solie.memo_example.items.ListItem

class ListAdapter : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {
    private var data = mutableListOf<ListItem>()
    private lateinit var listClickListener : ListClickListener

    interface ListClickListener {
        fun onClick (view : View, position : Int)
    }

    fun listClickListener (listClickListener : ListClickListener) {
        this.listClickListener = listClickListener
    }

    class ListViewHolder(val binding : ItemContentListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (listItem: ListItem) {
            binding.itemListTxt.text = listItem.Memo
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.ListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemContentListBinding>(layoutInflater, R.layout.item_content_list, parent, false)

        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListAdapter.ListViewHolder, position: Int) {
        holder.bind(data[position])
        holder.itemView.setOnClickListener {
            listClickListener.onClick(it, position)
        }
        holder.binding.itemListTxt.text = data[position].Memo
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setContent (list : MutableList<ListItem>) {
        data = list.toMutableList()
        notifyDataSetChanged()
    }
}