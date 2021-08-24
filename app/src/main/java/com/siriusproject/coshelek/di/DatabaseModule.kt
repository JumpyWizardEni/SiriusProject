package com.siriusproject.coshelek.di

import android.content.Context
import androidx.room.*
import com.siriusproject.coshelek.wallet_list.data.model.WalletDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    //    @Provides
//    @Singleton
//    fun provideDB(@ApplicationContext context: Context) = MainDatabase.getInstance(context)
//
//    @Provides
//    @Singleton
//    fun provideDAO(db: MainDatabase) = db.Dao()/

}