package com.example.skincarechecker

import com.google.gson.annotations.SerializedName

data class IngredientData(
    @SerializedName("title") val name: String,
    @SerializedName("body") val description: String,
    val skinType: String = "All Skin Types" // Dodajemo podrazumevani tag
)