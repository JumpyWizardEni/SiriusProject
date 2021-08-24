package com.siriusproject.coshelek.di

import com.siriusproject.coshelek.utils.CurrencyFormatter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UtilsModule {

    @Provides
    @Singleton
    fun provideCurrencyFormatter() = CurrencyFormatter()
}