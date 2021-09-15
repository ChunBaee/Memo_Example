package com.solie.memo_example.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.solie.memo_example.R
import com.solie.memo_example.adapters.ListAdapter
import com.solie.memo_example.databinding.FragmentContentListBinding
import com.solie.memo_example.items.ListItem
import com.solie.memo_example.util.FirebaseData
import com.solie.memo_example.util.RecyclerDecoration

class ListFragment : Fragment(), FirebaseData {

    private lateinit var binding : FragmentContentListBinding

    private val adapter = ListAdapter()

    private lateinit var YearInfo : String
    private lateinit var MonthInfo : String
    private lateinit var DateInfo : String

    private var contentFragment = ContentFragment()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentContentListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if(arguments != null) {
            YearInfo = requireArguments().getString("Year").toString()
            MonthInfo = requireArguments().getString("Month").toString()
            DateInfo = requireArguments().getString("Date").toString()
        }
        setBoard()
    }

    private fun setBoard() {
        val list = mutableListOf<ListItem>()
        binding.contentList.addItemDecoration(RecyclerDecoration(10,10,0,0))
        binding.contentList.adapter = adapter
        binding.contentList.setHasFixedSize(true)

        storageCheck.whereEqualTo("Year", YearInfo)
            .whereEqualTo("Month",MonthInfo)
            .whereEqualTo("Date",DateInfo)
            .get()
            .addOnCompleteListener { task ->
                for(document in task.result!!) {
                    list.add(document.toObject(ListItem::class.java))
                }
                adapter.setContent(list)

                adapter.listClickListener(object : ListAdapter.ListClickListener {
                    override fun onClick(view: View, position: Int) {
                        val bundle = Bundle()
                        bundle.putString("Year", YearInfo)
                        bundle.putString("Month", MonthInfo)
                        bundle.putString("Date", DateInfo)
                        bundle.putString("Memo", list[position].Memo)
                        contentFragment.arguments = bundle
                        requireActivity().supportFragmentManager.beginTransaction().addToBackStack(null).replace(
                            R.id.MainContainer, contentFragment).commit()
                    }

                })
            }
    }

}