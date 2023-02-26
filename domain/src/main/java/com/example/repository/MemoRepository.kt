package com.example.repository

import com.example.model.Memo

interface MemoRepository {
    fun getMemo(): Memo
}