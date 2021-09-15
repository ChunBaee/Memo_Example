package com.solie.memo_example.util

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

interface FirebaseData {
    val currentUser : FirebaseUser?
        get() = FirebaseAuth.getInstance().currentUser

    val userID
        get() = currentUser!!.uid

    val storageCheck
        get() = FirebaseFirestore.getInstance().collection(userID)


    val date
        get() = Date(System.currentTimeMillis())

    val year
        get() = SimpleDateFormat("yyyy", Locale("ko", "KR"))

}   val month
        get() = SimpleDateFormat("MM", Locale("ko", "KR"))

    val dates
        get() = SimpleDateFormat("dd", Locale("ko", "KR"))

    val currentTime
        get() = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("ko", "KR"))