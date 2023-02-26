package com.example.mymemo.di

import com.example.mymemo.ResourceProviderImp
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
        provider: ResourceProviderImp
    ): ResourceProvider
}