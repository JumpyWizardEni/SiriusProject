package com.siriusproject.coshelek.di

import com.siriusproject.coshelek.wallet_list.domain.use_cases.CreateWalletUseCase
import com.siriusproject.coshelek.wallet_list.domain.use_cases.CreateWalletUseCaseImpl
import com.siriusproject.coshelek.wallet_list.domain.use_cases.GetWalletsListUseCaseImpl
import com.siriusproject.coshelek.wallet_list.domain.use_cases.GetWalletsUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseBindsModule {
    @Binds
    abstract fun bindGetWallet(useCase: GetWalletsListUseCaseImpl): GetWalletsUseCase

    @Binds
    abstract fun bindCreateWallet(useCase: CreateWalletUseCaseImpl): CreateWalletUseCase


}