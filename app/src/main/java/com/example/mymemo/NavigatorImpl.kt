package com.example.mymemo

import android.content.Context
import android.content.Intent
import com.example.common.Navigator
import com.example.creatememo.CreateMemoActivity
import com.example.memolist.MemoListActivity
import javax.inject.Inject

class NavigatorImpl @Inject constructor(): Navigator {
    override fun gotoCreateMemoScreen(context: Context, id: String?) {
        val intent = CreateMemoActivity.createIntent(context, CreateMemoActivity.Param(id))
        context.startActivity(intent)
    }

    override fun gotoMemoListScreen(context: Context) {
        val intent = Intent(context, MemoListActivity::class.java)
        context.startActivity(intent)
    }

}