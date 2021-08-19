package com.siriusproject.coshelek.domain.model

data class Category(
    val id: Int,
    val name: String,
    val type: Int,
    val picture: String,
    val color: Int
)