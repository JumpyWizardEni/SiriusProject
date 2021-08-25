package com.siriusproject.coshelek.di

import com.siriusproject.coshelek.categories_info.data.db.CategoriesDataSource
import com.siriusproject.coshelek.categories_info.data.db.CategoriesDataSourceMock
import com.siriusproject.coshelek.wallet_information.data.network.MockTransactionRemote
import com.siriusproject.coshelek.wallet_information.data.network.TransactionService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindCategoriesDataSource(local: CategoriesDataSourceMock): CategoriesDataSource

    //Removed with Retrofit in Remote module
//    @Binds
//    @Singleton
//    abstract fun bindCategoriesApi(local: CategoriesApiMock): CategoriesApi

//    @Binds
//    @Singleton
//    abstract fun bindWalletRemoteService(remote: MockServerRemote): WalletService

    @Binds
    @Singleton
    abstract fun bindTransactionRemoteService(remote: MockTransactionRemote): TransactionService

}