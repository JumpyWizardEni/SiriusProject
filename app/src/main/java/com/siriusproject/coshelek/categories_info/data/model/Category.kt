package com.siriusproject.coshelek.categories_info.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: Int,
    val name: String,
    val type: String,
    val picture: String,
    val color: Int
)