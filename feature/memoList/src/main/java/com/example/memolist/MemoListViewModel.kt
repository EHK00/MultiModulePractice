package com.example.memolist

import androidx.lifecycle.viewModelScope
import com.example.common.BaseViewModel
import com.example.common.State
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
) : BaseViewModel<MemoListState>() {
    override val _stateFlow: MutableStateFlow<MemoListState> = MutableStateFlow(initialState)

    fun startLoad() {
        viewModelScope.launch {
            val result = getMemoListUc(Unit).getOrElse {
                val message = it.message ?: it.toString()
                _stateFlow.update {
                    it.copy(listState = MemoListState.ListState.OnError(message))
                }

                return@launch
            }
            _stateFlow.update {
                it.copy(listState = MemoListState.ListState.OnLoad(result.list))
            }
        }
    }
}

private val initialState: MemoListState = MemoListState(
    listState = MemoListState.ListState.None
)

data class MemoListState(
    val listState: ListState
) : State {
    sealed interface ListState {
        object None : ListState
        data class OnLoad(
            val itemList: List<ShortenMemo>
        ) : ListState

        data class OnError(
            val errorMessage: String
        ) : ListState
    }
}


