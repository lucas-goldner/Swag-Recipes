package de.hdmstuttgart.swagrecipes.data.model.recipe

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import de.hdmstuttgart.swagrecipes.data.model.ingredient.Ingredient

@Entity(tableName = "recipe_table")
data class Recipe(
    @PrimaryKey val id: String,
    val title: String,
    val ingredients: List<Ingredient>,
    val imageURL: String,
    val instructions: String,
    val readyInMinutes: Int,
    val vegetarian: Boolean,
    val vegan: Boolean,
    val glutenFree: Boolean,
    val dairyFree: Boolean,
    val veryHealthy: Boolean,
    val cheap: Boolean,
    val veryPopular: Boolean,
    val sustainable: Boolean,
    val weightWatcherSmartPoints: Int,
    val gaps: String,
    val lowFodmap: Boolean,
    val ketogenic: Boolean,
    val whole30: Boolean,
    val servings: Int
)