package com.example.cookbook.models
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FirebaseRecipe(
    @SerializedName("primaryID") var primaryID: String? = null,
    // @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    // @SerializedName("description") var description: String? = null,
    // @SerializedName("stars") var stars: String? = null,
    // @SerializedName("length") var length: String? = null,
    // @SerializedName("image") var image: String? = null,
    // @SerializedName("year") var year: String? = null,
    // @SerializedName("rating") var rating: String? = null,
    // @SerializedName("director") var director: String? = null,
    // @SerializedName("url") var url: String? = null
) : Serializable
