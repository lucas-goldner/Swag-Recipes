package de.hdmstuttgart.swagrecipes.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import de.hdmstuttgart.swagrecipes.data.repository.RandomRecipeRepository
import de.hdmstuttgart.swagrecipes.data.repository.RecipeInformationByIdRepository
import de.hdmstuttgart.swagrecipes.di.ActivityContext
import de.hdmstuttgart.swagrecipes.ui.ViewModelProviderFactory
import de.hdmstuttgart.swagrecipes.ui.browse.BrowseRecipeAdapter
import de.hdmstuttgart.swagrecipes.ui.browse.BrowseRecipeViewModel

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @ActivityContext
    @Provides
    fun provideContext(): Context {
        return activity
    }

    //@Provides
    //fun provideRecipeInformationViewModel(recipeInformationRepository: RecipeInformationByIdRepository): TopHeadlineViewModel {
    //    return ViewModelProvider(activity,
    //        ViewModelProviderFactory(TopHeadlineViewModel::class) {
    //            TopHeadlineViewModel(topHeadlineRepository)
    //        })[TopHeadlineViewModel::class.java]
    //}

    @Provides
    fun provideRandomRecipeViewModel(randomRecipeRepository: RandomRecipeRepository): BrowseRecipeViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(BrowseRecipeViewModel::class) {
                BrowseRecipeViewModel(randomRecipeRepository)
            })[BrowseRecipeViewModel::class.java]
    }

    @Provides
    fun provideRandomRecipeAdapter() = BrowseRecipeAdapter(ArrayList())

}