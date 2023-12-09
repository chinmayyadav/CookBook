package com.example.cookbook.models
data class Recipe(
    val id: String? = null,
    val title: String = "",
    val ingredients: List<String> = emptyList(),
    val directions: List<String> = emptyList(),
    val youtubeLink: String? = null


)