package de.hdmstuttgart.swagrecipes.data.model.recipe

import de.hdmstuttgart.swagrecipes.data.model.ingredient.Ingredient

data class RecipeResponse(
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
    val servings: Int,
    val sourceUrl: String,
    val spoonacularSourceUrl: String,
    val aggregateLikes: Int,
    val creditText: String,
    val sourceName: String,
    val extendedIngredients: List<Ingredient>,
    val id: String,
    val title: String,
    val readyInMinutes: Int,
    val image: String,
    val imageType: String,
    val instructions: String
) {
    fun toRecipe() = Recipe(
        id = this.id,
        title = this.title,
        ingredients = this.extendedIngredients,
        imageURL = this.image,
        instructions = this.instructions,
        readyInMinutes = this.readyInMinutes,
        vegetarian = this.vegetarian,
        vegan = this.vegan,
        glutenFree = this.glutenFree,
        dairyFree = this.dairyFree,
        veryHealthy = this.veryHealthy,
        cheap = this.cheap,
        veryPopular = this.veryPopular,
        sustainable = this.sustainable,
        weightWatcherSmartPoints = this.weightWatcherSmartPoints,
        gaps = this.gaps,
        lowFodmap = this.lowFodmap,
        ketogenic = this.ketogenic,
        whole30 = this.whole30,
        servings = this.servings
    )
}
