package com.example.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<S : State> : ViewModel() {
    protected abstract val _stateFlow: MutableStateFlow<S>
    val stateFlow: StateFlow<S> by lazy { _stateFlow.asStateFlow() }

    protected fun updateState(state: S){
        _stateFlow.update { state }
    }
}

interface State