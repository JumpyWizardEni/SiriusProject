package com.siriusproject.coshelek.di

import com.siriusproject.coshelek.wallet_information.data.db.DataSource
import com.siriusproject.coshelek.wallet_information.data.db.TestDataManager
import com.siriusproject.coshelek.wallet_information.data.network.RemoteSource
import com.siriusproject.coshelek.wallet_information.data.network.ServerRemoteSource
import com.siriusproject.coshelek.wallet_list.data.remote.MockServerRemote
import com.siriusproject.coshelek.wallet_list.data.remote.WalletRemote
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindLocalDataSource(local: TestDataManager): DataSource

    @Binds
    abstract fun bindRemoteDataSource(remote: ServerRemoteSource): RemoteSource

    @Binds
    abstract fun bindWalletRemoteService(remote: MockServerRemote): WalletRemote
}