package de.hdmstuttgart.swagrecipes.ui

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import de.hdmstuttgart.swagrecipes.SwagRecipesApplication
import de.hdmstuttgart.swagrecipes.data.model.ingredient.Ingredient
import de.hdmstuttgart.swagrecipes.data.model.recipe.Recipe
import de.hdmstuttgart.swagrecipes.databinding.ActivityAddNewRecipeBinding
import de.hdmstuttgart.swagrecipes.providers.ViewModelProviderFactory
import de.hdmstuttgart.swagrecipes.ui.addnew.AddNewRecipeViewModel
import de.hdmstuttgart.swagrecipes.ui.collection.CollectionViewModel
import java.util.*
import kotlin.collections.ArrayList

/**
 * This class implements the AddNewRecipe Activity which allows the user to create his
 * own Recipe by filling out all of the needed attributes to create a Recipe
 */
class AddNewRecipeActivity : AppCompatActivity() {

    // This List stores all of the User recipes
    lateinit var userRecipes: ArrayList<Recipe>

    private lateinit var binding: ActivityAddNewRecipeBinding

    private val addNewRecipeViewModel by viewModels<AddNewRecipeViewModel> {
        ViewModelProviderFactory(AddNewRecipeViewModel::class) {
            AddNewRecipeViewModel(
                (application as SwagRecipesApplication).savedRecipesRepository
            )
        }
    }

    // Input Fields/Checkboxes
    private lateinit var titleInput: EditText
    private lateinit var instructionsInput: EditText
    private lateinit var readyInMinutesInput: EditText
    private lateinit var servingsInput: EditText
    private lateinit var vegetarianCheckbox: CheckBox
    private lateinit var veganCheckbox: CheckBox
    private lateinit var glutenFreeCheckbox: CheckBox
    private lateinit var dairyFreeCheckbox: CheckBox
    private lateinit var veryHealthyCheckbox: CheckBox
    private lateinit var cheapCheckbox: CheckBox
    private lateinit var ketogenicCheckbox: CheckBox


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Binding Input Fields/Checkboxes
        titleInput = binding.titleInput
        instructionsInput = binding.instructionsInput
        readyInMinutesInput = binding.readyInMinutesInput
        servingsInput = binding.servingsInput
        vegetarianCheckbox = binding.vegetarianCheckbox
        veganCheckbox = binding.veganCheckbox
        glutenFreeCheckbox = binding.glutenFreeCheckbox
        dairyFreeCheckbox = binding.dairyFreeCheckbox
        veryHealthyCheckbox = binding.isHealthyInput
        cheapCheckbox = binding.isCheapInput
        ketogenicCheckbox = binding.ketogenic

        // Adds more Input fields when button pressed
        binding.addMoreIngredients.setOnClickListener {
            addInputFields()
        }

        binding.doneButton.setOnClickListener {

            // Create Recipe
            val recipe = Recipe(
                id = UUID.randomUUID().toString(),
                title = titleInput.text.toString(),
                ingredients = getInputs(),
                imageURL = "NotImplementedYet",
                instructions = instructionsInput.text.toString(),
                readyInMinutes = readyInMinutesInput.text.toString().toInt(),
                vegetarian = vegetarianCheckbox.isChecked,
                vegan = veganCheckbox.isChecked,
                glutenFree = glutenFreeCheckbox.isChecked,
                dairyFree = dairyFreeCheckbox.isChecked,
                veryHealthy = veryHealthyCheckbox.isChecked,
                veryPopular = false,
                cheap = cheapCheckbox.isChecked,
                ketogenic = ketogenicCheckbox.isChecked,
                servings = servingsInput.text.toString().toInt()
                )

            addNewRecipeViewModel.insert(recipe)

            // Go back to CollectionPage
            finish()
        }
    }

    /**
     * This function creates a Linear Layout with 3 Input Fields inside of it. It represents
     * a single Ingredient input of the user. This "package" of input fields can be generated
     * as much as the user wants to
     */
    private fun addInputFields() {

        // Linear Layout
        val newInputFields = LinearLayout(this)
        newInputFields.orientation = LinearLayout.HORIZONTAL
        newInputFields.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        // Input field for Ingredient Name
        val ingredientInput = EditText(this)
        ingredientInput.id = View.generateViewId()
        ingredientInput.layoutParams = LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1f
        ).apply {
            setMargins(16.dpToPx(), 0, 16.dpToPx(), 0)
        }
        ingredientInput.hint = "Ingredient Name"
        ingredientInput.inputType = InputType.TYPE_CLASS_TEXT

        // Input field for Ingredient Amount
        val ingredientAmountInput = EditText(this)
        ingredientInput.id = View.generateViewId()
        ingredientAmountInput.layoutParams = LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            0.5f
        ).apply {
            setMargins(16.dpToPx(), 0, 16.dpToPx(), 0)
        }
        ingredientAmountInput.hint = "Amount"
        ingredientAmountInput.inputType = InputType.TYPE_CLASS_NUMBER

        // Input field for Ingredient Unit
        val ingredientUnitInput = EditText(this)
        ingredientInput.id = View.generateViewId()
        ingredientUnitInput.layoutParams = LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            0.5f
        ).apply {
            setMargins(16.dpToPx(), 0, 16.dpToPx(), 0)
        }
        ingredientUnitInput.hint = "Unit"
        ingredientUnitInput.inputType = InputType.TYPE_CLASS_TEXT

        newInputFields.addView(ingredientInput)
        newInputFields.addView(ingredientAmountInput)
        newInputFields.addView(ingredientUnitInput)

        // Add the whole Linear Layout with its 3 Input fields to the main layout
        binding.ingredientsLinearLayout.addView(newInputFields)
    }

    private fun Int.dpToPx(): Int {
        return (this * resources.displayMetrics.density + 0.5f).toInt()
    }

    /**
     * This function gets all of the inputs by the generated User input fields. It then
     * creates a Ingredient based on the Inputs and stores them in a list returning them afterwards
     */
    private fun getInputs(): List<Ingredient> {
        val ingredientList = mutableListOf<Ingredient>()

        for (i in 0 until binding.ingredientsLinearLayout.childCount) {
            val linearLayout = binding.ingredientsLinearLayout.getChildAt(i) as LinearLayout
            val ingredientInput = linearLayout.getChildAt(0) as EditText
            val ingredientAmountInput = linearLayout.getChildAt(1) as EditText
            val ingredientUnitInput = linearLayout.getChildAt(2) as EditText

            var ingredient = Ingredient(ingredientInput.text.toString(),ingredientAmountInput.text.toString().toDouble(),ingredientUnitInput.text.toString())
            ingredientList.add(ingredient)
        }
        return ingredientList
    }

}