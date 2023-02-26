package com.example.mymemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.memolist.MemoListActivity
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    fun startMemo() {
        startActivity(Intent(this, MemoListActivity::class.java))
    }
}