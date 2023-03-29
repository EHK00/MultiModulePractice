package com.example.data.local

import com.example.data.FileProvider
import com.example.model.Memo
import com.example.model.ShortenMemo
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

private val LOCAL_DATA_MUTEX: Mutex = Mutex()

class JsonMemoLocalDataSource @Inject constructor(
    private val fileProvider: FileProvider
) : MemoLocalDataSource {


    override suspend fun getMemo(id: String): Memo? = LOCAL_DATA_MUTEX.withLock {
        val memoList = fileProvider.fromJson(JsonKey.GetMemoList.fileName, Array<Memo>::class.java)
            ?: return null
        return memoList.find { it.id == id }
    }

    override suspend fun getShortenMemoList(): List<ShortenMemo> = LOCAL_DATA_MUTEX.withLock {
        val memoList = fileProvider.fromJson(JsonKey.GetMemoList.fileName, Array<Memo>::class.java)
            ?: return emptyList()
        return memoList.map { it.toShorten() }
    }

    override suspend fun saveMemo(memo: Memo): Boolean = LOCAL_DATA_MUTEX.withLock {
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