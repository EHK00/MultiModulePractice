package com.example.repository

import com.example.model.Memo
import com.example.model.ShortenMemo

interface MemoRepository {
    fun getMemo(id: String): Memo?

    fun getShortenMemoList(): List<ShortenMemo>

    fun saveMemo(memo:Memo): Boolean
}