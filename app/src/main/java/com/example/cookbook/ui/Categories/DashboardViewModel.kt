package com.example.cookbook.ui.Categories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cookbook.models.Category
import com.google.firebase.ktx.Firebase


class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Categories Fragment"
    }

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> get() = _categories

//    init {
//        // Fetch categories from Firestore and update _categories LiveData
//        fetchCategoriesFromFirestore()
//    }

//    private fun fetchCategoriesFromFirestore() {
//        // Example code using Firestore
//        val db = Firebase.firestore
//        val categoriesCollection = db.collection("categories")
//
//        categoriesCollection.get()
//            .addOnSuccessListener { result ->
//                val categoryList = mutableListOf<Category>()
//                for (document in result) {
//                    val id = document.id
//                    val name = document.getString("name") ?: ""
//                    val category = Category(id, name)
//                    categoryList.add(category)
//                }
//
//                _categories.value = categoryList
//            }
//            .addOnFailureListener { exception ->
//                // Handle failure, e.g., log an error
//                Log.e("CategoriesViewModel", "Error getting categories", exception)
//            }
//    }

    val text: LiveData<String> = _text
}