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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MemoListViewModel @Inject constructor(
    private val getMemoListUc: GetMemoListUc,
) : BaseViewModel<MemoListState, MemoListAction>() {
    override val _stateFlow: MutableStateFlow<MemoListState> = MutableStateFlow(initialState)

    init {
        observeUiAction()
    }

    private fun observeUiAction() = viewModelScope.launch {
        _uiActionFlow.collectLatest { action ->
            when (action) {
                MemoListAction.LoadMemoList -> {
                    _stateFlow.update { loadMemoList(it) }
                }
            }
        }
    }

    fun uiAction(action: MemoListAction) {
        viewModelScope.launch {
            _uiActionFlow.emit(action)
        }
    }

    private suspend fun loadMemoList(state: MemoListState): MemoListState {
        return when (val result = getMemoListUc(Unit)) {
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

internal sealed interface MemoListAction : UiAction {
    object LoadMemoList : MemoListAction
}


