package com.siriusproject.coshelek.categories_info.domain.use_cases

import com.siriusproject.coshelek.categories_info.data.model.CategoryUiModel
import com.siriusproject.coshelek.categories_info.data.repos.CategoriesRepo
import com.siriusproject.coshelek.categories_info.domain.mappers.CategoriesMapper
import com.siriusproject.coshelek.utils.LoadResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCategories @Inject constructor(
    private val repository: CategoriesRepo,
    private val categoriesMapper: CategoriesMapper
) : GetCategoriesUseCase {

    override suspend fun getCategoriesForUI(): Flow<LoadResult<List<CategoryUiModel>>> {
        return repository.getCategories()
            .map { categoriesMapper(it) }
            .map { LoadResult.Success(it) as LoadResult<List<CategoryUiModel>> }
            .catch { e -> emit(LoadResult.Error(e)) }
    }
}
