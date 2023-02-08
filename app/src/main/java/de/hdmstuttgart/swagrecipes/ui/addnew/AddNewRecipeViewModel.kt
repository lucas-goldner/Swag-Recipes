package de.hdmstuttgart.swagrecipes.ui.addnew

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.hdmstuttgart.swagrecipes.data.model.recipe.Recipe
import de.hdmstuttgart.swagrecipes.data.repository.SavedRecipesRepository
import kotlinx.coroutines.launch

class AddNewRecipeViewModel(private val repository: SavedRecipesRepository) : ViewModel() {
    fun insert(recipe: Recipe) = viewModelScope.launch {
        repository.saveRecipe(recipe)
    }
}