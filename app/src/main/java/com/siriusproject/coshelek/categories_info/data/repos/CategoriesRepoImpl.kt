package com.siriusproject.coshelek.categories_info.data.repos

import com.siriusproject.coshelek.categories_info.data.db.CategoriesDataSource
import com.siriusproject.coshelek.categories_info.data.remote.CategoriesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CategoriesRepoImpl @Inject constructor(
    private val dbSource: CategoriesDataSource,
    private val categoriesApi: CategoriesApi
) : CategoriesRepo {

    override suspend fun getCategories() = flow {
        emit(categoriesApi.getCategories())
    }.flowOn(Dispatchers.IO)

}