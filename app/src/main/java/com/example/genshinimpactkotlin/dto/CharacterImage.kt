package com.example.genshinimpactkotlin.dto

import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
@IgnoreExtraProperties
data class CharacterImage(
    val portrait: String? = null,
    val cover2: String? = null,
    val icon: String? = null,
    /* Variables no utilizadas
    val cover1: String? = null,
    val card: String? = null,

    val hoyolab_avatar: String? = null,
    val namegachaslice: String? = null,
    val namegachasplash: String? = null,
    val nameicon: String? = null,
    val nameiconcard: String? = null,
    val namesideicon: String? = null,
    val sideicon: String? = null
    */
) : Parcelable