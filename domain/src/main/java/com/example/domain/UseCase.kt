package com.example.domain

import com.example.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber

abstract class UseCase<in P, out R>(private val dispatcher: CoroutineDispatcher) {
    suspend operator fun invoke(parameters: P): Resource<R> {
        return try {
            withContext(dispatcher) {
                execute(parameters).let {
                    Resource.Success(it)
                }
            }
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: P): R
}


