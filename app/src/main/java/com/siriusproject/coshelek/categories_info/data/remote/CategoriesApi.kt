package com.siriusproject.coshelek.categories_info.data.remote

import com.siriusproject.coshelek.categories_info.data.model.Category
import com.siriusproject.coshelek.categories_info.data.model.CategoryCreatingBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CategoriesApi {

    @GET("categories")
    suspend fun getCategories(): List<Category>

    @POST("category")
    suspend fun createCategory(@Body body: CategoryCreatingBody): List<Category>
}