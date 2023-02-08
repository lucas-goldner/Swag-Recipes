package de.hdmstuttgart.swagrecipes.data.model.ingredient

import android.os.Parcel
import android.os.Parcelable
import android.text.TextUtils
import androidx.room.Entity
import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import de.hdmstuttgart.swagrecipes.data.model.recipe.Recipe
import java.lang.reflect.Type

@Entity(tableName = "ingredients")
data class Ingredient(
    //val id: String,
    //val aisle: String,
    val name: String,
    val amount: Double,
    val unit: String,
    //val imageURL: String,
    //val metaInformation: List<String>
) : Parcelable {

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<Ingredient> {
            override fun createFromParcel(parcel: Parcel): Ingredient {
                return Ingredient(parcel)
            }

            override fun newArray(size: Int): Array<Ingredient?> {
                return arrayOfNulls(size)
            }
        }
    }
    constructor(parcel: Parcel) : this(
        //id = parcel.readString() ?: "",
        //aisle = parcel.readString() ?: "",
        name = parcel.readString() ?: "",
        amount = parcel.readDouble(),
        unit = parcel.readString() ?: "",
       //imageURL = parcel.readString() ?: "",
       //metaInformation = parcel.createStringArrayList() ?: listOf<String>()
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        //parcel.writeString(id)
        //parcel.writeString(aisle)
        parcel.writeString(name)
        parcel.writeDouble(amount)
        parcel.writeString(unit)
        //parcel.writeString(imageURL)
        //parcel.writeStringList(metaInformation)
    }

    override fun describeContents(): Int {
        return 0
    }
}

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