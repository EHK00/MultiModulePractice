package com.example.domain

import com.example.model.Memo
import com.example.repository.MemoRepository
import kotlinx.coroutines.CoroutineDispatcher
import java.security.InvalidParameterException
import javax.inject.Inject

class GetMemoUc @Inject constructor(
    private val memoRepository: MemoRepository,
    dispatcher: CoroutineDispatcher,
) : UseCase<GetMemoUc.Param, GetMemoUc.ResultData>(dispatcher) {

    override suspend fun execute(parameters: Param): ResultData {
        val memo = memoRepository.getMemo(parameters.id)
            ?: throw InvalidParameterException("There is no memo that correspond input id")
        return ResultData(memo)
    }

    data class Param(
        val id: String,
    )

    data class ResultData(
        val memo: Memo
    )
}