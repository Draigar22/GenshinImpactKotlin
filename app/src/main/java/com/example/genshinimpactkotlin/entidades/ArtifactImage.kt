package com.example.genshinimpactkotlin.entidades

import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize


@Parcelize
@IgnoreExtraProperties
data class ArtifactImage(
    val flower: String? = null,
    val plume: String? = null,
    val sands: String? = null,
    val goblet: String? = null,
    val circlet: String? = null,
) : Parcelable