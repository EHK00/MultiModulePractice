package com.example.mymemo.di

import com.example.data.repository.MemoRepositoryImpl
import com.example.mymemo.data.MyMemoSharedPref
import com.example.repository.MemoRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindMemoRepository(repo: MemoRepositoryImpl): MemoRepository
}