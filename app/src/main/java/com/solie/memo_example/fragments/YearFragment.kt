package com.solie.memo_example.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.solie.memo_example.R
import com.solie.memo_example.adapters.YearAdapter
import com.solie.memo_example.databinding.FragmentYearBinding
import com.solie.memo_example.items.YearItem
import com.solie.memo_example.util.FirebaseData
import com.solie.memo_example.util.RecyclerDecoration

class YearFragment : Fragment(), FirebaseData {

    private lateinit var binding: FragmentYearBinding

    private val nothingFragment = NothingFragment()
    private val monthFragment = MonthFragment()

    private val adapter = YearAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentYearBinding.inflate(inflater, container, false)

        setBoard()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setBoard()
    }

    private fun setBoard() {
        binding.yearRecycler.addItemDecoration(RecyclerDecoration(10, 10, 0, 0))
        binding.yearRecycler.adapter = adapter
        binding.yearRecycler.setHasFixedSize(true)

        storageCheck.document(userID)
            .get()
            .addOnSuccessListener { task ->
                if (task.exists()) {
                    Log.d("TAG", "메모 있음")
                    getMemo()
                } else {
                    Log.d("TAG", "메모 없음")
                    requireActivity().supportFragmentManager.beginTransaction().addToBackStack(null)
                        .replace(R.id.MainContainer, nothingFragment).commit()
                }
            }
    }

    private fun getMemo() {
        val list = mutableSetOf<YearItem>()
        storageCheck.whereGreaterThan("Year", "1950")
            .get()
            .addOnCompleteListener(OnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        list.add(document.toObject(YearItem::class.java))
                    }
                    adapter.setYear(list.toMutableList())
                    adapter.yearClickListener(object : YearAdapter.YearClickListener {
                        override fun onClick(view: View, position: Int) {
                            var bundle = Bundle()
                            bundle.putString("Year", list.toMutableList()[position].Year)
                            monthFragment.arguments = bundle
                            requireActivity().supportFragmentManager.beginTransaction().addToBackStack(null)
                                .replace(R.id.MainContainer, monthFragment).commit()

                        }

                    })
                }
            })
    }
}

