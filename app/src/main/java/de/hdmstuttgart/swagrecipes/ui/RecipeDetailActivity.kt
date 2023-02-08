package de.hdmstuttgart.swagrecipes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import de.hdmstuttgart.swagrecipes.R
import de.hdmstuttgart.swagrecipes.SwagRecipesApplication
import de.hdmstuttgart.swagrecipes.data.model.recipe.Recipe
import de.hdmstuttgart.swagrecipes.databinding.ActivityRecipeDetailBinding
import de.hdmstuttgart.swagrecipes.providers.ViewModelProviderFactory
import de.hdmstuttgart.swagrecipes.ui.detail.RecipeDetailAdapter
import de.hdmstuttgart.swagrecipes.ui.detail.RecipeDetailViewModel

class RecipeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeDetailBinding
    private val recipeDetailViewModel by viewModels<RecipeDetailViewModel> {
        ViewModelProviderFactory(RecipeDetailViewModel::class) {
            RecipeDetailViewModel(
                (application as SwagRecipesApplication).savedRecipesRepository
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        binding = ActivityRecipeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recipe = loadRecipeFromIntent()
        val saveable = loadIfSaveableFromIntent()

        if (recipe != null) {

            // Meal Title
            binding.mealName.text = recipe.title

            // Meal "Badges"
            if (recipe.veryPopular) {
                binding.isPopular.visibility = View.VISIBLE
            }
            if (recipe.veryHealthy) {
                binding.isHealthy.visibility = View.VISIBLE
            }
            if (recipe.cheap) {
                binding.isCheap.visibility = View.VISIBLE
            }

            // Meal Image
            Glide.with(this)
                .load(recipe.imageURL)
                .placeholder(R.drawable.placeholder)
                .into(binding.image)
            // Meal Servings and Preparation time
            binding.mealTime.text = getString(R.string.ready_in_minutes_with_placeholder, recipe.readyInMinutes)
            binding.mealServings.text = getString(R.string.servings_with_placeholder, recipe.servings)

            // Meal Instructions
            binding.mealInstructions.text = recipe.instructions.replace(Regex("<[^>]*>"), "")

            // List of Ingredients
            val adapter = RecipeDetailAdapter(recipe.ingredients)
            val recyclerView = binding.mealIngredients
            recyclerView.adapter = adapter
            recyclerView.layoutManager = GridLayoutManager(this, 3)

            // Checkboxes
            binding.vegetarianCheckbox.isChecked = recipe.vegetarian
            binding.veganCheckbox.isChecked = recipe.vegan
            binding.glutenFreeCheckbox.isChecked = recipe.glutenFree
            binding.dairyFreeCheckbox.isChecked = recipe.dairyFree
            binding.ketogenic.isChecked = recipe.ketogenic

            // Save button
            if (saveable == true) binding.saveRecipeButton.visibility = View.VISIBLE
            binding.saveRecipeButton.setOnClickListener {
                recipeDetailViewModel.insert(recipe)
                finish()
            }
        }
    }

    private fun loadRecipeFromIntent(): Recipe? {
        val browseIntent = intent
        return browseIntent.getParcelableExtra("recipe")
    }

    private fun loadIfSaveableFromIntent(): Boolean {
        val browseIntent = intent
        return browseIntent.getBooleanExtra("saveable", false)
    }
}