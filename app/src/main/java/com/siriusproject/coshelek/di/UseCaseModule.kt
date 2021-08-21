package com.siriusproject.coshelek.di

import com.siriusproject.coshelek.categories_info.data.repos.CategoriesRepo
import com.siriusproject.coshelek.wallet_information.data.repos.WalletRepos
import com.siriusproject.coshelek.categories_info.domain.mappers.CategoriesMapper
import com.siriusproject.coshelek.categories_info.domain.use_cases.GetCategories
import com.siriusproject.coshelek.categories_info.domain.use_cases.GetCategoriesUseCase
import com.siriusproject.coshelek.wallet_information.domain.use_cases.GetTransactionUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


//Отсюда перейдет в UseCaseBindsModule
@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetTransactionUseCase(repos: WalletRepos) = GetTransactionUseCase(repos)

}