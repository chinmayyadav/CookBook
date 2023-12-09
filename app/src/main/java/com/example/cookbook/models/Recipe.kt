package com.example.cookbook.models
data class Recipe(
    val title: String = "",
    val ingredients: List<String> = emptyList(),
    val directions: List<String> = emptyList(),
    val imageLink: String? = "",
    val youtubeLink: String? = null


)