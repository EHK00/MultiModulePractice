package com.example.creatememo

import android.os.Parcelable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.common.UiAction
import kotlinx.parcelize.Parcelize


@Composable
internal fun CreateMemoScreenView(
    vm: CreateMemoViewModel = hiltViewModel(),
    scrollableState: ScrollableState = rememberScrollState(),
    navHostController: NavHostController,
) {
    val state by vm.stateFlow.collectAsState()
    val onSave = { vm.uiAction(CreateMemoUiAction.SaveMemo(state)) }
    val onTitleChange: (String) -> Unit = { vm.uiAction(CreateMemoUiAction.InputTitleText(it)) }
    val onContentChange: (String) -> Unit = { vm.uiAction(CreateMemoUiAction.InputContentText(it)) }
    LaunchedEffect(state){
        if(state.onComplete){
            navHostController.popBackStack()
        }
    }

    CreateMemoScreen(
        modifier = Modifier.fillMaxWidth(),
        onTitleChange = onTitleChange,
        onContentChange = onContentChange,
        onSave = onSave,
        state = state,
        scrollableState = scrollableState
    )

}

@Composable
internal fun CreateMemoScreen(
    modifier: Modifier = Modifier,
    onTitleChange: (String) -> Unit = {},
    onContentChange: (String) -> Unit = {},
    onSave: () -> Unit = {},
    state: CreateMemoState = CreateMemoState.initial,
    scrollableState: ScrollableState = rememberScrollState(),
) {
    Scaffold(
        modifier = modifier
    ) { paddingValues ->
        Column(modifier = modifier.padding(paddingValues)) {
            TextField(
                modifier = modifier,
                value = state.subject,
                onValueChange = onTitleChange
            )
            TextField(
                modifier = modifier
                    .scrollable(
                        state = scrollableState,
                        orientation = Orientation.Vertical
                    )
                    .weight(1f),
                value = state.content,
                onValueChange = onContentChange
            )
            Button(
                modifier = modifier,
                onClick = onSave,
            ) {
                Text(text = "save")
            }
        }
    }
}

@Parcelize
data class Param(
    val id: String?
) : Parcelable

@Preview
@Composable
internal fun CreateMemoScreenPreview() {
    CreateMemoScreen(Modifier.fillMaxWidth())
}