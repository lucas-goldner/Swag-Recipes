package de.hdmstuttgart.swagrecipes.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.hdmstuttgart.swagrecipes.data.model.recipe.Recipe
import de.hdmstuttgart.swagrecipes.data.repository.SavedRecipesRepository
import kotlinx.coroutines.launch

class RecipeDetailViewModel(private val repository: SavedRecipesRepository) : ViewModel() {
    fun insert(recipe: Recipe) = viewModelScope.launch {
        repository.saveRecipe(recipe)
    }
}