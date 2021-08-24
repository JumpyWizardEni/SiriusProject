package com.siriusproject.coshelek.di

import com.siriusproject.coshelek.wallet_information.data.db.DataSource
import com.siriusproject.coshelek.wallet_information.data.db.TestDataManager
import com.siriusproject.coshelek.wallet_information.data.network.MockTransactionRemote
import com.siriusproject.coshelek.wallet_information.data.network.RemoteSource
import com.siriusproject.coshelek.wallet_information.data.network.ServerRemoteSource
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
    abstract fun bindLocalDataSource(local: TestDataManager): DataSource

    @Binds
    @Singleton
    abstract fun bindRemoteDataSource(remote: ServerRemoteSource): RemoteSource

//    @Binds
//    @Singleton
//    abstract fun bindWalletRemoteService(remote: MockServerRemote): WalletService

    @Binds
    @Singleton
    abstract fun bindTransactionRemoteService(remote: MockTransactionRemote): TransactionService

}