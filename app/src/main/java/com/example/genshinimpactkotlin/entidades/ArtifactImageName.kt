package com.example.genshinimpactkotlin.entidades

data class ArtifactImageName(
    val defaultName: String?,
    val languageName:String?,
    val `1pc`: String?,
    val `2pc`: String?,
    val `4pc`: String?,
    val rarity: List<String>?,
    val icon: String?
)