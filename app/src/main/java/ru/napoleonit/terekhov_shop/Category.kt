package ru.napoleonit.terekhov_shop

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val productsUrl: String,
    val name: String,
    val imageUrl: String
)