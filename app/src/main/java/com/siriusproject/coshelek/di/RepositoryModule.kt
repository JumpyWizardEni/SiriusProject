package com.siriusproject.coshelek.di

import com.siriusproject.coshelek.wallet_information.data.repos.TestWalletRepos
import com.siriusproject.coshelek.wallet_information.data.repos.WalletRepos
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindTransactionsRepos(repos: TestWalletRepos): WalletRepos


}