package com.example.data.repository

import com.example.data.local.MemoLocalDataSource
import com.example.model.Memo
import com.example.model.ShortenMemo
import com.example.repository.MemoRepository
import javax.inject.Inject

class MemoRepositoryImpl @Inject constructor(
    private val disk: MemoLocalDataSource
) : MemoRepository {
    override suspend fun getMemo(id: String): Memo? {
        return disk.getMemo(id)
    }

    override suspend fun getShortenMemoList(): List<ShortenMemo> {
        return disk.getShortenMemoList()
    }

    override suspend fun saveMemo(memo: Memo): Boolean {
        return disk.saveMemo(memo)
    }
}