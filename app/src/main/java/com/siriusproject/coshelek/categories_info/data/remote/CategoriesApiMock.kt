package com.siriusproject.coshelek.categories_info.data.remote

import com.siriusproject.coshelek.categories_info.data.model.Category

class CategoriesApiMock : CategoriesApi {

    override suspend fun getCategories(): List<Category> {
        TODO("Not yet implemented")
    }

}