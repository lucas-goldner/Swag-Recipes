package de.hdmstuttgart.swagrecipes.ui.collection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdmstuttgart.swagrecipes.R
import de.hdmstuttgart.swagrecipes.data.model.ingredient.Ingredient
import de.hdmstuttgart.swagrecipes.data.model.recipe.Recipe

class CollectionAdapter(  var onItemClick: ((Recipe) -> Unit)) :
    ListAdapter<Recipe, CollectionAdapter.RecipeViewHolder>(RecipeComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.title, current.ingredients, current.readyInMinutes)
        holder.itemView.setOnClickListener {
            onItemClick(current)
        }
        Glide.with(holder.itemView.context)
            .load(current.imageURL)
            .placeholder(R.drawable.placeholder)
            .into(holder.imageView)
    }

    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleView: TextView = itemView.findViewById(R.id.title)
        private val ingredientsView: TextView = itemView.findViewById(R.id.ingredients)
        private val readyInMinutesView: TextView = itemView.findViewById(R.id.readyInMinutes)
        val imageView: ImageView = itemView.findViewById(R.id.image)

        fun bind(title: String?, ingredients: List<Ingredient>, readyInMinutes: Int) {
            titleView.text = title
            ingredientsView.text = ingredients.joinToString(separator = ", ") { it.name }
            readyInMinutesView.text = "Prep time: " + readyInMinutes.toString() + " min"
        }

        companion object {
            fun create(parent: ViewGroup): RecipeViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recipe_list_view, parent, false)

                return RecipeViewHolder(view)
            }
        }
    }

    class RecipeComparator : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.id === newItem.id
        }

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.title == newItem.title && oldItem.instructions == newItem.instructions
        }
    }
}