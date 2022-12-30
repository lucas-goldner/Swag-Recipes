package de.hdmstuttgart.swagrecipes.data.model.ingredient

import android.text.TextUtils
import androidx.room.Entity
import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

@Entity(tableName = "ingredients")
data class Ingredient(
    val id: String,
    val aisle: String,
    val name: String,
    val amount: Double,
    val unit: String,
    val imageURL: String,
    val metaInformation: List<String>
)

object IngredientConverter {
    @TypeConverter
    fun toList(value: String?): List<Ingredient?>? {
        if (TextUtils.isEmpty(value))
            return listOf()
        val gson = Gson()
        val listType: Type = object :
            TypeToken<List<Ingredient?>?>() {}.type
        return gson.fromJson<List<Ingredient?>>(value, listType)
    }

    @TypeConverter
    fun toString(list: List<Ingredient?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}