package com.example.domain

import com.example.model.ShortenMemo
import com.example.repository.MemoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import java.util.PrimitiveIterator
import javax.inject.Inject

class GetMemoListUc @Inject constructor(
    private val memoRepository: MemoRepository,
    dispatcher: CoroutineDispatcher,
) : UseCase<Unit, GetMemoListUc.ResultData>(dispatcher) {

    override suspend fun execute(parameters: Unit): ResultData {
        val list = memoRepository.getShortenMemoList()
        return ResultData(list)
    }

    data class ResultData(
        val list: List<ShortenMemo>
    )
}