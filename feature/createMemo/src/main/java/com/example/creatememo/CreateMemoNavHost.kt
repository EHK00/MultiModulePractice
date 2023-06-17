package com.example.creatememo

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.common.NavGraphProvider
import javax.inject.Qualifier

@Qualifier
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class CreateMemoNavGraph

class CreateMemoNavGraphProvider: NavGraphProvider {
    override fun provide(navController: NavHostController, navGraphBuilder: NavGraphBuilder) {
        with(navGraphBuilder) {
            composable("createMemo") {
                CreateMemoScreenView(navHostController = navController)
            }
        }
    }
}
