package com.example.data.local

import com.example.model.Memo
import com.example.model.ShortenMemo

interface MemoLocalDataSource {
    fun getMemo(id: String): Memo?

    fun getShortenMemoList(): List<ShortenMemo>
}