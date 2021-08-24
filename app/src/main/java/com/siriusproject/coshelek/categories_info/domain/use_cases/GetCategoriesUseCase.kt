package com.siriusproject.coshelek.categories_info.domain.use_cases

import com.siriusproject.coshelek.categories_info.data.model.CategoryUiModel
import com.siriusproject.coshelek.utils.LoadResult
import kotlinx.coroutines.flow.Flow

interface GetCategoriesUseCase {
    suspend fun getCategoriesForUI(): Flow<LoadResult<List<CategoryUiModel>>>
}