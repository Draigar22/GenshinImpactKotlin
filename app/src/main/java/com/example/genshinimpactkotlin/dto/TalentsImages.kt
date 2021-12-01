package com.example.genshinimpactkotlin.dto

import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


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