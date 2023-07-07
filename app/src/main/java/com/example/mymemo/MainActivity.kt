package com.example.mymemo

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.navigation.compose.rememberNavController
import com.example.common.MainState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var myNavigator: MyNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val scaffoldState = rememberScaffoldState()
                Scaffold(scaffoldState = scaffoldState) { padding ->
                    val mainState = MainState(
                        navController = rememberNavController(),
                        scaffoldState = scaffoldState
                    )
                    myNavigator.MyNavHost(
                        mainState = mainState
                    )
                }
            }
        }
    }
}