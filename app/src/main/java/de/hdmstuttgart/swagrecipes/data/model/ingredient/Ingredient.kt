package de.hdmstuttgart.swagrecipes.data.model.ingredient

data class Ingredient(
    val id: String,
    val aisle: String,
    val name: String,
    val amount: Int,
    val unit: String,
    val imageURL: String,
    val metaInformation: List<String>
)