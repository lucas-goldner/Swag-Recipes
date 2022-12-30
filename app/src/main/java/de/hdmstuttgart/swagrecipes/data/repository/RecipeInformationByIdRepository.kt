package de.hdmstuttgart.swagrecipes.data.repository

import de.hdmstuttgart.swagrecipes.data.api.NetworkService
import de.hdmstuttgart.swagrecipes.data.model.recipe.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeInformationByIdRepository @Inject constructor(private val networkService: NetworkService) {

    fun getRecipeInformationById(id: String): Flow<Recipe> {
        return flow {
            emit(networkService.getRecipeInformation(id))
        }.map {
            it.toRecipe()
        }
    }

}