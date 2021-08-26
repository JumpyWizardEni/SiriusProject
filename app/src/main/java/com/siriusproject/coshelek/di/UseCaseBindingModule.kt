package com.siriusproject.coshelek.di

import com.siriusproject.coshelek.categories_info.domain.use_cases.AddCategory
import com.siriusproject.coshelek.categories_info.domain.use_cases.AddCategoryUseCase
import com.siriusproject.coshelek.categories_info.domain.use_cases.GetCategories
import com.siriusproject.coshelek.categories_info.domain.use_cases.GetCategoriesUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseBindingModule {

    @Binds
    abstract fun bindCategoriesUseCase(useCase: GetCategories): GetCategoriesUseCase

    @Binds
    abstract fun bindCategoryAddUseCase(useCase: AddCategory): AddCategoryUseCase

}