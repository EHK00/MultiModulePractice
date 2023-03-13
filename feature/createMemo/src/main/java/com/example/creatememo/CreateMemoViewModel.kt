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
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
internal class CreateMemoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val saveMemoUc: SaveMemoUc,
    private val getMemoUc: GetMemoUc,
) : BaseViewModel<CreateMemoState, CreateMemoUiAction>() {
    override val _stateFlow: MutableStateFlow<CreateMemoState> = MutableStateFlow(CreateMemoState.initial)

    init {
        val param = savedStateHandle.get<CreateMemoActivity.Param>(EXTRA_CREATE_MEMO_PARAM)
        viewModelScope.launch {
            _stateFlow.value = getMemo(param?.id)
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
            when (action) {
                is CreateMemoUiAction.InputContentText -> {
                    _stateFlow.update { state ->
                        state.copy(content = action.text)
                    }
                }
                is CreateMemoUiAction.InputTitleText -> {
                    _stateFlow.update { state ->
                        state.copy(subject = action.text)
                    }
                }
                is CreateMemoUiAction.SaveMemo -> {
                    _stateFlow.update { it.copy(isLoading = true) }
                    val memo = action.currentState.toMemo()
                    when (val result = saveMemoUc(SaveMemoUc.Param(memo))) {
                        is Resource.Success -> {
                            _stateFlow.update {
                                val notifications = it.notifications.toMutableList().apply {
                                    add("Success")
                                }

                                it.copy(
                                    isLoading = false,
                                    notifications = notifications
                                )
                            }
                        }
                        is Resource.Error -> {
                            val errorMessage = result.error?.message ?: result.error.toString()
                            _stateFlow.update {
                                val notifications = it.notifications.toMutableList().apply {
                                    add(errorMessage)
                                }

                                it.copy(
                                    isLoading = false,
                                    notifications = notifications
                                )
                            }
                        }
                    }


                }
                is CreateMemoUiAction.OnShowMessage ->
                    _stateFlow.update {
                        val newNotifications = it.notifications.toMutableList().apply {
                            remove(action.message)
                        }
                        it.copy(
                            notifications = newNotifications
                        )
                    }
            }
        }
    }


    private suspend fun getMemo(id: String?): CreateMemoState {
        if (id == null) {
            return CreateMemoState(
                id = UUID.randomUUID().toString(),
                subject = "",
                content = "",
                notifications = emptyList(),
                isLoading = false,
            )
        }

        return when (val result = getMemoUc(GetMemoUc.Param(id))) {
            is Resource.Success -> {
                val data = result.dataModel
                CreateMemoState(
                    id = data.memo.id,
                    subject = data.memo.subject,
                    content = data.memo.content,
                    notifications = emptyList(),
                    isLoading = false,
                )
            }
            is Resource.Error -> {
                CreateMemoState(
                    id = UUID.randomUUID().toString(),
                    subject = "",
                    content = "",
                    notifications = emptyList(),
                    isLoading = false,
                )
            }
        }
    }


    private fun CreateMemoState.toMemo(): Memo {
        return Memo(
            id = this.id,
            subject = this.subject,
            content = this.content
        )
    }
}

internal data class CreateMemoState(
    val id: String,
    val subject: String,
    val content: String,
    val notifications: List<String>,
    val isLoading: Boolean,
) : State {
    companion object {
        val initial: CreateMemoState = CreateMemoState(
            "", "", "", listOf(), false
        )
    }

}

internal sealed interface CreateMemoUiAction : UiAction {
    data class InputTitleText(val text: String) : CreateMemoUiAction
    data class InputContentText(val text: String) : CreateMemoUiAction
    data class SaveMemo(val currentState: CreateMemoState) : CreateMemoUiAction
    data class OnShowMessage(val message: String) : CreateMemoUiAction
}