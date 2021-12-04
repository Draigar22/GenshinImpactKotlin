package com.example.genshinimpactkotlin.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Weapon(
    val name: String? = null,
    val weapontype: String? = null,
    val rarity: String? = null,
    val baseatk: Int? = null,
    val substat: String? = null,
    val description: String? = null,
    val effectname: String? = null,
    val effect: String? = null,
    val r1: List<String>? = null
    /*
    val r2: List<String>? = null,
    val r3: List<String>? = null,
    val r4: List<String>? = null,
    val r5: List<String>? = null,

    val subvalue: String? = null,
    val weaponmaterialtype: String? = null,

    val costs: Costs? = null,
     */
) : Parcelable
/*
data class Costs(
    val ascend1: List<Ascend1>? = null,
    val ascend2: List<Ascend2>? = null,
    val ascend3: List<Ascend3>? = null,
    val ascend4: List<Ascend4>? = null,
    val ascend5: List<Ascend5>? = null,
    val ascend6: List<Ascend6>
)

data class Ascend1(
    val count: Int? = null,
    val name: String
)

data class Ascend2(
    val count: Int? = null,
    val name: String
)

data class Ascend3(
    val count: Int? = null,
    val name: String
)

data class Ascend4(
    val count: Int? = null,
    val name: String
)

data class Ascend5(
    val count: Int? = null,
    val name: String
)

data class Ascend6(
    val count: Int? = null,
    val name: String
)
*/
