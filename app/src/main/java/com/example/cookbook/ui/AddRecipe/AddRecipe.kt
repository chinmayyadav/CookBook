package com.example.cookbook.ui.AddRecipe

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.cookbook.R
import com.example.cookbook.databinding.FragmentAddRecipeBinding
import com.example.cookbook.models.Recipe
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.FirebaseDatabase


class AddRecipe : Fragment() {
    private lateinit var binding: FragmentAddRecipeBinding
    private lateinit var viewModel: AddRecipeViewModel // Create ViewModel if needed
    private var ingredientCounter = 1
    private var ingredientsLayout: LinearLayout? = null
    private var btnAddIngredient: Button? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel if needed
        // viewModel = ViewModelProvider(this).get(AddRecipeViewModel::class.java)
        ingredientsLayout = view.findViewById(R.id.ingredientsLayout);
        btnAddIngredient = view.findViewById(R.id.btnAddIngredient);

        binding.btnAddIngredient.setOnClickListener {
            addIngredientRow()
        }


//        binding.btnAddRecipe.setOnClickListener {
//            addRecipe()
//        }
    }

    private fun addIngredientRow() {
        val inflater =
            context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val newRowView = inflater.inflate(R.layout.row_ingredient, null)

        val editIngredient = newRowView.findViewById<TextInputEditText>(R.id.editIngredient)
        val editIngredientQty = newRowView.findViewById<TextInputEditText>(R.id.editIngredientQty)

        // Set unique IDs to avoid conflicts
        editIngredient.id = View.generateViewId()
        editIngredientQty.id = View.generateViewId()

        // You can customize the hints or other attributes if needed
        editIngredient.hint = "Enter ingredient"
        editIngredientQty.hint = "Enter quantity"

        // Add the new row to the ingredientsLayout
        ingredientsLayout?.addView(newRowView)
    }

    private fun addNewIngredientView() {
        val newIngredientLayout = TextInputLayout(requireContext())
        val newIngredientEditText = TextInputEditText(requireContext())

        val ingredientRow = layoutInflater.inflate(R.layout.ingredient_row, null)

        ingredientRow.findViewById<EditText>(R.id.editIngredientname).id = View.generateViewId()
        ingredientRow.findViewById<EditText>(R.id.editIngredientqty1).id = View.generateViewId()

        // Set unique ID for the new EditText
        newIngredientEditText.id = View.generateViewId()

        newIngredientLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        newIngredientLayout.hint = "Ingredient ${ingredientCounter + 1}"

        newIngredientLayout.addView(newIngredientEditText)

        // Add the new ingredient view to the existing layout
        binding.ingredientsLayout.addView(newIngredientLayout)

        ingredientCounter++
    }

//    private fun addRecipe() {
//        val title = binding.editTitle.text.toString()
//        val ingredients = binding.editIngredients.text.toString()
//        val instructions = binding.editDirections.text.toString()
//
//        // Validate the input if needed
//
//        // Create a Recipe object
//        val recipe = Recipe(
//            title = title,
//            ingredients = ingredients,
//            directions = instructions
//            // Add other properties as needed
//        )
//
//        // Call a function to add the recipe to Firebase
//        addRecipeToFirebase(recipe)
//    }

    private fun addRecipeToFirebase(recipe: Recipe) {
        // Use Realtime Database
        val database = FirebaseDatabase.getInstance()
        val recipesReference = database.getReference("recipe_dataset_final")

        // Generate a unique key for the new recipe
        val recipeKey = recipesReference.push().key

        // Set the recipe data in the "recipes" node with the generated key
        recipeKey?.let {
            recipesReference.child(it).setValue(recipe)
                .addOnSuccessListener {
                    // Recipe added successfully
                    Toast.makeText(requireContext(), "Recipe added successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    // Handle failure, e.g., log an error
                    Log.e("AddRecipeFragment", "Error adding recipe", e)
                    Toast.makeText(requireContext(), "Error adding recipe", Toast.LENGTH_SHORT).show()
                }
        }
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        binding = null
//    }

}