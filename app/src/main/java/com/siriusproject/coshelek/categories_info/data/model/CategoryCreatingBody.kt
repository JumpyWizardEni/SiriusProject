package com.siriusproject.coshelek.categories_info.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryCreatingBody(
    val name: String,
    val type: String,
    val picture: String,
    @SerialName("colour")
    val color: Int
)