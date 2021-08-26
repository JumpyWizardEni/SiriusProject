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

    companion object {
        val pictureIDbyNameMap = mapOf(
            "icon_card" to R.drawable.ic_cat_multivalue_cards,
            "icon_gift" to R.drawable.ic_cat_other_gift,
            "icon_percent" to R.drawable.ic_cat_other_percent,
            "icon_shop" to 0,
            "icon_home" to 0,
            "icon_bus" to 0
        )
    }

    private fun iconNameToDrawable(name: String): Int =
        pictureIDbyNameMap[name] ?: R.drawable.ic_cat_multivalue_cards

    private fun intTypeToTransactionType(type: String): TransactionType =
        when (type) {
            "INCOME" -> TransactionType.Income
            "EXPENSE" -> TransactionType.Expense
            else -> TransactionType.Expense
        }

}
