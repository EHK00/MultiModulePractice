package com.example.memolist

import androidx.lifecycle.viewModelScope
import com.example.Resource
import com.example.common.BaseViewModel
import com.example.common.State
import com.example.common.UiAction
import com.example.domain.GetMemoListUc
import com.example.model.ShortenMemo
import com.example.repository.MemoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemoListViewModel @Inject constructor(
    private val getMemoListUc: GetMemoListUc,
) : BaseViewModel<MemoListState, MemoListAction>() {
    override val _stateFlow: MutableStateFlow<MemoListState> = MutableStateFlow(initialState)

    init {
        viewModelScope.launch {
            val result = getMemoListUc(Unit)
            _stateFlow.update { state ->
                when (result) {
                    is Resource.Success -> {
                        val data = result.dataModel
                        state.copy(listState = ListState.OnLoad(data.list))
                    }
                    is Resource.Error -> {
                        val message = result.error?.message ?: result.error.toString()
                        state.copy(listState = ListState.OnError(message))
                    }
                }
            }
        }
    }
}

private val initialState: MemoListState = MemoListState(
    listState = ListState.None
)

data class MemoListState(
    val listState: ListState
) : State

sealed interface ListState {
    object None : ListState
    data class OnLoad(
        val itemList: List<ShortenMemo>
    ) : ListState

    data class OnError(
        val errorMessage: String
    ) : ListState
}

class MemoListAction : UiAction


