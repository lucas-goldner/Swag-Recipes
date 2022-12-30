package de.hdmstuttgart.swagrecipes.data.repository

import androidx.annotation.WorkerThread
import de.hdmstuttgart.swagrecipes.data.api.DBService
import de.hdmstuttgart.swagrecipes.data.model.recipe.Recipe
import kotlinx.coroutines.flow.Flow

class SavedRecipesRepository(private val dbService: DBService) {

    val allRecipes: Flow<List<Recipe>> = dbService.getRecipes()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun saveRecipe(recipe: Recipe) {
        dbService.insert(recipe)
    }
}