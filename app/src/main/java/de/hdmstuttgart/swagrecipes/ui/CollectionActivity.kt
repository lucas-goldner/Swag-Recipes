package de.hdmstuttgart.swagrecipes.ui

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.DecelerateInterpolator
import androidx.activity.viewModels
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.viewModels
import de.hdmstuttgart.swagrecipes.R
import de.hdmstuttgart.swagrecipes.SwagRecipesApplication
import de.hdmstuttgart.swagrecipes.data.RecipeRoomDatabase
import de.hdmstuttgart.swagrecipes.data.model.ingredient.Ingredient
import de.hdmstuttgart.swagrecipes.data.model.recipe.Recipe
import de.hdmstuttgart.swagrecipes.data.repository.SavedRecipesRepository
import de.hdmstuttgart.swagrecipes.ui.collection.CollectionViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import androidx.fragment.app.viewModels
import androidx.lifecycle.asFlow
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import de.hdmstuttgart.swagrecipes.data.api.DBService
import de.hdmstuttgart.swagrecipes.databinding.ActivityBrowseBinding
import de.hdmstuttgart.swagrecipes.databinding.ActivityCollectionBinding
import de.hdmstuttgart.swagrecipes.ui.browse.BrowseRecipeAdapter
import de.hdmstuttgart.swagrecipes.ui.collection.CollectionAdapter

class CollectionActivity : AppCompatActivity() {
    private var contentHasLoaded = false
    private lateinit var binding: ActivityCollectionBinding
    lateinit var adapter: CollectionAdapter
    private val collectionViewModel by viewModels<CollectionViewModel> { ViewModelProviderFactory(CollectionViewModel::class) {
        CollectionViewModel(
            (application as SwagRecipesApplication).repository
        )
    }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)
        adapter = CollectionAdapter()
        collectionViewModel.allRecipes.observe(this) { recipes ->
            recipes?.let { adapter.submitList(it) }
        }
        // TODO: Load data before setup
        contentHasLoaded = true
        setupSplashScreen(splashScreen)
        binding = ActivityCollectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
    }


    private fun setupUI() {
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    private fun setupSplashScreen(splashScreen: SplashScreen) {
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return if (contentHasLoaded) {
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else false
                }
            }
        )

        splashScreen.setOnExitAnimationListener { splashScreenView ->
            val slideBack = ObjectAnimator.ofFloat(
                splashScreenView.view,
                View.TRANSLATION_Y,
                0f,
                -splashScreenView.view.height.toFloat()
            ).apply {
                interpolator = DecelerateInterpolator()
                duration = 500L
                doOnEnd { splashScreenView.remove() }
            }

            slideBack.start()
        }
    }
}