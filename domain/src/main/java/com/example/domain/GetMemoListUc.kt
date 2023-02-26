package com.example.domain

import com.example.model.ShortenMemo
import com.example.repository.MemoRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class GetMemoListUc @Inject constructor(
    private val memoRepository: MemoRepository
) : UseCase<Unit, GetMemoListUc.ResultData>(Dispatchers.IO) {

    override suspend fun execute(parameters: Unit): ResultData {
        val list = memoRepository.getShortenMemoList()
        return ResultData(list)
    }

    data class ResultData(
        val list: List<ShortenMemo>
    )
}