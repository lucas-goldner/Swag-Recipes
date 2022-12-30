package de.hdmstuttgart.swagrecipes.ui.collection

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import de.hdmstuttgart.swagrecipes.data.model.recipe.Recipe
import de.hdmstuttgart.swagrecipes.data.repository.SavedRecipesRepository
import kotlinx.coroutines.launch

class CollectionViewModel(private val repository: SavedRecipesRepository) : ViewModel() {
    val allRecipes: LiveData<List<Recipe>> = repository.allRecipes.asLiveData()

    fun insert(recipe: Recipe) = viewModelScope.launch {
        repository.saveRecipe(recipe)
    }
}