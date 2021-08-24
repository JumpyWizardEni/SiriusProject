package com.siriusproject.coshelek.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

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