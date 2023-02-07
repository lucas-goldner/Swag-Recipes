package de.hdmstuttgart.swagrecipes.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.GridLayoutManager;
import de.hdmstuttgart.swagrecipes.data.model.ingredient.Ingredient
import de.hdmstuttgart.swagrecipes.databinding.IngredientListViewBinding

class RecipeDetailAdapter (
    private val ingredientList: List<Ingredient>
) : RecyclerView.Adapter<RecipeDetailAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: IngredientListViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ingredient: Ingredient) {
            binding.title.text = ingredient.name
            binding.amountAndUnit.text = "${if (ingredient.amount % 1 != 0.0) "%.2f".format(ingredient.amount) else ingredient.amount.toInt()} ${ingredient.unit}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            IngredientListViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = ingredientList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(ingredientList[position])

}