package com.example.common

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Stable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController


interface NavGraphProvider {
    fun provide(
        mainState: MainState,
        navGraphBuilder: NavGraphBuilder
    )
}


@Stable
class MainState(
    val navController: NavHostController,
    val scaffoldState: ScaffoldState,
)