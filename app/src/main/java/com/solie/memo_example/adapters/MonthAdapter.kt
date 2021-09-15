package com.solie.memo_example.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.solie.memo_example.R
import com.solie.memo_example.databinding.ItemMonthFragmentBinding
import com.solie.memo_example.items.MonthItem

class MonthAdapter : RecyclerView.Adapter<MonthAdapter.MonthViewHolder>() {
    private var data = mutableListOf<MonthItem>()
    private lateinit var monthClickListener : MonthClickListener

    interface MonthClickListener {
        fun onClick (view : View, position : Int)
    }

    fun monthClickListener (monthClickListener : MonthClickListener) {
        this.monthClickListener = monthClickListener
    }

    class MonthViewHolder(val binding : ItemMonthFragmentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (monthItem: MonthItem) {
            binding.selectMonth.text = monthItem.Month
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthAdapter.MonthViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemMonthFragmentBinding>(layoutInflater, R.layout.item_month_fragment, parent, false)

        return MonthViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MonthAdapter.MonthViewHolder, position: Int) {
        holder.bind(data[position])
        holder.itemView.setOnClickListener {
            monthClickListener.onClick(it, position)
        }
        holder.binding.selectMonth.text = data[position].Month
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setMonth (list : MutableList<MonthItem>) {
        data = list.toMutableList()
        notifyDataSetChanged()
    }
}