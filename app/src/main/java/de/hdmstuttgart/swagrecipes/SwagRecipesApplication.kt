package de.hdmstuttgart.swagrecipes

import android.app.Application
import de.hdmstuttgart.swagrecipes.data.RecipeRoomDatabase
import de.hdmstuttgart.swagrecipes.data.repository.RandomRecipeRepository
import de.hdmstuttgart.swagrecipes.data.repository.SavedRecipesRepository
import de.hdmstuttgart.swagrecipes.providers.NetworkProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class SwagRecipesApplication : Application() {
    private val database by lazy { RecipeRoomDatabase.getDatabase(this) }
    private val network by lazy { NetworkProvider.getInstance() }
    val randomRecipeRepository by lazy { RandomRecipeRepository(network) }
    val savedRecipesRepository by lazy { SavedRecipesRepository(database.dbService()) }

    override fun onCreate() {
        super.onCreate()

    }
}