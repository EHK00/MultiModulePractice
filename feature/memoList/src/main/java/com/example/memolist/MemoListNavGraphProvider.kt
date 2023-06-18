package com.example.memolist

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.common.NavGraphProvider
import javax.inject.Inject
import javax.inject.Qualifier

@Qualifier
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class MemoListNavGraph

class MemoListNavGraphProvider @Inject constructor() : NavGraphProvider {
    override fun provide(navController: NavHostController, navGraphBuilder: NavGraphBuilder) {
        with(navGraphBuilder) {
            composable("memoList") {
                MemoListScreenView(navController = navController)
            }
        }
    }
}