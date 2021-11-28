package com.example.genshinimpactkotlin.dto

import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.*

@Parcelize
@IgnoreExtraProperties
data class Character(
    var defaultName: String? = null,
    var affiliation: String? = null,
    var association: String? = null,
    var birthday: String? = null,
    var birthdaymmdd: String? = null,
    var body: String? = null,
    var constellation: String? = null,
    var costs: Costs? = null,
    var cv: Cv? = null,
    var description: String? = null,
    var element: String? = null,
    var gender: String? = null,
    var name: String? = null,
    var rarity: String? = null,
    var region: String? = null,
    var substat: String? = null,
    var title: String? = null,
    var weapontype: String? = null,
) : Parcelable

@Parcelize
@IgnoreExtraProperties
data class Costs(
    var ascend1: List<Ascend1>? = null,
    var ascend2: List<Ascend2>? = null,
    var ascend3: List<Ascend3>? = null,
    var ascend4: List<Ascend4>? = null,
    var ascend5: List<Ascend5>? = null,
    var ascend6: List<Ascend6>? = null
) : Parcelable

@Parcelize
@IgnoreExtraProperties
data class Cv(
    var chinese: String? = null,
    var english: String? = null,
    var japanese: String? = null,
    var korean: String? = null
) : Parcelable

@Parcelize
@IgnoreExtraProperties
data class Ascend1(
    var count: Long? = null,
    var name: String? = null
) : Parcelable

@Parcelize
@IgnoreExtraProperties
data class Ascend2(
    var count: Long? = null,
    var name: String? = null
) : Parcelable

@Parcelize
@IgnoreExtraProperties
data class Ascend3(
    var count: Long? = null,
    var name: String? = null
) : Parcelable

@Parcelize
@IgnoreExtraProperties
data class Ascend4(
    var count: Long? = null,
    var name: String? = null
) : Parcelable

@Parcelize
@IgnoreExtraProperties
data class Ascend5(
    var count: Long? = null,
    var name: String? = null
) : Parcelable

@Parcelize
@IgnoreExtraProperties
data class Ascend6(
    var count: Long? = null,
    var name: String? = null
) : Parcelable