package com.solie.memo_example

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.solie.memo_example.databinding.DialogFixBinding
import com.solie.memo_example.util.FirebaseData
import com.solie.memo_example.util.currentTime

class FixDialog (private val content : String,
                private val YearInfo : String,
                private val MonthInfo : String,
                private val DateInfo : String,
                private val DataKey : String) : DialogFragment(), FirebaseData{
    private lateinit var binding : DialogFixBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogFixBinding.inflate(inflater, container, false)
        binding.fixEdt.setText(content)
        binding.fixBtn.setOnClickListener {
            upLoadFix()
            dialog!!.dismiss()
        }

        return binding.root

    }

    override fun onResume() {
        super.onResume()
        context?.dialogResize(this@FixDialog, 0.8f, 0.6f)
    }

    fun Context.dialogResize(dialogFragment : FixDialog, width : Float, height : Float) {
        val windowMananger = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        if(Build.VERSION.SDK_INT < 30) {
            val display = windowMananger.defaultDisplay
            val size = Point()
            display.getSize(size)

            val window = dialogFragment.dialog?.window

            val x = (size.x * width).toInt()
            val y = (size.y * height).toInt()
            window?.setLayout(x, y)
        } else {
            val rect = windowMananger.currentWindowMetrics.bounds
            val window = dialogFragment.dialog?.window

            val x = (rect.width() * width).toInt()
            val y = (rect.height() * height).toInt()

            window?.setLayout(x, y)
        }
    }

    private fun upLoadFix() {
        val memoData = mutableMapOf<String, Any>()
        memoData["Memo"] = binding.fixEdt.text.toString()
        memoData["Year"] = YearInfo
        memoData["Month"] = MonthInfo
        memoData["Date"] = DateInfo
        memoData["CurrentTime"] = currentTime.format(date)
        storageCheck
            .document(DataKey)
            .set(memoData)
    }
}