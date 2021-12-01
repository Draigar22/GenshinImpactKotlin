package com.example.genshinimpactkotlin.dto

import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Parcelize
@IgnoreExtraProperties
data class Talents(
    val combat1: Combat1? = null,
    val combat2: Combat2? = null,
    val combat3: Combat3? = null,
    val passive1: Passive1? = null,
    val passive2: Passive2? = null,
    val passive3: Passive3? = null,
    /*
    val name: String? = null,
    val costs: Costs
    */
) : Parcelable


@Parcelize
@IgnoreExtraProperties
data class Combat1(
    // val attributes: Attributes,
    val info: String? = null,
    val name: String? = null
) : Parcelable


@Parcelize
@IgnoreExtraProperties
data class Combat2(
    val description: String? = null,
    val info: String? = null,
    val name: String? = null
    // val attributes: AttributesX,
) : Parcelable


@Parcelize
@IgnoreExtraProperties
data class Combat3(
    val description: String? = null,
    val info: String? = null,
    val name: String? = null
    // val attributes: AttributesXX,
) : Parcelable


@Parcelize
@IgnoreExtraProperties
data class Passive1(
    val info: String? = null,
    val name: String? = null
) : Parcelable


@Parcelize
@IgnoreExtraProperties
data class Passive2(
    val info: String? = null,
    val name: String? = null
) : Parcelable


@Parcelize
@IgnoreExtraProperties
data class Passive3(
    val info: String? = null,
    val name: String? = null
) : Parcelable

/* Clases no utilizadas
data class Costs(
    val lvl10: List<Lvl10>,
    val lvl2: List<Lvl2>,
    val lvl3: List<Lvl3>,
    val lvl4: List<Lvl4>,
    val lvl5: List<Lvl5>,
    val lvl6: List<Lvl6>,
    val lvl7: List<Lvl7>,
    val lvl8: List<Lvl8>,
    val lvl9: List<Lvl9>
) : Parcelable

data class Attributes(
    val labels: List<String>
) : Parcelable

data class AttributesX(
    val labels: List<String>
) : Parcelable

data class AttributesXX(
    val labels: List<String>
) : Parcelable

data class Lvl10(
    val count: Int,
    val name: String
) : Parcelable

data class Lvl2(
    val count: Int,
    val name: String
) : Parcelable

data class Lvl3(
    val count: Int,
    val name: String
) : Parcelable

data class Lvl4(
    val count: Int,
    val name: String
) : Parcelable

data class Lvl5(
    val count: Int,
    val name: String
) : Parcelable

data class Lvl6(
    val count: Int,
    val name: String
) : Parcelable

data class Lvl7(
    val count: Int,
    val name: String
) : Parcelable

data class Lvl8(
    val count: Int,
    val name: String
) : Parcelable

data class Lvl9(
    val count: Int,
    val name: String
) : Parcelable
*/