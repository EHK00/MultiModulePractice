package com.example.mymemo.di

import com.example.common.Navigator
import com.example.mymemo.NavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class NavigatorModule {
    @Binds
    abstract fun bindNavigator(navigatorImpl: NavigatorImpl): Navigator
}