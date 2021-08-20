package com.siriusproject.coshelek.wallet_information.data.model

import android.graphics.drawable.Drawable

data class CategoryUiModel(
    val id: Int,
    val name: String,
    val type: TransactionType,
    val picture: Drawable,
    val color: Int
)