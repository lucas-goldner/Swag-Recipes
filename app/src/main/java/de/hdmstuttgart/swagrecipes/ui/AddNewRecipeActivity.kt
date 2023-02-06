package de.hdmstuttgart.swagrecipes.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.Constraint
import de.hdmstuttgart.swagrecipes.R
import de.hdmstuttgart.swagrecipes.data.model.recipe.Recipe
import de.hdmstuttgart.swagrecipes.databinding.ActivityAddNewRecipeBinding


class AddNewRecipeActivity : AppCompatActivity() {


    private lateinit var binding: ActivityAddNewRecipeBinding

    private lateinit var titleInput: EditText
    private lateinit var instructionsInput: EditText
    private lateinit var readyInMinutesInput: EditText
    private lateinit var vegetarianCheckbox: CheckBox
    private lateinit var veganCheckbox: CheckBox


    var inputCounter = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityAddNewRecipeBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_add_new_recipe)

        titleInput = binding.titleInput
        instructionsInput = binding.instructionsInput
        readyInMinutesInput = binding.readyInMinutesInput
        vegetarianCheckbox = binding.vegetarianCheckbox
        veganCheckbox = binding.veganCheckbox


        binding.doneButton.setOnClickListener {
            /*val recipe = Recipe(
                title = titleInput.text.toString(),
                instructions = instructionsInput.text.toString(),
                readyInMinutes = readyInMinutesInput.text.toString().toInt(),
                vegetarian = vegetarianCheckbox.isChecked,
                vegan = veganCheckbox.isChecked
            )
             */
        }
        //binding.addMoreIngredients.setOnClickListener { addInputFields() }


    }



    fun addInputFields(view: View) {

        val inflater = LayoutInflater.from(this)
        val newLinearLayout = inflater.inflate(R.layout.input_fields_layout, binding.ingredientsLinearLayout, false) as ConstraintLayout
        newLinearLayout.id = inputCounter
        binding.ingredientsLinearLayout.addView(newLinearLayout)

        val ingredientInput = newLinearLayout.findViewById<EditText>(R.id.ingredientInput)
        val ingredientAmountInput = newLinearLayout.findViewById<EditText>(R.id.ingredientAmountInput)
        val ingredientUnitInput = newLinearLayout.findViewById<EditText>(R.id.ingredientUnitInput)

        inputCounter++
    }
}