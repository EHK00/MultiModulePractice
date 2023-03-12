package com.example.mymemo.di

import android.content.Context
import com.example.data.local.MemoLocalDataSource
import com.example.mymemo.data.MemoLocalDataSourceImpl
import com.example.mymemo.data.MyMemoSharedPref
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    private val prefKey = "prefKey"

    @Provides
    @Singleton
    fun bindDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Provides
    @Singleton
    fun providePreference(
        @ApplicationContext context: Context,
        moshi: Moshi,
    ): MyMemoSharedPref {
        return MyMemoSharedPref(context, prefKey, moshi)
    }

    @Binds
    @Singleton
    abstract fun providesMemoLocalDataSource(impl: MemoLocalDataSourceImpl): MemoLocalDataSource
}