package com.example.data.local

import android.content.Context
import com.example.data.FileProvider
import com.example.model.Memo
import com.example.model.ShortenMemo
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class JsonMemoLocalDataSource @Inject constructor(
    private val fileProvider: FileProvider
) : MemoLocalDataSource {
    override fun getMemo(id: String): Memo? {
        val memoList = fileProvider.fromJson(JsonKey.GetMemoList.fileName, Array<Memo>::class.java)
            ?: return null
        return memoList.find { it.id == id }
    }

    override fun getShortenMemoList(): List<ShortenMemo> {
        val memoList = fileProvider.fromJson(JsonKey.GetMemoList.fileName, Array<Memo>::class.java)
            ?: return emptyList()
        return memoList.map { it.toShorten() }
    }

    override fun saveMemo(memo: Memo): Boolean {
        val memoMap = fileProvider.fromJson(JsonKey.GetMemoList.fileName, Array<Memo>::class.java)?.associateBy { it.id }
            ?.toMutableMap()
            ?: mutableMapOf()
        memoMap[memo.id] = memo
        val memoArray = memoMap.values.toTypedArray()
        fileProvider.saveAsJsonFile(JsonKey.GetMemoList.fileName, memoArray)
        return true
    }

    enum class JsonKey(val fileName: String) {
        GetMemoList("GetMemoList")
    }
}

private fun Memo.toShorten(): ShortenMemo {
    return ShortenMemo(
        id = id,
        simpleText = content
    )
}