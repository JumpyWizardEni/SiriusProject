package com.siriusproject.coshelek.categories_info.data.db

import com.siriusproject.coshelek.categories_info.data.model.Category

interface CategoriesDataSource {

    suspend fun getCategories(): List<Category>

    suspend fun addCategory(category: Category)

}