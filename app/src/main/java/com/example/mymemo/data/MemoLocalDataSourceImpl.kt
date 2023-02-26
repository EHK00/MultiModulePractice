package com.example.mymemo.data

import com.example.data.local.MemoLocalDataSource
import com.example.model.Memo
import com.example.model.ShortenMemo
import javax.inject.Inject

class MemoLocalDataSourceImpl @Inject constructor(
    private val pref: MyMemoSharedPref
) : MemoLocalDataSource {

    override fun getMemo(id: String): Memo? {
        return null
//        val list = getShortenMemoList()
//        return list.find { it.id == id }
    }

    override fun getShortenMemoList(): List<ShortenMemo> {
        return pref.getParcelable<List<ShortenMemo>>(MyMemoSharedPref.PrefKey.MemoList)
            ?: emptyList()
    }
}