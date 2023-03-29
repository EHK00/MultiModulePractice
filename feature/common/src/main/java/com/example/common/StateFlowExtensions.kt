package com.example.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.map

fun <T, K> Flow<T>.distinctAndMap(keySelector: (T)->K) : Flow<K> {
    return this.distinctUntilChangedBy(keySelector).map(keySelector)
}
