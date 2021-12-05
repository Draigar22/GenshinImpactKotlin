package com.example.genshinimpactkotlin.entidades

data class Artifact(
    val name: String? = null,
    val `1pc`: String? = null,
    val `2pc`: String? = null,
    val `4pc`: String? = null,
    val rarity: List<String>? = null,
    val circlet: Circlet? = null,
    val flower: Flower? = null,
    val goblet: Goblet? = null,
    val plume: Plume? = null,
    val sands: Sands? = null
)

data class Circlet(
    val description: String? = null,
    val name: String? = null,
    val relictype: String? = null
)

data class Flower (
    val description: String? = null,
    val name: String? = null,
    val relictype: String? = null
)

data class Goblet(
    val description: String? = null,
    val name: String? = null,
    val relictype: String? = null
)

data class Plume(
    val description: String? = null,
    val name: String? = null,
    val relictype: String? = null
)

data class Sands(
    val description: String? = null,
    val name: String? = null,
    val relictype: String? = null
)