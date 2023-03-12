package com.example.creatememo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.Resource
import com.example.common.BaseViewModel
import com.example.common.State
import com.example.common.UiAction
import com.example.domain.GetMemoListUc
import com.example.domain.GetMemoUc
import com.example.domain.SaveMemoUc
import com.example.model.Memo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

internal const val EXTRA_MEMO_ID = "EXTRA_MEMO_ID"

@HiltViewModel
internal class CreateMemoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val saveMemoUc: SaveMemoUc,
    private val getMemoUc: GetMemoUc,
) : BaseViewModel<CreateMemoState, CreateMemoUiAction>() {
    override val _stateFlow: MutableStateFlow<CreateMemoState> = MutableStateFlow(CreateMemoState.initial)

    init {
        savedStateHandle.get<String?>(EXTRA_MEMO_ID)?.let {
            viewModelScope.launch {
                _stateFlow.value = getMemo(it)
            }
        }
        observeUiAction()
    }

    fun uiAction(action: CreateMemoUiAction) {
        viewModelScope.launch {
            _uiActionFlow.emit(action)
        }
    }

    private fun observeUiAction() = viewModelScope.launch {
        _uiActionFlow.collectLatest { action ->
            _stateFlow.update { state ->
                when (action) {
                    is CreateMemoUiAction.InputContentText -> {
                        state.copy(content = action.text)
                    }
                    is CreateMemoUiAction.InputTitleText -> {
                        state.copy(subject = action.text)
                    }
                }
            }
        }
    }


    private suspend fun getMemo(id: String): CreateMemoState {
        return when (val result = getMemoUc(GetMemoUc.Param(id))) {
            is Resource.Success -> {
                val data = result.dataModel
                CreateMemoState(
                    subject = data.memo.subject,
                    content = data.memo.content,
                    errorText = null,
                    isLoading = false,
                )
            }
            is Resource.Error -> {
                CreateMemoState(
                    subject = "",
                    content = "",
                    errorText = result.error.toString(),
                    isLoading = false,
                )
            }
        }
    }


}

internal data class CreateMemoState(
    val subject: String,
    val content: String,
    val errorText: String?,
    val isLoading: Boolean,
) : State {
    companion object {
        val initial: CreateMemoState = CreateMemoState(
            "", "", null, true
        )
    }

}

internal sealed interface CreateMemoUiAction : UiAction {
    data class InputTitleText(val text: String) : CreateMemoUiAction
    data class InputContentText(val text: String) : CreateMemoUiAction

}