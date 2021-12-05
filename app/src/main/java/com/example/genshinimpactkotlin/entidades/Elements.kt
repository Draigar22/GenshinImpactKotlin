package com.example.genshinimpactkotlin.entidades

import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@Parcelize
@IgnoreExtraProperties
data class ElementImage(
    var name: String? = null,
    var base64: String? = null,
    var wikia: String? = null
) : Parcelable
