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
            "icon_cinema" to R.drawable.icon_cinema,
            "icon_clothing" to R.drawable.icon_clothing,
            "icon_education" to R.drawable.icon_education,
            "icon_else" to R.drawable.icon_else,
            "icon_entertainment" to R.drawable.icon_entertainment,
            "icon_finance" to R.drawable.icon_finance,
            "icon_home" to R.drawable.icon_home,
            "icon_internet" to R.drawable.icon_internet,
            "icon_jewelry" to R.drawable.icon_jewelry,
            "icon_medicine" to R.drawable.icon_medicine,
            "icon_mobile" to R.drawable.icon_mobile,
            "icon_music" to R.drawable.icon_music,
            "icon_pets" to R.drawable.icon_pets,
            "icon_pharmacy" to R.drawable.icon_pharmacy,
            "icon_restaurants_2" to R.drawable.icon_restaurants_2,
            "icon_restaurants" to R.drawable.icon_restaurants,
            "icon_services" to R.drawable.icon_services,
            "icon_shop" to R.drawable.icon_shop,
            "icon_souvenirs" to R.drawable.icon_souvenirs,
            "icon_sport" to R.drawable.icon_sport,
            "icon_svyaz" to R.drawable.icon_svyaz,
            "icon_theatre" to R.drawable.icon_theatre,
            "icon_train" to R.drawable.icon_train,
            "icon_travel" to R.drawable.icon_travel,
            "icon_tv" to R.drawable.icon_tv,
            "icon_wallet" to R.drawable.icon_wallet,
            "icon_avia" to R.drawable.icon_avia,
            "icon_bus" to R.drawable.icon_bus,
            "icon_car" to R.drawable.icon_car,
            "icon_charity" to R.drawable.icon_charity,
            "icon_house" to R.drawable.icon_house,
            "icon_gas_station" to R.drawable.icon_gas_station
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
