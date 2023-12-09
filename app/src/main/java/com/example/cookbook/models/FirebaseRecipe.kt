package com.example.cookbook.models
import com.google.gson.annotations.SerializedName
import java.io.Serializable

import com.google.gson.Gson

data class FirebaseRecipe(
    @SerializedName("title") var title: String? = null,
    @SerializedName("NER") private var nerString: String? = null,
    @SerializedName("directions") private var directionsString: String? = null,
    @SerializedName("imageLink") var imageLink: String? = null,
    @SerializedName("ingredients") private var ingredientsString: String? = null,
    @SerializedName("youtubeLink") var youtubeLink: String? = null
) : Serializable {
    val ner: List<String> get() = parseStringToList(nerString)
    val directions: List<String> get() = parseStringToList(directionsString)
    val ingredients: List<String> get() = parseStringToList(ingredientsString)

    private fun parseStringToList(jsonString: String?): List<String> {
        return jsonString?.let {
            Gson().fromJson(it, Array<String>::class.java).toList()
        } ?: emptyList()
    }
}
