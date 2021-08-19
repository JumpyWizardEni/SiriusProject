package com.siriusproject.coshelek.domain

import android.graphics.Color

data class Category(
    val id: Int,
    val name: String,
    val type: Int,
    val picture: String,
    val color: Int
)

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
                id=0,
                name = "Подработка",
                type = 1,
                picture = "ic_cat_multivalue_cards",
                color = Color.GREEN),
            Category(
                id=0,
                name = "Подарок",
                type = 1,
                picture = "ic_cat_other_gift",
                color = Color.GREEN),
            Category(
                id=0,
                name = "Капитализация",
                type = 1,
                picture = "ic_cat_other_percent",
                color = Color.GREEN)
        )
    }
}