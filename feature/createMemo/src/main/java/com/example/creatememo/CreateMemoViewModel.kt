package com.example.creatememo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.Resource
import com.example.common.BaseViewModel
import com.example.common.State
import com.example.common.UiAction
import com.example.domain.GetMemoUc
import com.example.domain.SaveMemoUc
import com.example.model.Memo
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
internal class CreateMemoViewModel @Inject constructor(
    private val saveMemoUc: SaveMemoUc,
    private val getMemoUc: GetMemoUc,
    private val savedStateHandle: SavedStateHandle,
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
        _uiActionFlow.collect { action ->
            when (action) {
                is CreateMemoUiAction.InputContentText -> {
                    _stateFlow.update { it.copy(content = action.text) }
                }
                is CreateMemoUiAction.InputTitleText -> {
                    _stateFlow.update { it.copy(subject = action.text) }
                }
                is CreateMemoUiAction.SaveMemo -> {
                    _stateFlow.update { it.copy(isLoading = true) }
                    _stateFlow.update { doOnSaveMemo(it) }
                    _stateFlow.update { it.copy(isLoading = false) }
                }
                is CreateMemoUiAction.AfterShowMessage -> {
                    _stateFlow.update { doOnShowMessage(it, action.message) }
                }
                CreateMemoUiAction.AfterNavigate ->
                    _stateFlow.update { it.copy(onComplete = false) }
            }
        }
    }

    private suspend fun doOnSaveMemo(currentState: CreateMemoState): CreateMemoState {
        val memo = currentState.toMemo()
        return when (val result = saveMemoUc(SaveMemoUc.Param(memo))) {
            is Resource.Success -> {
                currentState.copy(onComplete = true)
            }
            is Resource.Error -> {
                val errorMessage = result.error?.message ?: result.error.toString()
                val notifications = currentState.notifications.toMutableList().apply {
                    add(errorMessage)
                }
                currentState.copy(notifications = notifications)
            }
        }
    }

    private fun doOnShowMessage(currentState: CreateMemoState, message: String): CreateMemoState {
        val newNotifications = currentState.notifications.toMutableList().apply {
            remove(message)
        }
        return currentState.copy(notifications = newNotifications)
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
                )
            }
            is Resource.Error -> {
                CreateMemoState(
                    id = UUID.randomUUID().toString(),
                    subject = "",
                    content = "",
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
    val notifications: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val onComplete: Boolean = false,
) : State {
    companion object {
        val initial: CreateMemoState = CreateMemoState("", "", "")
    }

}

internal sealed interface CreateMemoUiAction : UiAction {
    data class InputTitleText(val text: String) : CreateMemoUiAction
    data class InputContentText(val text: String) : CreateMemoUiAction
    data class SaveMemo(val currentState: CreateMemoState) : CreateMemoUiAction
    data class AfterShowMessage(val message: String) : CreateMemoUiAction
    object AfterNavigate : CreateMemoUiAction
}