package com.siriusproject.coshelek.di

import com.siriusproject.coshelek.wallet_information.domain.mapper.TransactionMapper
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

    @Provides
    @Singleton
    fun provideTransactionMapper() = TransactionMapper()
}