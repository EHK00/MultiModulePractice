package com.example.data.repository

import com.example.data.local.MemoLocalDataSource
import com.example.model.Memo
import com.example.model.ShortenMemo
import com.example.repository.MemoRepository
import javax.inject.Inject

class MemoRepositoryImpl @Inject constructor(
    private val disk: MemoLocalDataSource
) : MemoRepository {
    override fun getMemo(id: String): Memo? {
        return disk.getMemo(id)
    }

    override fun getShortenMemoList(): List<ShortenMemo> {
        return disk.getShortenMemoList()
    }
}