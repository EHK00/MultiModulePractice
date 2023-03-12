package com.example.mymemo.di

import com.example.data.FileProvider
import com.example.mymemo.FileProviderImpl
import com.example.mymemo.ResourceProviderImpl
import com.example.provider.ResourceProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProviderModule {
    @Binds
    @Singleton
    abstract fun bindResourceProvider(
        provider: ResourceProviderImpl
    ): ResourceProvider

    @Binds
    @Singleton
    abstract fun bindFileProvider(
        provider: FileProviderImpl
    ): FileProvider
}