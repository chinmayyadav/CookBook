package com.example.cookbook.models
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FirebaseRecipe(
    @SerializedName("title") var title: String? = null,
    @SerializedName("NER") var ner: List<String> = listOf(),
    @SerializedName("directions") var directions: List<String> = listOf(),
    @SerializedName("imageLink") var imageLink: String? = null,
    @SerializedName("ingredients") var ingredients: List<String> = listOf(),
    @SerializedName("youtubeLink") var youtubeLink: String? = null,
    @SerializedName("username") var username: String? = null
) : Serializable
