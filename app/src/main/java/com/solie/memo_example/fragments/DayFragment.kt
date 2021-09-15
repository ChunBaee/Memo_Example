package com.solie.memo_example.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.solie.memo_example.R
import com.solie.memo_example.adapters.DayAdapter
import com.solie.memo_example.databinding.FragmentDayBinding
import com.solie.memo_example.items.DayItem
import com.solie.memo_example.items.JudgeItem
import com.solie.memo_example.util.FirebaseData
import com.solie.memo_example.util.RecyclerDecoration

class DayFragment : Fragment(), FirebaseData {

    private lateinit var binding : FragmentDayBinding
    private lateinit var YearInfo : String
    private lateinit var MonthInfo : String

    private var contentFragment = ContentFragment()
    private var listFragment = ListFragment()

    private val adapter = DayAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDayBinding.inflate(inflater, container, false)
        setBoard()


        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(arguments != null) {
            YearInfo = requireArguments().getString("Year").toString()
            MonthInfo = requireArguments().getString("Month").toString()
        }
    }

    private fun setBoard() {
        val list = mutableSetOf<DayItem>()
        list.clear()
        binding.dayRecycler.addItemDecoration(RecyclerDecoration(10,10,0,0))
        binding.dayRecycler.adapter = adapter
        binding.dayRecycler.setHasFixedSize(true)
        storageCheck.whereEqualTo("Year", YearInfo)
            .whereEqualTo("Month", MonthInfo)
            .get()
            .addOnCompleteListener { task ->
                for(document in task.result!!) {
                    list.add(document.toObject(DayItem::class.java))
                }
                adapter.setDay(list.toMutableList())
            }

        adapter.dayClickListener(object : DayAdapter.DayClickListener {
            override fun onClick(view: View, position: Int) {
                val newList = mutableSetOf<JudgeItem>()
                storageCheck.whereEqualTo("Year", YearInfo)
                    .whereEqualTo("Month", MonthInfo)
                    .whereEqualTo("Date",list.toMutableList()[position].Date)
                    .get()
                    .addOnCompleteListener { task ->
                        for(document in task.result!!) {
                            newList.add(document.toObject(JudgeItem::class.java))

                        }
                        val bundle = Bundle()
                        bundle.putString("Year", YearInfo)
                        bundle.putString("Month", MonthInfo)
                        bundle.putString("Date", newList.toMutableList()[position].Date)
                        bundle.putString("Memo", newList.toMutableList()[position].Memo)
                        if(newList.size == 1) {
                            contentFragment.arguments = bundle
                            requireActivity().supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.MainContainer, contentFragment).commit()
                        } else {
                            listFragment.arguments = bundle
                            requireActivity().supportFragmentManager.beginTransaction().addToBackStack(null).replace(
                                R.id.MainContainer, listFragment).commit()
                        }
                    }
            }
        })
    }
}