package de.hdmstuttgart.swagrecipes

import android.app.Application
import de.hdmstuttgart.swagrecipes.data.RecipeRoomDatabase
import de.hdmstuttgart.swagrecipes.data.repository.SavedRecipesRepository
import de.hdmstuttgart.swagrecipes.di.component.ApplicationComponent
import de.hdmstuttgart.swagrecipes.di.component.DaggerApplicationComponent
import de.hdmstuttgart.swagrecipes.di.module.ApplicationModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class SwagRecipesApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())
    lateinit var applicationComponent: ApplicationComponent
    private val database by lazy { RecipeRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { SavedRecipesRepository(database.dbService()) }

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
    }

    private fun injectDependencies() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)
    }
}