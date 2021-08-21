package com.siriusproject.coshelek.di

import com.siriusproject.coshelek.wallet_information.data.repos.WalletRepos
import com.siriusproject.coshelek.wallet_information.domain.use_cases.CategoriesMapper
import com.siriusproject.coshelek.wallet_information.domain.use_cases.GetCategories
import com.siriusproject.coshelek.wallet_information.domain.use_cases.GetTransactionUseCase
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
    fun provideGetCategories(mapper: CategoriesMapper) = GetCategories(mapper)

    @Provides
    @Singleton
    fun provideCategoriesMapper() = CategoriesMapper()

    @Provides
    @Singleton
    fun provideGetTransactionUseCase(repos: WalletRepos) = GetTransactionUseCase(repos)


}