package com.example.common

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController


interface NavGraphProvider {
    fun provide(
        navController: NavHostController,
        navGraphBuilder: NavGraphBuilder
    )
}