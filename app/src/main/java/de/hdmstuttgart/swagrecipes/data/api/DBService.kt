package de.hdmstuttgart.swagrecipes.data.api

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.hdmstuttgart.swagrecipes.data.model.recipe.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface DBService {
    @Query("SELECT * FROM recipe_table ORDER BY title ASC")
    fun getRecipes(): Flow<List<Recipe>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(recipe: Recipe)
}