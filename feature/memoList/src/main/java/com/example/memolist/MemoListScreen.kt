package com.example.memolist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.example.model.ShortenMemo


@Composable
internal fun MemoListScreenView(
    modifier: Modifier,
    navController: NavController,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    vm: MemoListViewModel = hiltViewModel(),
) {
    val memoListState by vm.stateFlow.collectAsState()
    val onItemClick = { item: ShortenMemo ->
        navController.navigate("createMemo")
    }
    val onNewItemClick: () -> Unit = {
        navController.navigate("createMemo")
    }

    MemoListScreen(
        modifier = modifier,
        scaffoldState = scaffoldState,
        memoListState = memoListState,
        onItemClick = onItemClick,
        onNewItemClick = onNewItemClick,
    )

    vm.uiAction(MemoListAction.LoadMemoList)
}

@Composable
internal fun MemoListScreen(
    modifier: Modifier,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    memoListState: MemoListState,
    onItemClick: (ShortenMemo) -> Unit = {},
    onNewItemClick: () -> Unit = {},
    onStart: () -> Unit = {}
) {
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            NewItemFloatingActionButton(onNewItemClick)
        }
    ) { contentPadding ->

        val lifecycleOwner = rememberUpdatedState(newValue = LocalLifecycleOwner.current)
        DisposableEffect(lifecycleOwner) {
            val lifecycle = lifecycleOwner.value.lifecycle
            val observer = LifecycleEventObserver { _: LifecycleOwner, event: Lifecycle.Event ->
                when (event) {
                    Lifecycle.Event.ON_START -> onStart()
                    else -> Unit
                }
            }
            lifecycle.addObserver(observer)
            onDispose {
                lifecycle.removeObserver(observer)
            }
        }


        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            when (val listState = memoListState.listState) {
                ListState.None -> Unit
                is ListState.OnError -> {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = listState.errorMessage,
                    )
                }

                is ListState.OnLoad -> {
                    MemoList(
                        modifier = modifier, itemList = listState.itemList, onItemClick = onItemClick
                    )
                }
            }
        }
    }
}

@Composable
fun NewItemFloatingActionButton(
    onNewItemClick: () -> Unit
) {
    FloatingActionButton(onClick = onNewItemClick) {
        Icon(painter = painterResource(id = android.R.drawable.ic_menu_save), contentDescription = "new item")
    }
}

@Composable
fun MemoList(
    modifier: Modifier,
    itemList: List<ShortenMemo>,
    onItemClick: (ShortenMemo) -> Unit = {}
) {
    val contentPadding = PaddingValues(8.dp)
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = contentPadding
    ) {
        items(
            itemList,
            key = { item -> item.id }
        ) {
            MemoHolder(
                modifier = modifier, memo = it,
                onClick = onItemClick
            )
        }
    }
}

@Composable
fun MemoHolder(
    modifier: Modifier,
    memo: ShortenMemo,
    onClick: (ShortenMemo) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(memo) },
    ) {
        Text(text = memo.simpleText)
    }
}

@Preview
@Composable
internal fun MemoListScreenPreview(
    modifier: Modifier = Modifier,
    memoListState: MemoListState = MemoListState(
        listState = ListState.OnLoad(
            listOf(
                ShortenMemo("1", "test"),
                ShortenMemo("2", "test2"),
                ShortenMemo("3", "test3"),
            )
        ),
        navigateState = NavigateState.None
    ),
    onClick: (ShortenMemo) -> Unit = {}
) {
    MemoListScreen(modifier = modifier, memoListState = memoListState)
}