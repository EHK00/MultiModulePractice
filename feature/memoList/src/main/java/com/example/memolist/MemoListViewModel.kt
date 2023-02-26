package com.example.memolist

import androidx.lifecycle.ViewModel
import com.example.common.BaseViewModel
import com.example.common.State
import com.example.model.ShortenMemo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MemoListViewModel @Inject constructor() : BaseViewModel<MemoListState>() {
    override val _stateFlow: MutableStateFlow<MemoListState> = MutableStateFlow(None)

    fun startLoad() {
        _stateFlow.value = OnLoad(
            itemList = listOf()
        )
    }


}

sealed class MemoListState : State
object None : MemoListState()
data class OnLoad(
    val itemList: List<ShortenMemo>
) : MemoListState()
