package com.example.mymemo.di

import androidx.navigation.NavHost
import com.example.common.NavGraphProvider
import com.example.creatememo.CreateMemoNavGraph
import com.example.creatememo.CreateMemoNavGraphProvider
import com.example.memolist.MemoListNavGraph
import com.example.memolist.MemoListNavGraphProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
abstract class NavigatorModule {
    @Binds
    @CreateMemoNavGraph
    abstract fun bindCreateMemoNavGraphProvider(provider: CreateMemoNavGraphProvider): NavGraphProvider

    @Binds
    @MemoListNavGraph
    abstract fun bindMemoListNavGraphProvider(provider: MemoListNavGraphProvider): NavGraphProvider
}

//@Module
//object NavigationModule{
//    @Provides
//    @Singleton
//    fun provideMainNavGraphProvider(
//        @CreateMemoNavGraph createMemoNavGraph: NavGraphProvider,
//        @MemoListNavGraph memoListNavGraph: NavGraphProvider,
//    ): NavHost{
//    }
//}