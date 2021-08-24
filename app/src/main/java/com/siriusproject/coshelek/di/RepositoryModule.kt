package com.siriusproject.coshelek.di

import com.siriusproject.coshelek.categories_info.data.repos.CategoriesRepo
import com.siriusproject.coshelek.categories_info.data.repos.CategoriesRepoImpl
import com.siriusproject.coshelek.wallet_information.data.repos.TransactionsRepository
import com.siriusproject.coshelek.wallet_information.data.repos.TransactionsRepositoryImpl
import com.siriusproject.coshelek.wallet_list.data.repos.WalletsRepository
import com.siriusproject.coshelek.wallet_list.data.repos.WalletsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCategoriesRepos(repos: CategoriesRepoImpl): CategoriesRepo

    @Binds
    @Singleton
    abstract fun bindWalletRepos(repos: WalletsRepositoryImpl): WalletsRepository

    @Binds
    @Singleton
    abstract fun bindTransactionRepos(repos: TransactionsRepositoryImpl): TransactionsRepository
}