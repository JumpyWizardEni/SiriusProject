package com.siriusproject.coshelek.categories_info.data.db

import android.graphics.Color
import com.siriusproject.coshelek.categories_info.data.model.Category
import javax.inject.Inject

class CategoriesDataSourceMock @Inject constructor(): CategoriesDataSource {

    override suspend fun getCategories() = listOf(
        Category(
            id = 0,
            name = "Зарплата",
            type = "INCOME",
            picture = "ic_cat_multivalue_cards",
            color = Color.GREEN),
        Category(
            id=1,
            name = "Подработка",
            type = "INCOME",
            picture = "ic_cat_multivalue_cards",
            color = Color.GREEN),
        Category(
            id=2,
            name = "Подарок",
            type = "INCOME",
            picture = "ic_cat_other_gift",
            color = Color.GREEN),
        Category(
            id=3,
            name = "Капитализация",
            type = "INCOME",
            picture = "ic_cat_other_percent",
            color = Color.GREEN)
    )

    override suspend fun addCategory(category: Category) {
        TODO("Not yet implemented")
    }
}