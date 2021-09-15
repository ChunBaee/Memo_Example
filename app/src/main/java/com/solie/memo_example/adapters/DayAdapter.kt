package com.solie.memo_example.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.solie.memo_example.R
import com.solie.memo_example.databinding.ItemDayFragmentBinding
import com.solie.memo_example.items.DayItem

class DayAdapter : RecyclerView.Adapter<DayAdapter.DayViewHolder>() {
    private var data = mutableListOf<DayItem>()
    private lateinit var dayClickListener : DayClickListener

    interface DayClickListener {
        fun onClick (view : View, position : Int)
    }

    fun dayClickListener (dayClickListener : DayClickListener) {
        this.dayClickListener = dayClickListener
    }

    class DayViewHolder(val binding : ItemDayFragmentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (dayItem: DayItem) {
            binding.selectDay.text = dayItem.Date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayAdapter.DayViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemDayFragmentBinding>(layoutInflater, R.layout.item_day_fragment, parent, false)

        return DayViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DayAdapter.DayViewHolder, position: Int) {
        holder.bind(data[position])
        holder.itemView.setOnClickListener {
            dayClickListener.onClick(it, position)
        }
        holder.binding.selectDay.text = data[position].Date
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setDay (list : MutableList<DayItem>) {
        data = list.toMutableList()
        notifyDataSetChanged()
    }
}