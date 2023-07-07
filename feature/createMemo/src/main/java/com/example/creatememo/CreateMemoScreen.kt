package com.example.creatememo

import android.os.Parcelable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import timber.log.Timber


@Composable
internal fun CreateMemoScreenView(
    vm: CreateMemoViewModel = hiltViewModel(),
    scrollableState: ScrollableState = rememberScrollState(),
    navHostController: NavHostController,
    snackBarHostState: SnackbarHostState,
) {
    val state by vm.stateFlow.collectAsState()
    val scope = rememberCoroutineScope()

    val onSave: () -> Unit = remember { { vm.uiAction(CreateMemoUiAction.SaveMemo(state)) } }
    val onTitleChange: (String) -> Unit = { vm.uiAction(CreateMemoUiAction.InputTitleText(it)) }
    val onContentChange: (String) -> Unit = {
        vm.uiAction(CreateMemoUiAction.InputContentText(it))
    }
    val test1 = LaunchedEffect(state.onComplete) {
        if (state.onComplete) {
            navHostController.popBackStack()
        }
    }

    val test2 = LaunchedEffect(state.content) {
        scope.launch {
            snackBarHostState.showSnackbar(state.content)
        }
    }
    Timber.d("__ test1 : ${test1.hashCode()}")
    Timber.d("__ test2 : ${test2.hashCode()}")


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
                onValueChange = onTitleChange,
                singleLine = true
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
    CreateMemoScreen(
        modifier = Modifier.fillMaxWidth(),
        state = CreateMemoState(
            id = "id",
            subject = "subject",
            content = "content",
            notifications = listOf("notifications"),
            isLoading = false,
            onComplete = false,
        )
    )
}