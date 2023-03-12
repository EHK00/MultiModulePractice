package com.example.mymemo.data

import com.example.data.local.MemoLocalDataSource
import com.example.model.Memo
import com.example.model.ShortenMemo
import javax.inject.Inject

class MemoLocalDataSourceImpl @Inject constructor(
    private val pref: MyMemoSharedPref
) : MemoLocalDataSource {

    override fun getMemo(id: String): Memo? {
        val memoList = pref.getParcelable<List<Memo>>(MyMemoSharedPref.PrefKey.MemoList)
        return memoList?.find { it.id == id }
    }

    override fun getShortenMemoList(): List<ShortenMemo> {
        return pref.getParcelable<List<Memo>>(MyMemoSharedPref.PrefKey.MemoList)
            ?.map { it.toShorten() }
            ?: emptyList()
    }

    override fun saveMemo(memo: Memo): Boolean {
        val memoList = pref.getParcelable<List<Memo>>(MyMemoSharedPref.PrefKey.MemoList)
            ?.toMutableList()
            ?: mutableListOf()
        memoList.add(memo)
        return try {
            pref.setParcelableCollection(MyMemoSharedPref.PrefKey.MemoList, memoList)
            true
        } catch (e: Exception) {
            false
        }

    }
}

private fun Memo.toShorten(): ShortenMemo {
    return ShortenMemo(
        id = id,
        simpleText = content
    )
}