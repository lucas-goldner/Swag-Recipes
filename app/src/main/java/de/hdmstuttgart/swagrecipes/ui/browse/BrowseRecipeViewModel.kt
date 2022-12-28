package de.hdmstuttgart.swagrecipes.ui.browse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.hdmstuttgart.swagrecipes.data.model.recipe.Recipe
import de.hdmstuttgart.swagrecipes.data.repository.RandomRecipeRepository
import de.hdmstuttgart.swagrecipes.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class BrowseRecipeViewModel(private val randomRecipesRepository: RandomRecipeRepository): ViewModel()  {
    private val _recipeList = MutableStateFlow<Resource<List<Recipe>>>(Resource.loading())

    val recipeList: StateFlow<Resource<List<Recipe>>> = _recipeList

    init {
        fetchRandomRecipes()
    }

    private fun fetchRandomRecipes() {
        viewModelScope.launch {
            randomRecipesRepository.getRandomRecipes()
                .catch { e ->
                    _recipeList.value = Resource.error(e.toString())
                }
                .collect {
                    _recipeList.value = Resource.success(it)
                }
        }
    }
}