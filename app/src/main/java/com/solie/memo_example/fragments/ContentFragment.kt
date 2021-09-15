package com.solie.memo_example.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.solie.memo_example.FixDialog
import com.solie.memo_example.databinding.FragmentContentBinding
import com.solie.memo_example.items.ContentItem
import com.solie.memo_example.util.FirebaseData

class ContentFragment : Fragment(), FirebaseData{

    private lateinit var binding : FragmentContentBinding

    private lateinit var YearInfo : String
    private lateinit var MonthInfo : String
    private lateinit var DateInfo : String
    private lateinit var Memo : String

    private var key : String = ""
    private var list = mutableListOf<ContentItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentContentBinding.inflate(inflater, container, false)

        binding.btnFix.setOnClickListener {
            Log.d("TAG", "수정버튼눌림")
            getDialog()
        }
        binding.btnDelete.setOnClickListener {
            deleteMemo()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if(arguments != null) {
            YearInfo = requireArguments().getString("Year").toString()
            MonthInfo = requireArguments().getString("Month").toString()
            DateInfo = requireArguments().getString("Date").toString()
            Memo = requireArguments().getString("Memo").toString()
        }
        list.clear()
        storageCheck.whereEqualTo("Year", YearInfo)
            .whereEqualTo("Month", MonthInfo)
            .whereEqualTo("Date",DateInfo)
            .whereEqualTo("Memo", Memo)
            .get()
            .addOnCompleteListener { task ->
                for(document in task.result!!) {
                    list.add(document.toObject(ContentItem::class.java))
                    key = document.id
                }
                binding.ContentTxt.text = list[0].Memo
                binding.TimeTxt.text = list[0].CurrentTime
            }
    }

    private fun getDialog() {
        Log.d("TAG","다이얼로그 가자")
       val dialog = FixDialog(binding.ContentTxt.text.toString(), YearInfo, MonthInfo, DateInfo, key)
        dialog.show(requireActivity().supportFragmentManager, "FixDialog")
    }

    private fun deleteMemo() {
        storageCheck.document(key)
            .delete()
            .addOnSuccessListener { task ->
                Toast.makeText(requireContext(), "메모 삭제 완료", Toast.LENGTH_SHORT).show()
            }
    }
}