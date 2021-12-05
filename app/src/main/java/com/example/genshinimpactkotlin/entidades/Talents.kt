package com.example.genshinimpactkotlin.entidades

import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@Parcelize
@IgnoreExtraProperties
data class Talents(
    var combat1: Combat1? = null,
    var combat2: Combat2? = null,
    var combat3: Combat3? = null,
    var passive1: Passive1? = null,
    var passive2: Passive2? = null,
    var passive3: Passive3? = null,
    /*
    var name: String? = null,
    var costs: Costs
    */
) : Parcelable


@Parcelize
@IgnoreExtraProperties
data class Combat1(
    // var attributes: Attributes,
    var info: String? = null,
    var name: String? = null
) : Parcelable


@Parcelize
@IgnoreExtraProperties
data class Combat2(
    var description: String? = null,
    var info: String? = null,
    var name: String? = null
    // var attributes: AttributesX,
) : Parcelable


@Parcelize
@IgnoreExtraProperties
data class Combat3(
    var description: String? = null,
    var info: String? = null,
    var name: String? = null
    // var attributes: AttributesXX,
) : Parcelable


@Parcelize
@IgnoreExtraProperties
data class Passive1(
    var info: String? = null,
    var name: String? = null
) : Parcelable


@Parcelize
@IgnoreExtraProperties
data class Passive2(
    var info: String? = null,
    var name: String? = null
) : Parcelable


@Parcelize
@IgnoreExtraProperties
data class Passive3(
    var info: String? = null,
    var name: String? = null
) : Parcelable

/* Clases no utilizadas
data class Costs(
    var lvl10: List<Lvl10>,
    var lvl2: List<Lvl2>,
    var lvl3: List<Lvl3>,
    var lvl4: List<Lvl4>,
    var lvl5: List<Lvl5>,
    var lvl6: List<Lvl6>,
    var lvl7: List<Lvl7>,
    var lvl8: List<Lvl8>,
    var lvl9: List<Lvl9>
) : Parcelable

data class Attributes(
    var labels: List<String>
) : Parcelable

data class AttributesX(
    var labels: List<String>
) : Parcelable

data class AttributesXX(
    var labels: List<String>
) : Parcelable

data class Lvl10(
    var count: Int,
    var name: String
) : Parcelable

data class Lvl2(
    var count: Int,
    var name: String
) : Parcelable

data class Lvl3(
    var count: Int,
    var name: String
) : Parcelable

data class Lvl4(
    var count: Int,
    var name: String
) : Parcelable

data class Lvl5(
    var count: Int,
    var name: String
) : Parcelable

data class Lvl6(
    var count: Int,
    var name: String
) : Parcelable

data class Lvl7(
    var count: Int,
    var name: String
) : Parcelable

data class Lvl8(
    var count: Int,
    var name: String
) : Parcelable

data class Lvl9(
    var count: Int,
    var name: String
) : Parcelable
*/