package de.hdmstuttgart.swagrecipes.ui.browse

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdmstuttgart.swagrecipes.data.model.recipe.Recipe
import de.hdmstuttgart.swagrecipes.databinding.RecipeListViewBinding

class BrowseRecipeAdapter (
    private val recipeList: ArrayList<Recipe>
) : RecyclerView.Adapter<BrowseRecipeAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: RecipeListViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Recipe) {
            binding.title.text = recipe.title
            binding.ingredients.text = recipe.ingredients.joinToString(separator = ", ")

            // Glide.with(binding.imageViewBanner.context)
            //    .load(article.imageUrl)
            //    .into(binding.imageViewBanner)
            // itemView.setOnClickListener {
            //    val builder = CustomTabsIntent.Builder()
            //    val customTabsIntent = builder.build()
            //    customTabsIntent.launchUrl(it.context, Uri.parse(article.url))
            // }
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