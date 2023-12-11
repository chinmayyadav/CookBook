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
import com.example.cookbook.models.Ingredient
import com.example.cookbook.models.Recipe
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.UUID
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.provider.MediaStore
import android.net.Uri
import android.widget.ImageView
import com.example.cookbook.models.FirebaseRecipe
import com.google.firebase.auth.FirebaseAuth


class AddRecipe : Fragment() {
    private lateinit var binding: FragmentAddRecipeBinding
    private lateinit var viewModel: AddRecipeViewModel // Create ViewModel if needed
    private var ingredientCounter = 1
    private var ingredientsLayout: LinearLayout? = null
    private var btnAddIngredient: Button? = null
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private val PICK_IMAGE_REQUEST_CODE = 1
    private val MAX_INGREDIENTS = 20
    private var imageUri: Uri? = null
    private lateinit var imgRecipe: ImageView

    private lateinit var title: String
    private lateinit var ingredientsList: List<Ingredient>
    private lateinit var directions: String
    private lateinit var youTubeLink: String



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_recipe, container, false)
        binding = FragmentAddRecipeBinding.inflate(inflater, container, false)
        imgRecipe = binding.imgRecipe

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().reference.child("recipes")
        // Initialize Firebase Storage reference
        storageReference = FirebaseStorage.getInstance().reference.child("recipe_images")

//        imgRecipe = view.findViewById(R.id.imgRecipe)
        val btnAddRecipe: Button = binding.btnAddRecipe
        btnAddRecipe.setOnClickListener {
            addRecipeToFirebase()
        }

        val btnUploadImage: Button = binding.btnUploadImage
        btnUploadImage.setOnClickListener {
            openFileChooser()
        }
        binding = FragmentAddRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel if needed
        // viewModel = ViewModelProvider(this).get(AddRecipeViewModel::class.java)
        ingredientsLayout = view.findViewById(R.id.ingredientsLayout);
        btnAddIngredient = view.findViewById(R.id.btnAddIngredient);
        databaseReference = FirebaseDatabase.getInstance().reference.child("favorites")
        storageReference = FirebaseStorage.getInstance().reference.child("recipe_images")



        binding.btnAddIngredient.setOnClickListener {
            addIngredientRow()
        }

        val btnAddRecipe: Button = view.findViewById(R.id.btnAddRecipe)
        btnAddRecipe.setOnClickListener {
            addRecipeToFirebase()
            clearForm()
        }

        val btnUploadImage: Button = binding.btnUploadImage
        btnUploadImage.setOnClickListener {
            openFileChooser()
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
//            // The user has successfully picked an image
//            val selectedImageUri: Uri = data.data!!
//
//            // Now you can use selectedImageUri for your upload
//            // Call the function with the obtained imageUri
//            uploadImageToFirebase(selectedImageUri)
//        }
//    }

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



//    private fun addRecipeToFirebase(recipe: Recipe) {
//        // Use Realtime Database
//        val database = FirebaseDatabase.getInstance()
//        val recipesReference = database.getReference("recipe_dataset_final")
//
//        // Generate a unique key for the new recipe
//        val recipeKey = recipesReference.push().key
//
//        // Set the recipe data in the "recipes" node with the generated key
//        recipeKey?.let {
//            recipesReference.child(it).setValue(recipe)
//                .addOnSuccessListener {
//                    // Recipe added successfully
//                    Toast.makeText(requireContext(), "Recipe added successfully", Toast.LENGTH_SHORT).show()
//                }
//                .addOnFailureListener { e ->
//                    // Handle failure, e.g., log an error
//                    Log.e("AddRecipeFragment", "Error adding recipe", e)
//                    Toast.makeText(requireContext(), "Error adding recipe", Toast.LENGTH_SHORT).show()
//                }
//        }
//    }

    private fun getLoggedInUsername(): String? {
        val user = FirebaseAuth.getInstance().currentUser
        return user?.getEmail().toString()
    }


    val username = getLoggedInUsername()
    private fun addRecipeToFirebase() {
        title = binding?.editTitle?.text.toString()
        directions = binding?.editDirections?.text.toString()
        youTubeLink = binding?.editYouTubeLink?.text.toString()

        // Collect ingredients from dynamically added rows
        ingredientsLayout = view?.findViewById<LinearLayout>(R.id.ingredientsLayout)
        val ingredients = mutableListOf<String>()


        for (i in 0 until ingredientsLayout?.childCount!!) {
            ingredientsLayout!!.getChildAt(i)?.let { childView ->
                val ingredientLayout = childView as? LinearLayout
                val ingredientEditText = ingredientLayout?.findViewById<TextInputEditText>(R.id.editIngredient1)
                val ingredient = ingredientEditText?.text?.toString()
                ingredient?.let { ingredients.add(it) }

            }
        }
        val recipe = FirebaseRecipe(
            title = title,
            directions = directions.split("\n"),
            youtubeLink = youTubeLink,
            ingredients = ingredients,
            username = getLoggedInUsername()
        )

        val recipeKey = databaseReference.push().key
        recipeKey?.let {
            databaseReference.child(it).setValue(recipe)
        }

        // Upload image to Firebase Storage
        uploadImageToFirebaseStorage(recipeKey)
    }
//        for (i in 1..MAX_INGREDIENTS) {
//            val ingredientName = view?.findViewById<TextInputEditText>(
//                resources.getIdentifier(
//                    "editIngredient$i",
//                    "id",
//                    requireActivity().packageName
//                )
//            )?.text.toString()
//            val ingredientQty = view?.findViewById<TextInputEditText>(
//                resources.getIdentifier(
//                    "editIngredientqty$i",
//                    "id",
//                    requireActivity().packageName
//                )
//            )?.text.toString()
//
//            if (ingredientName.isNotEmpty() && ingredientQty.isNotEmpty()) {
//                ingredientsList.add(Ingredient(ingredientName, ingredientQty))
//            }
//        }

//         Upload image to Firebase Storage
//        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE)

//    }

    // Handle the result of image selection
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
//            val selectedImageUri: Uri = data.data!!
//
//            // Continue with the rest of the upload process
//            uploadImageToFirebase(selectedImageUri)
//        }
//    }

    private fun uploadImageToFirebaseStorage(recipeKey: String?) {
        if (imageUri != null && recipeKey != null) {
            val imageRef = storageReference.child("$recipeKey.jpg")

            imageRef.putFile(imageUri!!)
                .addOnSuccessListener {
                    // Image uploaded successfully
                    imageRef.downloadUrl.addOnSuccessListener { uri ->
                        // Image URL obtained, update Firebase Database with the image link
                        databaseReference.child(recipeKey).child("imageLink").setValue(uri.toString())
                        Toast.makeText(context, "RECIPE ADDED TO YOUR FAVOURITES", Toast.LENGTH_LONG).show()
                    }
                }
                .addOnFailureListener {
                    // Handle unsuccessful uploads
                }
        }
    }

    private fun openFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.data != null) {
            imageUri = data.data
            imgRecipe.setImageURI(imageUri)
        }
    }


