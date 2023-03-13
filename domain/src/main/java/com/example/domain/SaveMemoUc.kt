package com.example.domain

import com.example.model.Memo
import com.example.repository.MemoRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SaveMemoUc @Inject constructor(
    private val memoRepository: MemoRepository,
    dispatcher: CoroutineDispatcher,
) : UseCase<SaveMemoUc.Param, Boolean>(dispatcher) {
    override suspend fun execute(parameters: Param): Boolean {
        return memoRepository.saveMemo(memo = parameters.memo)
    }

    data class Param(
        val memo: Memo,
    )
}