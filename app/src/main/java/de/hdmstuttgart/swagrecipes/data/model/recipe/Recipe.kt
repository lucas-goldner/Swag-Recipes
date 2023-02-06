package de.hdmstuttgart.swagrecipes.data.model.recipe

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import de.hdmstuttgart.swagrecipes.data.model.ingredient.Ingredient
import de.hdmstuttgart.swagrecipes.data.model.recipe.Recipe.Companion.CREATOR

@Entity(tableName = "recipe_table")
data class Recipe (
    @PrimaryKey val id: String,
    val title: String,
    val ingredients: List<Ingredient>,
    val imageURL: String,
    val instructions: String,
    val readyInMinutes: Int,
    val vegetarian: Boolean,
    val vegan: Boolean,
    val glutenFree: Boolean,
    val dairyFree: Boolean,
    val veryHealthy: Boolean,
    val veryPopular: Boolean,
    val cheap: Boolean,
    val ketogenic: Boolean,
    val servings: Int
) : Parcelable{
    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<Recipe> {
            override fun createFromParcel(parcel: Parcel): Recipe {
                return Recipe(parcel)
            }

            override fun newArray(size: Int): Array<Recipe?> {
                return arrayOfNulls(size)
            }
        }
    }
    constructor(parcel: Parcel) : this(
        id = parcel.readString() ?: "",
        title = parcel.readString() ?: "",
        ingredients = parcel.createTypedArrayList(Ingredient.CREATOR) ?: emptyList(),
        imageURL = parcel.readString() ?: "",
        instructions = parcel.readString() ?: "",
        readyInMinutes = parcel.readInt(),
        vegetarian = parcel.readInt() == 1,
        vegan = parcel.readInt() == 1,
        glutenFree = parcel.readInt() == 1,
        dairyFree = parcel.readInt() == 1,
        veryHealthy = parcel.readInt() == 1,
        veryPopular = parcel.readInt() == 1,
        cheap = parcel.readInt() == 1,
        ketogenic = parcel.readInt() == 1,
        servings = parcel.readInt()
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeTypedList(ingredients)
        parcel.writeString(imageURL)
        parcel.writeString(instructions)
        parcel.writeInt(readyInMinutes)
        parcel.writeInt(if (vegetarian) 1 else 0)
        parcel.writeInt(if (vegan) 1 else 0)
        parcel.writeInt(if (glutenFree) 1 else 0)
        parcel.writeInt(if (dairyFree) 1 else 0)
        parcel.writeInt(if (veryHealthy) 1 else 0)
        parcel.writeInt(if (veryPopular) 1 else 0)
        parcel.writeInt(if (cheap) 1 else 0)
        parcel.writeInt(if (ketogenic) 1 else 0)
        parcel.writeInt(servings)
    }

    override fun describeContents(): Int {
        return 0
    }

}