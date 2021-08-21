package com.siriusproject.coshelek.wallet_information.domain.use_cases

import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.wallet_information.data.model.Category
import com.siriusproject.coshelek.wallet_information.data.model.CategoryUiModel
import com.siriusproject.coshelek.wallet_information.data.model.TransactionType

class CategoriesMapper : (Category) -> CategoryUiModel {

    override fun invoke(category: Category): CategoryUiModel {
        return CategoryUiModel(
            id = category.id,
            name = category.name,
            type = intTypeToTransactionType(category.type),
            picture = iconNameToDrawable(category.picture),
            color = category.color
        )
    }

    private fun iconNameToDrawable(name: String): Int =
        when (name) {
            "icon_card" -> R.drawable.ic_cat_multivalue_cards
            "icon_gift" -> R.drawable.ic_cat_other_gift
            "icon_percent" -> R.drawable.ic_cat_other_percent
            else -> R.drawable.ic_cat_multivalue_cards //TODO default icon for unknown icon name
        }

    private fun intTypeToTransactionType(type: Int): TransactionType =
        when (type) {
            0 -> TransactionType.Consumption
            1 -> TransactionType.Income
            else -> TransactionType.Consumption
        }
}
