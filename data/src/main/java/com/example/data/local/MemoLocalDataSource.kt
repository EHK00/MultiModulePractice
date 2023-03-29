package com.example.data.local

import com.example.model.Memo
import com.example.model.ShortenMemo

interface MemoLocalDataSource {
    suspend fun getMemo(id: String): Memo?

    suspend fun getShortenMemoList(): List<ShortenMemo>

    suspend fun saveMemo(memo: Memo): Boolean
}