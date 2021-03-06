package com.siriusproject.coshelek.categories_info.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("type")
    val type: String,
    @SerialName("picture")
    val picture: String,
    @SerialName("colour")
    val color: Int
)