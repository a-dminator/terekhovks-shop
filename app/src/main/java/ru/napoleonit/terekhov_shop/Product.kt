package ru.napoleonit.terekhov_shop

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val title: String,
    val imageUrl: String
)