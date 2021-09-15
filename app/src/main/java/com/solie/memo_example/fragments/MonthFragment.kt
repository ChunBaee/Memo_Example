package com.solie.memo_example.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.solie.memo_example.R
import com.solie.memo_example.adapters.MonthAdapter
import com.solie.memo_example.databinding.FragmentMonthBinding
import com.solie.memo_example.items.MonthItem
import com.solie.memo_example.util.FirebaseData
import com.solie.memo_example.util.RecyclerDecoration

class MonthFragment : Fragment(), FirebaseData {

    private lateinit var binding: FragmentMonthBinding
    private lateinit var YearInfo: String

    private var dayFragment = DayFragment()

    private val adapter = MonthAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMonthBinding.inflate(inflater, container, false)
        setBoard()


        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (arguments != null) {
            YearInfo = requireArguments().getString("Year").toString()
        }
    }

    private fun setBoard() {
        val list = mutableSetOf<MonthItem>()
        binding.monthRecycler.addItemDecoration(RecyclerDecoration(10, 10, 0, 0))
        binding.monthRecycler.adapter = adapter
        binding.monthRecycler.setHasFixedSize(true)

        storageCheck.whereEqualTo("Year", YearInfo)
            .whereGreaterThan("Month", "0")
            .get()
            .addOnCompleteListener { task ->
                for (document in task.result!!) {
                    list.add(document.toObject(MonthItem::class.java))
                }
                adapter.setMonth(list.toMutableList())
            }
        adapter.monthClickListener(object : MonthAdapter.MonthClickListener {
            override fun onClick(view: View, position: Int) {
                val bundle = Bundle()
                bundle.putString("Year", YearInfo)
                bundle.putString("Month", list.toMutableList()[position].Month)
                dayFragment.arguments = bundle
                requireActivity().supportFragmentManager.beginTransaction().addToBackStack(null)
                    .replace(R.id.MainContainer, dayFragment).commit()

            }
        })
    }
}