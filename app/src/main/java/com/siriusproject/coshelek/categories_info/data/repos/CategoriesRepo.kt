package com.siriusproject.coshelek.categories_info.data.repos

import com.siriusproject.coshelek.categories_info.data.model.Category
import com.siriusproject.coshelek.categories_info.data.model.CategoryCreatingBody
import kotlinx.coroutines.flow.Flow

interface CategoriesRepo {
    suspend fun getCategories(): Flow<List<Category>>
    suspend fun addCategory(categoryCreatingBody: CategoryCreatingBody)
}