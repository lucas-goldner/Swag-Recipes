package de.hdmstuttgart.swagrecipes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import de.hdmstuttgart.swagrecipes.R
import de.hdmstuttgart.swagrecipes.SwagRecipesApplication
import de.hdmstuttgart.swagrecipes.data.model.recipe.Recipe
import de.hdmstuttgart.swagrecipes.databinding.ActivityBrowseBinding
import de.hdmstuttgart.swagrecipes.providers.ViewModelProviderFactory
import de.hdmstuttgart.swagrecipes.ui.browse.BrowseRecipeAdapter
import de.hdmstuttgart.swagrecipes.ui.browse.BrowseRecipeViewModel
import de.hdmstuttgart.swagrecipes.utils.Status
import kotlinx.coroutines.launch

class BrowseActivity : AppCompatActivity() {
    var adapter: BrowseRecipeAdapter = BrowseRecipeAdapter(ArrayList())
    private lateinit var binding: ActivityBrowseBinding
    private val browseRecipeViewModel by viewModels<BrowseRecipeViewModel> {
        ViewModelProviderFactory(BrowseRecipeViewModel::class) {
            BrowseRecipeViewModel(
                (application as SwagRecipesApplication).randomRecipeRepository
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse)
        binding = ActivityBrowseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        setupObserver()
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

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                browseRecipeViewModel.recipeList.collect {
                    when (it.status) {
                        Status.SUCCESS -> {
                            binding.progressBar.visibility = View.GONE
                            it.data?.let { articleList -> renderList(articleList) }
                            binding.recyclerView.visibility = View.VISIBLE
                        }
                        Status.LOADING -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.recyclerView.visibility = View.GONE
                        }
                        Status.ERROR -> {
                            //Handle Error
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this@BrowseActivity, it.message, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun renderList(articleList: List<Recipe>) {
        adapter.addData(articleList)
        adapter.notifyDataSetChanged()
    }
}