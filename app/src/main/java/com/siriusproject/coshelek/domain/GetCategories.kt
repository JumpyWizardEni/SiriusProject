package com.siriusproject.coshelek.domain

import android.graphics.Color
import com.siriusproject.coshelek.domain.model.Category

class GetCategories: () -> List<Category> {

    override fun invoke(): List<Category> {
        return listOf(
            Category(
                id=0,
                name = "Зарплата",
                type = 1,
                picture = "ic_cat_multivalue_cards",
                color = Color.GREEN),
            Category(
                id=1,
                name = "Подработка",
                type = 1,
                picture = "ic_cat_multivalue_cards",
                color = Color.GREEN),
            Category(
                id=2,
                name = "Подарок",
                type = 1,
                picture = "ic_cat_other_gift",
                color = Color.GREEN),
            Category(
                id=3,
                name = "Капитализация",
                type = 1,
                picture = "ic_cat_other_percent",
                color = Color.GREEN)
        )
    }
}