package com.siriusproject.coshelek.categories_info.data.model

import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import com.siriusproject.coshelek.wallet_information.data.model.TransactionType

data class CategoryUiModel(
    val id: Long,
    val name: String,
    val type: TransactionType,
    @DrawableRes
    val picture: Int,
    @ColorInt
    val color: Int
)