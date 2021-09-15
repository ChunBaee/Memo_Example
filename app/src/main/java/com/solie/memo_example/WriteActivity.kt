package com.solie.memo_example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.solie.memo_example.databinding.ActivityWriteBinding
import com.solie.memo_example.util.FirebaseData
import com.solie.memo_example.util.currentTime
import com.solie.memo_example.util.dates
import com.solie.memo_example.util.month

class WriteActivity : AppCompatActivity(), FirebaseData, View.OnClickListener {

    private lateinit var binding : ActivityWriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_write)

        binding.btnSave.setOnClickListener(this)
        binding.btnCancel.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.btn_cancel -> {
                Toast.makeText(applicationContext, "메모 작성 취소", Toast.LENGTH_LONG).show()
                finish()
            }

            R.id.btn_save -> {
                val memoData = mutableMapOf<String, Any>()
                memoData["Memo"] = binding.WriteEdt.text.toString()
                memoData["Year"] = year.format(date)
                memoData["Month"] = month.format(date)
                memoData["Date"] = dates.format(date)
                memoData["CurrentTime"] = currentTime.format(date)

                val loginCheck = mutableMapOf<String, Any>()
                loginCheck["Login"] = "Success"
                storageCheck.document(userID).set(loginCheck)

                storageCheck
                    .document()
                    .set(memoData)
                    .addOnSuccessListener {
                        Toast.makeText(applicationContext, "저장 성공", Toast.LENGTH_SHORT).show()
                        finish()
                    }
            }
        }
    }
}