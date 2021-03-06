package com.example.genshinimpactkotlin.entidades

import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@Parcelize
@IgnoreExtraProperties
data class TalentsImages(
    val combat1: String? = null,
    val combat2: String? = null,
    val combat3: String? = null,
    val passive1: String? = null,
    val passive2: String? = null,
    val passive3: String? = null
) : Parcelable