package de.hdmstuttgart.swagrecipes.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import de.hdmstuttgart.swagrecipes.data.api.DBService
import de.hdmstuttgart.swagrecipes.data.model.ingredient.IngredientConverter
import de.hdmstuttgart.swagrecipes.data.model.recipe.Recipe

@Database(entities = [Recipe::class], version = 1, exportSchema = false)
@TypeConverters(IngredientConverter::class)
abstract class RecipeRoomDatabase : RoomDatabase() {

    abstract fun dbService(): DBService

    private class RecipeDatabaseCallback : Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let {

            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: RecipeRoomDatabase? = null

        fun getDatabase(context: Context): RecipeRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeRoomDatabase::class.java,
                    "recipe_database"
                ).addCallback(RecipeDatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}