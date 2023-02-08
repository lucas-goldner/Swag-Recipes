package de.hdmstuttgart.swagrecipes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import de.hdmstuttgart.swagrecipes.R
import de.hdmstuttgart.swagrecipes.data.model.recipe.Recipe
import de.hdmstuttgart.swagrecipes.databinding.ActivityRecipeDetailBinding
import de.hdmstuttgart.swagrecipes.ui.detail.RecipeDetailAdapter

class RecipeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        binding = ActivityRecipeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recipe = loadRecipeFromIntent()

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
            binding.mealTime.text = "Ready in " + recipe.readyInMinutes + " minutes"
            binding.mealServings.text = recipe.servings.toString() + " servings"

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
        }
    }

    private fun loadRecipeFromIntent(): Recipe? {
        val browseIntent = intent
        return browseIntent.getParcelableExtra("recipe")
    }
}