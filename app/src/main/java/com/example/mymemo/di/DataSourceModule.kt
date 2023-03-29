package com.example.mymemo.di

import com.example.data.local.JsonMemoLocalDataSource
import com.example.data.local.MemoLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    @Singleton
    fun bindDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}


@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceBindModule {
    @Binds
    @Singleton
    abstract fun providesMemoLocalDataSource(impl: JsonMemoLocalDataSource): MemoLocalDataSource
}