package de.hdmstuttgart.swagrecipes.data.repository

import de.hdmstuttgart.swagrecipes.data.api.NetworkService
import de.hdmstuttgart.swagrecipes.data.model.recipe.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RandomRecipeRepository @Inject constructor(private val networkService: NetworkService) {

    fun getRandomRecipes(): Flow<List<Recipe>> {
        return flow {
            emit(networkService.getRandomRecipes())
        }.map { randomRecipesIt ->
            randomRecipesIt.recipes.map { it.toRecipe() }
        }
    }

}