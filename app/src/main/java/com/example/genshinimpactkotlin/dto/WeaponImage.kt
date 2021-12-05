package com.example.genshinimpactkotlin.dto

import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize


@Parcelize
@IgnoreExtraProperties
data class WeaponImage(
    val icon: String? = null,
    val awakenicon: String? = null,
) : Parcelable