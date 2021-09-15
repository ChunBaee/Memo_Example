package com.solie.memo_example.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.solie.memo_example.R
import com.solie.memo_example.databinding.ItemYearFragmentBinding
import com.solie.memo_example.items.YearItem

class YearAdapter : RecyclerView.Adapter<YearAdapter.YearViewHolder>() {
    private var data = mutableListOf<YearItem>()
    private lateinit var yearClickListener : YearClickListener

    interface YearClickListener {
        fun onClick (view : View, position : Int)
    }

    fun yearClickListener (yearClickListener : YearClickListener) {
        this.yearClickListener = yearClickListener
    }

    class YearViewHolder(val binding : ItemYearFragmentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (yearItem: YearItem) {
            binding.selectYear.text = yearItem.Year
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YearAdapter.YearViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemYearFragmentBinding>(layoutInflater, R.layout.item_year_fragment, parent, false)

        return YearViewHolder(binding)
    }

    override fun onBindViewHolder(holder: YearAdapter.YearViewHolder, position: Int) {
        holder.bind(data[position])
        holder.itemView.setOnClickListener {
            yearClickListener.onClick(it, position)
        }
        holder.binding.selectYear.text = data[position].Year
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setYear (list : MutableList<YearItem>) {
        data = list.toMutableList()
        notifyDataSetChanged()
    }
}