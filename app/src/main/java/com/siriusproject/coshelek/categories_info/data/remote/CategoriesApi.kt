package com.siriusproject.coshelek.categories_info.data.remote

import com.siriusproject.coshelek.categories_info.data.model.Category
import retrofit2.http.GET

interface CategoriesApi {

    @GET("categories")
    suspend fun getCategories(): List<Category>

}