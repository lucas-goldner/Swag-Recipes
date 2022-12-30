package de.hdmstuttgart.swagrecipes.di.component

import android.content.Context
import dagger.Component
import de.hdmstuttgart.swagrecipes.SwagRecipesApplication
import de.hdmstuttgart.swagrecipes.data.api.NetworkService
import de.hdmstuttgart.swagrecipes.data.repository.RandomRecipeRepository
import de.hdmstuttgart.swagrecipes.data.repository.RecipeInformationByIdRepository
import de.hdmstuttgart.swagrecipes.di.ApplicationContext
import de.hdmstuttgart.swagrecipes.di.module.ApplicationModule
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: SwagRecipesApplication)

    @ApplicationContext
    fun getContext(): Context

    fun getNetworkService(): NetworkService

    fun getRecipeInformationRepository(): RecipeInformationByIdRepository

    fun getRandomRecipeRepository(): RandomRecipeRepository
}