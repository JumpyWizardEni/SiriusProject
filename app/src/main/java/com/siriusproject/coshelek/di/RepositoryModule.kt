package com.siriusproject.coshelek.di

import com.siriusproject.coshelek.categories_info.data.repos.CategoriesRepo
import com.siriusproject.coshelek.categories_info.data.repos.CategoriesRepoImpl
import com.siriusproject.coshelek.categories_info.domain.use_cases.GetCategories
import com.siriusproject.coshelek.categories_info.domain.use_cases.GetCategoriesUseCase
import com.siriusproject.coshelek.wallet_information.data.repos.TestWalletRepos
import com.siriusproject.coshelek.wallet_information.data.repos.TransactionsRepository
import com.siriusproject.coshelek.wallet_information.data.repos.TransactionsRepositoryImpl
import com.siriusproject.coshelek.wallet_information.data.repos.WalletRepos
import com.siriusproject.coshelek.wallet_list.data.repos.WalletsRepository
import com.siriusproject.coshelek.wallet_list.data.repos.WalletsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindTransactionsRepos(repos: TestWalletRepos): WalletRepos

    @Binds
    abstract fun bindCategoriesRepos(repos: CategoriesRepoImpl): CategoriesRepo

    @Binds
    abstract fun bindWalletRepos(repos: WalletsRepositoryImpl): WalletsRepository

    @Binds
    abstract fun bindTransactionRepos(repos: TransactionsRepositoryImpl): TransactionsRepository
}