package com.siriusproject.coshelek.categories_info.data.remote

import com.siriusproject.coshelek.categories_info.data.model.Category
import com.siriusproject.coshelek.categories_info.data.model.CategoryCreatingBody
import javax.inject.Inject

class CategoriesApiMock @Inject constructor() : CategoriesApi {

    override suspend fun getCategories(): List<Category> {
        TODO("Not yet implemented")
    }

    override suspend fun createCategory(body: CategoryCreatingBody): List<Category> {
        TODO("Not yet implemented")
    }

}