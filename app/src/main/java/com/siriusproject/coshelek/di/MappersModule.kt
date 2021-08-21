package com.siriusproject.coshelek.di

import com.siriusproject.coshelek.wallet_list.domain.mappers.WalletMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MappersModule {

    @Provides
    @Singleton
    fun provideWalletMapper() = WalletMapper()
}