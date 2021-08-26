package com.siriusproject.coshelek.categories_info.domain.mappers

import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.categories_info.data.model.Category
import com.siriusproject.coshelek.categories_info.data.model.CategoryUiModel
import com.siriusproject.coshelek.wallet_information.data.model.TransactionType

class CategoriesMapper : (List<Category>) -> List<CategoryUiModel> {

    override fun invoke(categories: List<Category>): List<CategoryUiModel> {
        return categories.map { category ->
            CategoryUiModel(
                id = category.id,
                name = category.name,
                type = intTypeToTransactionType(category.type),
                picture = iconNameToDrawable(category.picture),
                color = category.color
            )
        }
    }

    private fun iconNameToDrawable(name: String): Int =
        when (name) {
            "icon_card" -> R.drawable.ic_cat_multivalue_cards
            "icon_gift" -> R.drawable.ic_cat_other_gift
            "icon_percent" -> R.drawable.ic_cat_other_percent
            else -> R.drawable.ic_cat_multivalue_cards //TODO default icon for unknown icon name
        }

    private fun intTypeToTransactionType(type: String): TransactionType =
        when (type) {
            "INCOME" -> TransactionType.Income
            "EXPENSE" -> TransactionType.Expense
            else -> TransactionType.Expense
        }

}
