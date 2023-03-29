package com.example.repository

import com.example.model.Memo
import com.example.model.ShortenMemo

interface MemoRepository {
    suspend fun getMemo(id: String): Memo?

    suspend fun getShortenMemoList(): List<ShortenMemo>

    suspend fun saveMemo(memo:Memo): Boolean
}