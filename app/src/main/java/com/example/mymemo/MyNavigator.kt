package com.example.mymemo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.example.common.MainState
import com.example.common.NavGraphProvider
import com.example.creatememo.CreateMemoNavGraph
import com.example.memolist.MemoListNavGraph
import javax.inject.Inject

class MyNavigator @Inject constructor(
    @CreateMemoNavGraph private val createMemoNavGraph: NavGraphProvider,
    @MemoListNavGraph private val memoListNavGraph: NavGraphProvider,
) {

    @Composable
    fun MyNavHost(
        mainState: MainState,
    ) {
        val navController = mainState.navController
        NavHost(
            modifier = Modifier,
            startDestination = "memoList",
            navController = navController,
        ) {
            createMemoNavGraph.provide(mainState, this)
            memoListNavGraph.provide(mainState, this)
        }
    }

}
