package com.example.cookbook.models
//import android.util.Log
//import com.google.gson.annotations.SerializedName
//
////import com.google.gson.Gson
//
////data class FirebaseRecipe(
////    @SerializedName("title") var title: String? = null,
////    @SerializedName("NER") private var nerString: String? = null,
////    @SerializedName("directions") private var directionsString: String? = null,
////    @SerializedName("imageLink") var imageLink: String? = null,
////    @SerializedName("ingredients") private var ingredientsString: String? = null,
////    @SerializedName("youtubeLink") var youtubeLink: String? = null
////) : Serializable {
////    val ner: List<String> get() = parseStringToList(nerString)
////    val directions: List<String> get() = parseStringToList(directionsString)
////    val ingredients: List<String> get() = parseStringToList(ingredientsString)
////
////    private fun parseStringToList(jsonString: String?): List<String> {
////        return jsonString?.let {
////            Gson().fromJson(it, Array<String>::class.java).toList()
////        } ?: emptyList()
////    }
////}
//
////data class FirebaseRecipe(
////    @SerializedName("title") var title: String? = null,
////    @SerializedName("NER") private var nerString: String? = null,
////    @SerializedName("directions") private var directionsString: String? = null,
////    @SerializedName("imageLink") var imageLink: String? = null,
////    @SerializedName("ingredients") private var ingredientsString: String? = null,
////    @SerializedName("youtubeLink") var youtubeLink: String? = null
////) : Serializable {
////    val ner: List<String> get() = parseStringToList(nerString)
////    val directions: List<String> get() = parseStringToList(directionsString)
////    val ingredients: List<String> get() = parseStringToList(ingredientsString)
////
////    private fun parseStringToList(jsonString: String?): List<String> {
////        return jsonString?.let {
////            try {
////                Gson().fromJson(it, Array<String>::class.java).toList()
////            } catch (e: Exception) {
////                e.printStackTrace()
////                emptyList<String>()
////            }
////        } ?: emptyList()
////    }
////}
////
////import com.google.gson.Gson
////import com.google.gson.annotations.SerializedName
//import java.io.Serializable
//
//data class FirebaseRecipe(
//    @SerializedName("title") var title: String? = null,
//    @SerializedName("NER") private var nerString: String? = null,
//    @SerializedName("directions") private var directionsString: String? = null,
//    @SerializedName("imageLink") var imageLink: String? = null,
//    @SerializedName("ingredients") private var ingredientsString: String? = null,
//    @SerializedName("youtubeLink") var youtubeLink: String? = null
//) : Serializable {
//    val ner: List<String> get() = parseStringToList(nerString)
//    val directions: List<String> get() = parseStringToList(directionsString)
//    val ingredients: List<String> get() = parseStringToList(ingredientsString)
//
//    private fun parseStringToList(jsonString: String?): List<String> {
//        return jsonString?.let {
//            try {
//                // Logging the raw JSON string
//                Log.d("FirebaseRecipe", "Raw JSON String: $it")
//
//                // Manual parsing as an alternative approach
//                it.trim().removeSurrounding("[", "]")
//                    .split(",").map { str -> str.trim().trim('"') }
//            } catch (e: Exception) {
//                e.printStackTrace()
//                emptyList<String>()
//            }
//        } ?: emptyList()
//    }
//}

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FirebaseRecipe(
    @SerializedName("title") var title: String? = null,
    @SerializedName("NER") var ner: List<String> = listOf(),
    @SerializedName("directions") var directions: List<String> = listOf(),
    @SerializedName("imageLink") var imageLink: String? = null,
    @SerializedName("ingredients") var ingredients: List<String> = listOf(),
    @SerializedName("youtubeLink") var youtubeLink: String? = null,
    @SerializedName("username") var username: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("cuisineType") var cuisineType: String? = null,
) : Serializable
