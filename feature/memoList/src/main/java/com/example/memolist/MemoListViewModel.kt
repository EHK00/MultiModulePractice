package com.example.memolist

import androidx.lifecycle.ViewModel
import com.example.common.BaseViewModel
import com.example.common.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MemoListViewModel @Inject constructor() : BaseViewModel<MemoListState>() {
    override val _stateFlow: MutableStateFlow<MemoListState> = MutableStateFlow(MemoListState.None)


}

sealed class MemoListState : State {
    object None : MemoListState()

}