//    private fun uploadImageToFirebase(
//        imageUri: Uri
//    ) {
//        val imageRef = storageReference.child(UUID.randomUUID().toString())
//        val uploadTask = imageRef.putFile(imageUri)
//
//        // Listen for the completion of the upload task
//        uploadTask.continueWithTask { task ->
//            if (!task.isSuccessful) {
//                task.exception?.let {
//                    throw it
//                }
//            }
//            imageRef.downloadUrl
//        }.addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                val imageDownloadUrl = task.result.toString()
//
//                // Create a Recipe object
//                val recipe = Recipe(title, ingredientsList, directions, youTubeLink, imageDownloadUrl)
//
//                // Push the recipe to Firebase Database
//                val recipeKey = databaseReference.push().key
//                recipeKey?.let {
//                    databaseReference.child(it).setValue(recipe)
//                }
//
//                // Optionally, clear the form or navigate to another screen
//                clearForm()
//            } else {
//                // Handle the error
//                Log.e(TAG, "Image upload failed: ${task.exception}")
//            }
//        }
//    }



    private fun clearForm() {
        // Clear the form fields after successful submission
        binding.editTitle?.text?.clear()
        binding.editDirections?.text?.clear()
        binding.editYouTubeLink?.text?.clear()

        // Clear dynamically added ingredient rows
        for (i in 1..MAX_INGREDIENTS) {
            view?.findViewById<TextInputEditText>(resources.getIdentifier("editIngredient$i", "id", requireActivity().packageName))?.text?.clear()
            view?.findViewById<TextInputEditText>(resources.getIdentifier("editIngredientqty$i", "id", requireActivity().packageName))?.text?.clear()
        }
    }

//    companion object {
//        private const val MAX_INGREDIENTS = 5
//        private const val TAG = "AddRecipeFragment"
//    }
}

//    override fun onDestroyView() {
//        super.onDestroyView()
//        binding = null
//    }

