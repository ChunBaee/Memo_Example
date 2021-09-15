package com.solie.memo_example

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.solie.memo_example.items.ViewModelItem

class ViewModel : ViewModel() {
   private val item = ViewModelItem()
    val text = ObservableField(item.text)

    fun changeText(new : String) {
        text.set(new)
    }

}