package com.solie.memo_example

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.solie.memo_example.databinding.ActivityMainBinding
import com.solie.memo_example.fragments.YearFragment
import com.solie.memo_example.util.FirebaseData
import com.solie.memo_example.util.NetworkStatus

class MainActivity : AppCompatActivity(), FirebaseData {

    private lateinit var binding : ActivityMainBinding
    private var yearFragment = YearFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        networkCheck()
        setToolbar()
        setFragment()
    }

    private fun networkCheck() {
        val networkStatus = NetworkStatus(applicationContext).checkNetworkStatus()
        if(!networkStatus) {
            Toast.makeText(applicationContext, "인터넷에 연결되지 않았습니다.", Toast.LENGTH_LONG).show()
            finish()
        } else {
            userAuthCheck()
        }
    }

    private fun userAuthCheck() {
        if(currentUser == null) {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        }
    }
    private fun setToolbar() {
        setSupportActionBar(binding.MainToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowCustomEnabled(true)
    }

    private fun setFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.MainContainer, yearFragment).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.Write_Btn -> {
                Toast.makeText(applicationContext, "작성버튼", Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext, WriteActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}