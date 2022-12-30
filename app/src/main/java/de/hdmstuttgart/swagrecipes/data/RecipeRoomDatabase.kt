package de.hdmstuttgart.swagrecipes.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import de.hdmstuttgart.swagrecipes.data.api.DBService
import de.hdmstuttgart.swagrecipes.data.model.ingredient.Ingredient
import de.hdmstuttgart.swagrecipes.data.model.ingredient.IngredientConverter
import de.hdmstuttgart.swagrecipes.data.model.recipe.Recipe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Recipe::class], version = 1, exportSchema = false)
@TypeConverters(IngredientConverter::class)
public abstract class RecipeRoomDatabase : RoomDatabase() {

    abstract fun dbService(): DBService

    private class RecipeDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let {

            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: RecipeRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): RecipeRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeRoomDatabase::class.java,
                    "recipe_database"
                ).addCallback(RecipeDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}