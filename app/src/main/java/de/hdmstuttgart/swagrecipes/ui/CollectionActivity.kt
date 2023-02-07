package de.hdmstuttgart.swagrecipes.ui

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.DecelerateInterpolator
import androidx.activity.viewModels
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import de.hdmstuttgart.swagrecipes.R
import de.hdmstuttgart.swagrecipes.SwagRecipesApplication
import de.hdmstuttgart.swagrecipes.ui.collection.CollectionViewModel
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import de.hdmstuttgart.swagrecipes.databinding.ActivityCollectionBinding
import de.hdmstuttgart.swagrecipes.providers.ViewModelProviderFactory
import de.hdmstuttgart.swagrecipes.ui.collection.CollectionAdapter

class CollectionActivity : AppCompatActivity() {
    private var contentHasLoaded = false
    private lateinit var binding: ActivityCollectionBinding
    lateinit var adapter: CollectionAdapter
    private val collectionViewModel by viewModels<CollectionViewModel> {
        ViewModelProviderFactory(CollectionViewModel::class) {
            CollectionViewModel(
                (application as SwagRecipesApplication).savedRecipesRepository
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
        contentHasLoaded = true
        setupSplashScreen(splashScreen)
        binding = ActivityCollectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
    }


    private fun setupUI() {
        val searchForMoreButton = binding.floatingActionButtonBrowseRecipe
        searchForMoreButton.setOnClickListener {
            navigateToBrowseActivity()
        }
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
        binding.floatingActionButtonAddRecipe.setOnClickListener {
            navigateToAddNewRecipeActivity()
        }
    }

    private fun navigateToAddNewRecipeActivity() {
        val intent = Intent(this, AddNewRecipeActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToBrowseActivity() {
        val browseIntent = Intent(this, BrowseActivity::class.java)
        startActivity(browseIntent)
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