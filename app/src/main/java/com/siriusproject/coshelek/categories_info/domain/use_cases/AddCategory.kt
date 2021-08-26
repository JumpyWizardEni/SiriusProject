package com.siriusproject.coshelek.categories_info.domain.use_cases

import com.siriusproject.coshelek.categories_info.data.model.CategoryCreatingBody
import com.siriusproject.coshelek.categories_info.data.model.CategoryIcon
import com.siriusproject.coshelek.categories_info.data.repos.CategoriesRepo
import com.siriusproject.coshelek.wallet_information.data.model.TransactionType
import javax.inject.Inject

class AddCategory @Inject constructor(
    private val categoriesRepo: CategoriesRepo,
) : AddCategoryUseCase {

    override suspend fun addCategory(
        name: String,
        type: TransactionType,
        icon: CategoryIcon,
        color: Int
    ) {
        categoriesRepo.addCategory(CategoryCreatingBody(name, type.toString(), icon.id, color))
    }
}