package de.hdmstuttgart.swagrecipes.ui.browse

import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdmstuttgart.swagrecipes.R
import de.hdmstuttgart.swagrecipes.data.model.recipe.Recipe
import de.hdmstuttgart.swagrecipes.databinding.RecipeListViewBinding

class BrowseRecipeAdapter(
    private val recipeList: ArrayList<Recipe>,
    var onItemClick: ((Recipe) -> Unit)
) : RecyclerView.Adapter<BrowseRecipeAdapter.DataViewHolder>() {


    inner class DataViewHolder(private val binding: RecipeListViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onItemClick(recipeList[adapterPosition])
            }
        }

        fun bind(recipe: Recipe) {
            binding.title.text = recipe.title
            binding.ingredients.text = recipe.ingredients.joinToString(separator = ", ") { it.name }
            binding.readyInMinutes.text = "Prep time: " + recipe.readyInMinutes.toString() + " min"

            Glide.with(binding.image.context)
                .load(recipe.imageURL)
                .placeholder(R.drawable.placeholder)
                .into(binding.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            RecipeListViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = recipeList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(recipeList[position])

    fun addData(list: List<Recipe>) {
        recipeList.addAll(list)
    }
}