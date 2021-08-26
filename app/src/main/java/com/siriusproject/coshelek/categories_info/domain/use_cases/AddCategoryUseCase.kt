package com.siriusproject.coshelek.categories_info.domain.use_cases

import com.siriusproject.coshelek.categories_info.data.model.CategoryIcon
import com.siriusproject.coshelek.wallet_information.data.model.TransactionType

interface AddCategoryUseCase {

    suspend fun addCategory(
        name: String,
        type: TransactionType,
        icon: CategoryIcon,
        color: Int
    )

}